package net.senmori.custommobs.commands.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.senmori.custommobs.commands.ICommand;

public class GUICommand implements ICommand
{
    @Override
    public void register(CommandDispatcher dispatcher)
    {
        dispatcher.register(Commands.literal("cmgui")
                                    .requires(source -> source.getEntity() instanceof ClientPlayerEntity )
                                    .executes((context) -> {
                                        CommandSource source = context.getSource();
                                        if (source.getEntity() instanceof PlayerEntity)
                                        {

                                        }
                                        return 0;
                                    })

        );
    }

    private void send(PlayerEntity player, String message)
    {
        player.sendMessage(new StringTextComponent(message));
    }
}
