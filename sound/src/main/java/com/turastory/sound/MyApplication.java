package com.turastory.sound;

import android.app.Application;

import com.turastory.sound.sound.Sound;
import com.turastory.sound.sound.Sounds;

/**
 * Created by tura on 2018-04-12.
 *
 * Custom Application class to load/unload sounds.
 * Sounds are from soundbible.com.
 */
public class MyApplication extends Application {
    
    private Sounds sounds;
    
    @Override
    public void onCreate() {
        super.onCreate();
    
        sounds = Sounds.preloadFromAssets(this)
            .setMaxStreamSizeAtOnce(5)
            .addRawSound(new Sound("cartoon_enlarge", "Cartoon Enlarge.wav", 1))
            .addRawSound(new Sound("cartoon_slip", "Cartoon Slip.wav", 1))
            .addRawSound(new Sound("laugh", "Crowd Laugh 5.wav", 1))
            .addRawSound(new Sound("sad_trombone", "Sad Trombone 2.wav", 1))
            .addRawSound(new Sound("yee_ha", "Yelling Yee Ha.wav", 1))
            .enableSequentialPlayback()
            .load();
    
        Sounds.setGlobalInstance(sounds);
    
        // This should not play the sounds.
        testNotReady();
    }
    
    private void testNotReady() {
        Sounds.global().play("yee_ha");
    }
    
    @Override
    public void onTerminate() {
        super.onTerminate();
    
        sounds.unloadAll();
    }
}
