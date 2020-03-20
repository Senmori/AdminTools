package net.senmori.admintools.lib.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class NetworkTools
{

    public static void writeItemStack(ByteBuf dataOut, @Nonnull ItemStack itemStack)
    {
        PacketBuffer buffer = new PacketBuffer(dataOut);
        CompoundNBT nbt = new CompoundNBT();
        itemStack.write(nbt);
        try {
            buffer.writeCompoundTag(nbt);
            buffer.writeInt(itemStack.getCount());
        } catch (IndexOutOfBoundsException e)
        {
            //TODO: Handle errors with the logger
        }
    }

    public static ItemStack readItemStack(ByteBuf dataIn) {
        PacketBuffer buf = new PacketBuffer(dataIn);
        CompoundNBT nbt = buf.readCompoundTag();
        ItemStack stack = ItemStack.read(nbt);
        int amount = buf.readInt();
        stack.setCount( Math.max( amount, 0 ) );
        return stack;
    }

    @Nonnull
    public static List<ItemStack> readItemStackList(ByteBuf buf) {
        int size = buf.readInt();
        List<ItemStack> outputs = new ArrayList<>(size);
        for (int i = 0 ; i < size ; i++) {
            outputs.add(NetworkTools.readItemStack(buf));
        }
        return outputs;
    }

    public static void writeItemStackList(ByteBuf buf, @Nonnull List<ItemStack> outputs) {
        buf.writeInt(outputs.size());
        outputs.forEach( item -> NetworkTools.writeItemStack( buf, item ) );
    }
}
