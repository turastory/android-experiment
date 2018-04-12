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
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        Sounds.preloadFromAssets(getAssets())
            .setMaxStreamSizeAtOnce(5)
            .addRawSound(new Sound("cartoon_enlarge", "Cartoon Enlarge.wav", 1))
            .addRawSound(new Sound("cartoon_slip", "Cartoon Slip.wav", 1))
            .addRawSound(new Sound("laugh", "Crowd Laugh 5.wav", 1))
            .addRawSound(new Sound("sad_trombone", "Sad Trombone 2.wav", 1))
            .addRawSound(new Sound("yee_ha", "Yelling Yee Ha.wav", 1))
            .load();
    
        // This should not play the sounds.
        testNotReady();
    }
    
    private void testNotReady() {
        Sounds.ready().play("yee_ha");
    }
    
    @Override
    public void onTerminate() {
        super.onTerminate();
        
        Sounds.unloadAll();
    }
}
