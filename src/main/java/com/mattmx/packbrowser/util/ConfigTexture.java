package com.mattmx.packbrowser.util;

import net.minecraft.client.resource.metadata.TextureResourceMetadata;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

public class ConfigTexture extends ResourceTexture {
    Identifier i = null;

    public ConfigTexture(Identifier location) {
        super(location);
        i = location;
    }

    //Implement
    protected ResourceTexture.TextureData getTextureData(ResourceManager resourceManager) {
        try {
            BufferedImage bufferedImage;
            URL url = new URL("https://ichef.bbci.co.uk/news/976/cpsprodpb/CBB1/production/_121854125_bahongkong.jpg");
            URLConnection openConnection = url.openConnection();
            InputStream input = openConnection.getInputStream();
            TextureData texture;
            try {
                texture = new TextureData(new TextureResourceMetadata(false, true), NativeImage.read(input));
            } finally {
                input.close();
            }
//            BufferedInputStream in = new BufferedInputStream(input);
//            ImageInputStream stream = ImageIO.createImageInputStream(in);
//            Iterator readers = ImageIO.getImageReaders(stream);
//            if (readers.hasNext()) {
//                ImageReader reader = (ImageReader) readers.next();
//                String format = reader.getFormatName();
//                bufferedImage = reader.read(0);
//                try {
//                    texture = new TextureData(new TextureResourceMetadata(false, true), NativeImage.read(input));
//                }
//            }
//            try {
//                texture = new TextureData(new TextureResourceMetadata(true, true), NativeImage.read(input));
//            } finally {
//                input.close();
//            }

            return texture;
        } catch (IOException var18) {
            return new TextureData(var18);
        }
    }
}
