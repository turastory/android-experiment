package com.turastory.sound.sound;

/**
 * Created by tura on 2018-04-12.
 *
 * Represents sound to play in Sounds.
 */
class RawSound {
    private String name;
    private int soundId;
    private float rate;
    
    public RawSound(String name, int soundId, float rate) {
        this.name = name;
        this.soundId = soundId;
        this.rate = rate;
    }
    
    public String getName() {
        return name;
    }
    
    public int getSoundId() {
        return soundId;
    }
    
    public float getRate() {
        return rate;
    }
}
