package com.mattmx.packbrowser.mixin;

import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;

@Mixin(net.minecraft.client.font.UnicodeTextureFont.class)
public class UnicodeTextureFont {
    private Resource resource;
    @Redirect(
            at = @At(value ="INVOKE",
                    target="Lnet/minecraft/resource/ResourceManager;getResource(Lnet/minecraft/util/Identifier;)Lnet/minecraft/resource/Resource;"
            ),
            method = "getGlyphImage"
    )
    public Resource getGlyphImage$ResourceManager$getResource(ResourceManager resourceManager, Identifier identifier){
        Identifier fontSheet = new Identifier("packbrowser", "unicode_page_00.png");
        try {
            System.out.print(fontSheet.getPath());
            resource = resourceManager.getResource(fontSheet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resource;
    }
}
