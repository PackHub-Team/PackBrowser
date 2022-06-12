package com.mattmx.packbrowser.gui.components;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UICircle;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.ConstantColorConstraint;
import gg.essential.elementa.constraints.PixelConstraint;
import gg.essential.elementa.constraints.RelativeConstraint;
import gg.essential.elementa.constraints.animation.AnimatingConstraints;
import gg.essential.elementa.constraints.animation.AnimationComponent;
import gg.essential.elementa.constraints.animation.Animations;
import gg.essential.elementa.constraints.animation.ColorAnimationComponent;
import org.apache.logging.log4j.core.config.plugins.validation.Constraint;

import java.awt.*;

public class UILoading extends UIBlock {
    private final Color full;
    private final Color fade;
    public UILoading(Color full, Color fade) {
        this.full = full;
        this.fade = fade;
        for (int i = 0; i < 3; i++) {
            UIComponent circle = new UICircle()
                    .setRadius(new RelativeConstraint(1f))
                    .setX(new RelativeConstraint(i/3f))
                    .setY(new CenterConstraint())
                    .setChildOf(this)
                    .setColor(fade);
            AnimatingConstraints anim = circle.makeAnimation();
            animate(circle, anim, i);
        }
    }

    public void animate(UIComponent circle, AnimatingConstraints anim, float delay) {
        anim.setColorAnimation(Animations.IN_OUT_EXP, 0.5f, new ConstantColorConstraint(full), delay);
        circle.animateTo(anim);
        anim.onCompleteRunnable(() -> {
            AnimatingConstraints anim1 = circle.makeAnimation();
            anim1.setColorAnimation(Animations.IN_OUT_EXP, 0.5f, new ConstantColorConstraint(fade));
            circle.animateTo(anim1);
            anim1.onCompleteRunnable(() -> {
                animate(circle, anim1, 0f);
            });
        });
    }
}
