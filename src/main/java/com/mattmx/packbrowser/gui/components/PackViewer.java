package com.mattmx.packbrowser.gui.components;

import com.mattmx.packbrowser.util.packhub.AbstractPack;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UIImage;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.components.UIWrappedText;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.FillConstraint;
import gg.essential.elementa.constraints.PixelConstraint;
import gg.essential.elementa.constraints.RelativeConstraint;
import gg.essential.elementa.effects.OutlineEffect;
import org.checkerframework.checker.guieffect.qual.UI;

import java.awt.*;

// seems to be moving the rest of the PackGUI out of the way...
public class PackViewer extends UIBlock {
    private AbstractPack pack;
    UIComponent preview;
    UIComponent title;
    UIComponent description;
    UIComponent downloads;

    public PackViewer(UIComponent parent) {
        setParent(parent);
        setWidth(new FillConstraint(false));
        setHeight(new FillConstraint(false));
        setX(new PixelConstraint(0f));
        setY(new PixelConstraint(0f));
        setColor(new Color(0, 0, 0, 40));
        UIComponent background = new UIBlock()
                .setColor(UIColors.BACKGROUND)
                .setRadius(new PixelConstraint(5f))
                .enableEffect(new OutlineEffect(UIColors.BACKGROUND_LINE, 1f))
                .setWidth(new RelativeConstraint(0.5f))
                .setHeight(new RelativeConstraint(0.8f))
                .setX(new CenterConstraint())
                .setY(new CenterConstraint())
                .setChildOf(this);
//        title = new UIText("PackName", false)
//                .setColor(UIColors.HIGHLIGHT);
        onMouseClickConsumer((e) -> close());
        hide();
    }

    public void open(AbstractPack pack) {
        this.pack = pack;
//        ((UIText)title).setText(pack.getName());
//        ((UIWrappedText)description).setText(pack.getDescription());
//        ((UIText)downloads).setText(pack.getDownloadsShort());
        unhide(true);
    }

    public void close() {
        hide();
    }
}
