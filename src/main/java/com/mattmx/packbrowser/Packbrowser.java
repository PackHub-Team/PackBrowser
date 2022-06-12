package com.mattmx.packbrowser;

import com.mattmx.packbrowser.util.packhub.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;

@Environment(type = Environment.EnvironmentType.DEV)
public class Packbrowser implements ModInitializer {
    public static final String MOD_ID = "packbrowser";

    @Override
    public void onInitialize() {
    }
}
