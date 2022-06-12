package com.mattmx.packbrowser.gui.components;

import com.mattmx.packbrowser.util.packhub.AbstractPack;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.*;
import gg.essential.elementa.constraints.*;
import gg.essential.elementa.constraints.animation.AnimatingConstraints;
import gg.essential.elementa.constraints.animation.AnimationComponent;
import gg.essential.elementa.constraints.animation.Animations;
import gg.essential.elementa.constraints.animation.XAnimationComponent;
import gg.essential.elementa.effects.ScissorEffect;
import kotlin.jvm.functions.Function1;
import net.minecraft.client.MinecraftClient;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

import static org.lwjgl.glfw.GLFW.glfwSetCursor;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

public class PackBlock extends UIRoundedRectangle {
    private AbstractPack pack;

    public PackBlock(AbstractPack pack) {
        super(2f);
        enableEffect(new ScissorEffect());
        this.pack = pack;
        UIComponent preview = null;
        try {
            // change to pack preview image or nothing
            if (pack.hasPreview()) {
                preview = UIImage.ofURL(new URL(pack.getFirstPreview()));
            } else {
                preview = UIImage.ofResource(pack.getFirstPreview());
            }
            preview.setX(new CenterConstraint())
                    .setY(new CenterConstraint())
                    .setWidth(new RelativeConstraint(1f))
                    .setHeight(new MaxConstraint(new ImageAspectConstraint(), new RelativeConstraint(1f)))
                    .setChildOf(this)
                    .setRadius(new PixelConstraint(this.getRadius()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        UIComponent grad = new GradientComponent(Color.WHITE, new Color(255, 255, 255, 20))
                .setDirection(GradientComponent.GradientDirection.LEFT_TO_RIGHT)
                .setX(new PixelConstraint(-100f))
                .setY(new PixelConstraint(0))
                .setWidth(new AdditiveConstraint(new RelativeConstraint(1f), new PixelConstraint(150f)))
                .setHeight(new RelativeConstraint(1f))
                .setChildOf(this)
                .setRadius(new PixelConstraint(this.getRadius()));
        try {
            // icon
            UIImage icon;
            if (pack.hasIcon()) {
                icon = UIImage.ofURL(new URL(pack.getIcon()));
            } else {
                icon = UIImage.ofResource(pack.getIcon());
            }
            icon.setX(new SubtractiveConstraint(new RelativeConstraint(1f), new PixelConstraint(55f)))
                    .setY(new PixelConstraint(5f))
                    .setWidth(new PixelConstraint(50f))
                    .setHeight(new PixelConstraint(50f))
                    .setRadius(new PixelConstraint(5f))
                    .setChildOf(this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // title
        new UIWrappedText(pack.getName(), false)
                .setColor(Color.decode("#F7971C"))
                .setTextScale(new PixelConstraint(1.5f))
                .setWidth(new RelativeConstraint(0.75f))
                .setX(new PixelConstraint(15f))
                .setY(new PixelConstraint(15f))
                .setChildOf(this);
        // description
        new UIWrappedText(pack.getDescription(), false)
                .setColor(UIColors.TEXT)
                .setTextScale(new PixelConstraint(1f))
                .setX(new PixelConstraint(15f))
                .setY(new AdditiveConstraint(new SiblingConstraint(), new PixelConstraint(5f)))
                .setWidth(new SubtractiveConstraint(new RelativeConstraint(1f), new PixelConstraint(75f)))
                .setChildOf(this);
        // downloads
        new UIText("\u009E " + pack.getDownloadsShort(), true)
                .setHeight(new PixelConstraint(15f))
                .setX(new SubtractiveConstraint(new RelativeConstraint(1f), new PixelConstraint(30f)))
                .setY(new SubtractiveConstraint(new RelativeConstraint(1f), new PixelConstraint(15f)))
                .setChildOf(this);
        UIComponent finalPreview = preview;
        onMouseEnterRunnable(() -> {
            AnimatingConstraints anim = grad.makeAnimation();
            anim.setXAnimation(Animations.OUT_EXP, 1f, new PixelConstraint(0));
            grad.animateTo(anim);
            if (finalPreview != null) {
                AnimatingConstraints anim1 = finalPreview.makeAnimation();
                anim1.setWidthAnimation(Animations.OUT_EXP, 1f, new RelativeConstraint(1.1f));
                anim1.setHeightAnimation(Animations.OUT_EXP, 1f, new MaxConstraint(new ImageAspectConstraint(), new RelativeConstraint(1.1f)));
                finalPreview.animateTo(anim1);
            }
        });
        onMouseLeaveRunnable(() -> {
            AnimatingConstraints anim = grad.makeAnimation();
            anim.setXAnimation(Animations.IN_OUT_EXP, 0.5f, new PixelConstraint(-100f));
            grad.animateTo(anim);
            if (finalPreview != null) {
                AnimatingConstraints anim1 = finalPreview.makeAnimation();
                anim1.setWidthAnimation(Animations.IN_OUT_EXP, 1f, new RelativeConstraint(1.0f));
                anim1.setHeightAnimation(Animations.IN_OUT_EXP, 1f, new MaxConstraint(new ImageAspectConstraint(), new RelativeConstraint(1.0f)));
                finalPreview.animateTo(anim1);
            }
        });
        onMouseClickConsumer((e) -> {
            // pack selected, open in pack viewer
        });
    }

}
