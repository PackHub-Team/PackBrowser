package com.mattmx.packbrowser.gui.components;

import com.mattmx.packbrowser.util.packhub.AbstractPack;
import gg.essential.elementa.components.*;
import gg.essential.elementa.constraints.*;
import kotlin.jvm.functions.Function1;
import net.minecraft.client.MinecraftClient;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

import static org.lwjgl.glfw.GLFW.glfwSetCursor;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

public class PackBlock extends UIRoundedRectangle {
    private float width = 895;
    private float height = 319;
    public PackBlock(AbstractPack pack) {
        super(3f);
        this.setColor(Color.decode("#282828"))
                .setWidth(new PixelConstraint(width))
                .setHeight(new PixelConstraint(height));
        try {
            // change to pack preview image or nothing
            UIImage.ofURL(new URL(pack.getIcon()))
                    .setX(new PixelConstraint(0f))
                    .setY(new PixelConstraint(0f))
                    .setWidth(new MaxImageConstraint())
                    .setHeight(new MaxImageConstraint())
                    .setChildOf(this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            UIImage.ofURL(new URL(pack.getIcon()))
                    .setX(new PixelConstraint(width - 60f))
                    .setY(new PixelConstraint(10f))
                    .setWidth(new PixelConstraint(50f))
                    .setHeight(new PixelConstraint(50f))
                    .setChildOf(this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new GradientComponent(Color.WHITE, new Color(255, 255, 255, 0))
                .setDirection(GradientComponent.GradientDirection.LEFT_TO_RIGHT)
                .setX(new PixelConstraint(0))
                .setY(new PixelConstraint(0))
                .setWidth(new PixelConstraint(width / 2))
                .setHeight(new PixelConstraint(height))
                .setChildOf(this);
        new UIText(pack.getName(), false)
                .setColor(Color.decode("#F7971C"))
                .setTextScale(new PixelConstraint(0.8f))
                .setX(new PixelConstraint(4f))
                .setY(new PixelConstraint(4f))
                .setChildOf(this);
        new UIWrappedText(pack.getDescription(), false)
                .setColor(new Color(163, 163, 163))
                .setTextScale(new RelativeConstraint(0.5f))
                .setWidth(new PixelConstraint(width * 0.75f))
                .setHeight(new PixelConstraint(height))
                .setX(new PixelConstraint(4f))
                .setY(new AdditiveConstraint(new SiblingConstraint(),  new PixelConstraint(5f)))
                .setChildOf(this);
    }

}
