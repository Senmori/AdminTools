package net.senmori.custommobs.lib.sound;

import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class CustomSound {

    private final SoundEvent soundEvent;
    private final SoundCategory category;
    private float volume, pitch;

    public CustomSound(SoundEvent sound) {
        this.soundEvent = sound;
        this.category = SoundUtil.get().getSoundCategory( sound.getName() );
        this.volume = 0.25F;
        this.pitch = 1.0F;
    }

    public SoundEvent getSound() {
        return this.soundEvent;
    }

    public SoundCategory getCategory() {
        return category;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void playSound(SoundHandler soundHandler) {
        SimpleSound simpleSound = new SimpleSound( getSound(), getCategory(), getVolume(), getPitch(), 0.0F, 0.0F, 0.0F );
        soundHandler.play( simpleSound );
    }

    public void playSound(SoundHandler soundHandler, BlockPos pos) {
        SimpleSound simpleSound = new SimpleSound( getSound(), getCategory(), getVolume(), getPitch(), pos);
        soundHandler.play( simpleSound );
    }
}
