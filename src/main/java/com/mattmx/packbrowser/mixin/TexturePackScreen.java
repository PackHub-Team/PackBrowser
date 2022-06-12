package com.mattmx.packbrowser.mixin;

import com.mattmx.packbrowser.gui.PackGUI;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PackScreen.class)
public class TexturePackScreen extends Screen {
    protected TexturePackScreen(Text title) {
        super(title);
    }
    @Inject(at = @At("HEAD"), method = "init")
    public void init(CallbackInfo ci) {
        this.addDrawableChild(new ButtonWidget(this.width / 2 + 4, this.height - 72, 150, 20, Text.of("Get Packs"), (button) -> {
            client.setScreen(new PackGUI(this));
        }));
    }
}
