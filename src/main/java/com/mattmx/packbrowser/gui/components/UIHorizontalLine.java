package com.mattmx.packbrowser.gui.components;

import gg.essential.elementa.components.UIRoundedRectangle;
import gg.essential.elementa.constraints.PixelConstraint;

public class UIHorizontalLine extends UIRoundedRectangle {
    public UIHorizontalLine() {
        this(1f);
    }

    public UIHorizontalLine(float thickness) {
        this(thickness, 0f);
    }

    public UIHorizontalLine(float thickness, float radius) {
        super(radius);
        setHeight(new PixelConstraint(thickness));
    }
}
