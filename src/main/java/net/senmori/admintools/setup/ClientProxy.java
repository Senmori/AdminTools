package net.senmori.admintools.setup;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.senmori.admintools.tmp.ClientConfig;

public class ClientProxy implements IProxy {
    @Override
    public void init () {
        ClientConfig.init();
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
