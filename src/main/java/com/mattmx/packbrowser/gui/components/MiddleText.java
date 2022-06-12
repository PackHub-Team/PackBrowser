package com.mattmx.packbrowser.gui.components;

import gg.essential.elementa.components.UIText;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.PixelConstraint;

public class MiddleText extends UIText {
    public MiddleText(String text) {
        this(text, false);
    }
    public MiddleText(String text, boolean shadow) {
        super(text == null ? "null" : (text.length() > 0 ? text : "null"), shadow);
        setTextScale(new PixelConstraint(1.2f));
        setX(new CenterConstraint());
        setY(new CenterConstraint());
    }
}
