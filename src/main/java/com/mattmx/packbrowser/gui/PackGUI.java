package com.mattmx.packbrowser.gui;

import com.mattmx.packbrowser.gui.components.PackBlock;
import com.mattmx.packbrowser.gui.components.SearchBar;
import com.mattmx.packbrowser.gui.components.SectionBrowser;
import com.mattmx.packbrowser.gui.components.UIColors;
import com.mattmx.packbrowser.util.packhub.AbstractPack;
import com.mattmx.packbrowser.util.packhub.PackHub;
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
import net.minecraft.client.gui.screen.Screen;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PackGUI extends WindowScreen {
    private Screen parent;
    UIComponent searchBar;
    UIComponent sections;

    public PackGUI(Screen parent) {
        super(ElementaVersion.V2);
        this.parent = parent;
//        List<AbstractPack> packs = PackHub.api().getRecentPacks();
        UIComponent background = new UIBlock()
                .setX(new PixelConstraint(0))
                .setY(new PixelConstraint(0))
                .setWidth(new FillConstraint())
                .setHeight(new FillConstraint())
                .setColor(UIColors.BACKGROUND)
                .setChildOf(getWindow());
        UIComponent center = new UIBlock()
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
                .setX(new RelativeConstraint(0))
                .setY(new RelativeConstraint(0))
                .setWidth(new FillConstraint())
                .setHeight(new PixelConstraint(30f))
                .setChildOf(center);
        // next bit
        sections = new SectionBrowser(
                Arrays.asList(
                new UIText(PackTabs.EXCLUSIVE.toString())
                        .setColor(UIColors.TEXT_ACTIVE)
                        .onMouseClickConsumer((e) -> setTab(PackTabs.EXCLUSIVE, 0)),
                new UIText(PackTabs.RECENT.toString())
                        .setColor(UIColors.TEXT)
                        .onMouseClickConsumer((e) -> setTab(PackTabs.RECENT, 1)),
                        new UIText(PackTabs.POPULAR.toString())
                                .setColor(UIColors.TEXT)
                                .onMouseClickConsumer((e) -> setTab(PackTabs.POPULAR, 2))
                ), 15f, UIColors.TEXT_ACTIVE, UIColors.HIGHLIGHT)
                .setX(new PixelConstraint(15f))
                .setY(new AdditiveConstraint(new SiblingConstraint(), new PixelConstraint(3f)))
                .setColor(UIColors.BACKGROUND)
                .setChildOf(center);
    }

    public void search(String value) {
        if (value == null) {
            // search cancelled
            return;
        }
    }

    public void setTab(PackTabs tab, int i) {
        SectionBrowser browser = (SectionBrowser) sections;
        browser.setSelected(i);
        browser.update();
        System.out.print(tab);
    }
}