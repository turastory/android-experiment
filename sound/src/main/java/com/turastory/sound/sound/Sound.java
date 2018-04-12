package com.turastory.sound.sound;

import android.support.annotation.FloatRange;

/**
 * Created by tura on 2018-04-12.
 *
 * Represents sound object.
 */
public class Sound {
    private String name;
    private String path;
    @FloatRange(from = 0.5, to = 2.0)
    private float pitch;
    
    public Sound(String name, String path, float pitch) {
        this.name = name;
        this.path = path;
        this.pitch = pitch;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public float getPitch() {
        return pitch;
    }
    
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
