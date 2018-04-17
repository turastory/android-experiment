package com.turastory.sound.sound;

/**
 * Created by tura on 2018-04-12.
 * <p>
 * Represents sound to play in Sounds.
 */
class RawSound {
    private String name;
    private int soundId;
    private float rate;
    private int duration;
    
    public RawSound(String name, int soundId, float rate, int duration) {
        this.name = name;
        this.soundId = soundId;
        this.rate = rate;
        this.duration = duration;
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
    
    public int getDuration() {
        return duration;
    }
}
