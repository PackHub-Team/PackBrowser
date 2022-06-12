package com.mattmx.packbrowser.gui;

import com.mattmx.packbrowser.gui.components.*;
import com.mattmx.packbrowser.util.fetch.Fetch;
import com.mattmx.packbrowser.util.packhub.*;
import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.WindowScreen;
import gg.essential.elementa.components.*;
import gg.essential.elementa.components.input.UITextInput;
import gg.essential.elementa.constraints.*;
import gg.essential.elementa.constraints.animation.AnimatingConstraints;
import gg.essential.elementa.constraints.animation.Animations;
import gg.essential.elementa.effects.OutlineEffect;
import gg.essential.elementa.effects.ScissorEffect;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class PackGUI extends WindowScreen {
    private Screen parent;
    private UIComponent viewer;
    List<AbstractPack> exclusive = new ArrayList<>();
    List<AbstractPack> recent = new ArrayList<>();
    List<AbstractPack> popular = new ArrayList<>();
    UIComponent searchBar;
    UIComponent sections;
    UIComponent scrollable;
    UIComponent divider;
    UIComponent center;

    public PackGUI(Screen parent) {
        super(ElementaVersion.V2);
        this.parent = parent;
        UIComponent background = new UIBlock()
                .setX(new PixelConstraint(0))
                .setY(new PixelConstraint(0))
                .setWidth(new FillConstraint())
                .setHeight(new FillConstraint())
                .setColor(UIColors.BACKGROUND)
                .setChildOf(getWindow());
        center = new UIBlock()
                .setX(new CenterConstraint())
                .setY(new CenterConstraint())
                .setWidth(new RelativeConstraint(0.85f))
                .setHeight(new RelativeConstraint(0.75f))
                .setColor(UIColors.BACKGROUND)
                .enableEffect(new OutlineEffect(UIColors.BACKGROUND_LINE, 1f)
                        .addSide(OutlineEffect.Side.Left)
                        .addSide(OutlineEffect.Side.Right)
                        .removeSide(OutlineEffect.Side.Top)
                        .removeSide(OutlineEffect.Side.Bottom))
                .setChildOf(background);
        searchBar = new SearchBar("Packs", this)
                .onSearch(() -> {
                    divider.hide();
                    sections.hide();
                }).onStopSearch(() -> {
                    divider.unhide(true);
                    sections.unhide(true);
                })
                .setX(new RelativeConstraint(0))
                .setY(new RelativeConstraint(0))
                .setWidth(new RelativeConstraint(1f))
                .setHeight(new PixelConstraint(30f))
                .setChildOf(center);
        sections = new SectionBrowser(
                Arrays.asList(
                        new UIText(PackTabs.EXCLUSIVE.toString())
                                .setColor(UIColors.TEXT_ACTIVE)
                                .onMouseClickConsumer((e) -> setTab(PackTabs.EXCLUSIVE)),
                        new UIText(PackTabs.RECENT.toString())
                                .setColor(UIColors.TEXT)
                                .onMouseClickConsumer((e) -> setTab(PackTabs.RECENT)),
                        new UIText(PackTabs.POPULAR.toString())
                                .setColor(UIColors.TEXT)
                                .onMouseClickConsumer((e) -> setTab(PackTabs.POPULAR))
                ), 15f, UIColors.TEXT_ACTIVE, UIColors.TEXT)
                .setX(new PixelConstraint(0f))
                .setY(new AdditiveConstraint(new SiblingConstraint(), new PixelConstraint(5f)))
                .setColor(UIColors.BACKGROUND)
                .setWidth(new SubtractiveConstraint(new RelativeConstraint(1f), new PixelConstraint(30f)))
                .setChildOf(center);
        divider = new UIHorizontalLine(2f)
                .setColor(UIColors.BACKGROUND_BAR)
                .setX(new PixelConstraint(15f))
                .setY(new AdditiveConstraint(new SiblingConstraint(), new PixelConstraint(3f)))
                .setWidth(new SubtractiveConstraint(new RelativeConstraint(1f), new PixelConstraint(30f)))
                .setChildOf(center);
        scrollable = new ScrollComponent()
                .setX(new PixelConstraint(15f))
                .setY(new AdditiveConstraint(new SiblingConstraint(), new PixelConstraint(3f)))
                .setWidth(new SubtractiveConstraint(new RelativeConstraint(1f), new PixelConstraint(30f)))
                .setHeight(new SubtractiveConstraint(new FillConstraint(true), new PixelConstraint(3f)))
                .setColor(UIColors.HIGHLIGHT)
                .setChildOf(center);
        new Fetch<>(() -> PackHub.api().getExclusivePacks()).then(result -> {
            exclusive = result;
            updatePackDisplay();
        }).get();
        new Fetch<>(() -> PackHub.api().getRecentPacks()).then(result -> {
            recent = result;
            updatePackDisplay();
        }).get();
        new Fetch<>(() -> PackHub.api().getPopularPacks()).then(result -> {
            popular = result;
            updatePackDisplay();
        }).get();
        viewer = new PackViewer(getWindow());
    }

    public void updatePackDisplay() {
        SearchBar bar = ((SearchBar)searchBar);
        if (bar.isSearching()) {
            // set the results to the search results.
            PackHubSearchQuery query = PackHubSearchQuery.builder()
                    .search(bar.getText())
                    .category(PackHubSearchQuery.Category.PACKS)
                    .build();
            if (!query.valid()) {
                setPackDisplay(List.of(), "Search must be 3 or more characters!");
                return;
            }
            setPackDisplay(List.of(), "Searching");
            new Fetch<>(() -> PackHub.api().search(query))
                    .then(this::setPackDisplay).fail(Throwable::printStackTrace).get();
        } else {
            // set the results to the selected tab
            SectionBrowser browser = (SectionBrowser) sections;
            PackTabs tab = PackTabs.get(browser.getSelectedNum());
            if (tab == null) return;
            switch(tab) {
                case RECENT -> setPackDisplay(recent);
                case EXCLUSIVE -> setPackDisplay(exclusive);
                case POPULAR -> setPackDisplay(popular);
            }
        }
    }

    public void setPackDisplay(List<AbstractPack> packs) {
        setPackDisplay(packs, "No packs found!");
    }

    public void setPackDisplay(List<AbstractPack> packs, String text) {
        scrollable.clearChildren();
        if (packs == null || packs.size() == 0) {
            new UIText(text).setColor(UIColors.TEXT)
                    .setX(new CenterConstraint())
                    .setY(new CenterConstraint())
                    .setChildOf(scrollable);
            return;
        }
        for (int i = 0; i < packs.size(); i++) {
            AbstractPack pack = packs.get(i);
            if (pack == null) return;
            UIComponent packBlock = new PackBlock(pack)
                    .setX(new SiblingConstraint(4f))
                    .setY(new PixelConstraint(i * (100f + 4f)))
                    .setWidth(new AdditiveConstraint(new RelativeConstraint(0.5f), new PixelConstraint(4f * (i % 2 + 1))))
                    .setHeight(new PixelConstraint(100f))
                    .setColor(Color.WHITE)
                    .setChildOf(scrollable);
            packBlock.onMouseClickConsumer(e -> {
                ((PackViewer)viewer).open(pack);
            });
        }
    }

    public void search(String value) {
        if (value == null) {
            // search cancelled
            setTab(PackTabs.EXCLUSIVE);
        } else {
            // set search results
            updatePackDisplay();
        }
    }

    public void setTab(PackTabs tab) {
        SectionBrowser browser = (SectionBrowser) sections;
        if (tab.num() == browser.getSelectedNum()) return;
        browser.setSelected(tab.num());
        browser.update();
        System.out.println("    " + browser.getSelectedNum());
        updatePackDisplay();
    }
}