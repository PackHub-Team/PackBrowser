package com.mattmx.packbrowser.gui.components;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.constraints.*;
import gg.essential.elementa.constraints.animation.AnimatingConstraints;
import gg.essential.elementa.constraints.animation.Animations;

public class UIButton extends UIBlock {
    public UIButton() {
        this.setColor(UIColors.BACKGROUND_BAR);
        this.onMouseEnterRunnable(() -> {
            AnimatingConstraints anim = this.makeAnimation();
            anim.setColorAnimation(Animations.IN_OUT_EXP, 0.2f, new ConstantColorConstraint(UIColors.BACKGROUND_LINE));
            this.animateTo(anim);
        });
        this.onMouseLeaveRunnable(() -> {
            AnimatingConstraints anim = this.makeAnimation();
            anim.setColorAnimation(Animations.IN_OUT_EXP, 0.2f, new ConstantColorConstraint(UIColors.BACKGROUND_BAR));
            this.animateTo(anim);
        });
    }
}
