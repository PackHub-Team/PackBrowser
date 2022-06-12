package com.mattmx.packbrowser.gui;

import com.mattmx.packbrowser.gui.components.*;
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
    private UIComponent viewer;
    UIComponent searchBar;
    UIComponent sections;
    UIComponent scrollable;
    UIComponent divider;

    public PackGUI(Screen parent) {
        super(ElementaVersion.V2);
        this.parent = parent;
//        List<AbstractPack> packs = PackHub.api().getRecentPacks();
        viewer = new PackViewer(getWindow());
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
                .setWidth(new RelativeConstraint(1f))
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
        AbstractPack pack = new AbstractPack();
        pack.setName("MattMX Private");
        pack.setDescription("Private pack for boosters of PackHub! Now public.");
        pack.setDownloads(2030);
        pack.setIcon("https://cdn.mos.cms.futurecdn.net/6QQEiDSc3p6yXjhohY3tiF.jpg");
        pack.setPreviews(new String[] {"https://www.pcgamesn.com/wp-content/uploads/2020/09/MinecraftPackSeed.jpg"});
        pack.setPrice("FREE");
        UIComponent pack1 = new PackBlock(pack)
                .setX(new SiblingConstraint(4f))
                .setY(new PixelConstraint(0f))
                .setWidth(new SubtractiveConstraint(new RelativeConstraint(0.5f), new PixelConstraint(8f)))
                .setHeight(new PixelConstraint(100f))
                .setColor(Color.WHITE)
                .setChildOf(scrollable);
        new PackBlock(pack)
                .setX(new SiblingConstraint(4f))
                .setY(new PixelConstraint(0f))
                .setWidth(new RelativeConstraint(4f))
                .setHeight(new PixelConstraint(100f))
                .setColor(Color.WHITE)
                .setChildOf(scrollable);
        pack1.onMouseClickConsumer(e -> {
            ((PackViewer)viewer).open(pack);
        });
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