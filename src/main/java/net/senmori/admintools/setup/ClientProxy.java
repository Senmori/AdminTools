package net.senmori.admintools.setup;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.senmori.admintools.config.ForgeClientConfig;

public class ClientProxy implements IProxy {
    @Override
    public void init () {
        ForgeClientConfig.init();
    }

    @Override
    public World getClientWorld () {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer () {
        return Minecraft.getInstance().player;
    }
}
