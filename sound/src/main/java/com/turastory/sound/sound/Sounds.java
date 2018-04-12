package com.turastory.sound.sound;

import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by tura on 2018-04-12.
 * <p>
 * Singleton class to manage sounds.
 */
public class Sounds {
    private AtomicBoolean isReady = new AtomicBoolean(false);
    private SoundPool pool;
    private Map<String, RawSound> rawSounds;
    
    private Sounds() {
    
    }
    
    /**
     * Play the sound with name.
     * @param name name of the sound to play.
     */
    public void play(String name) {
        use(name, sound -> {
            int success = pool.play(sound.getSoundId(), 1, 1, 1, 0, sound.getRate());
            
            if (success == 0)
                Log.e("Sounds", "Failed to play sound '" + name + "'");
        });
    }
    
    /**
     * Stop playback the sound with name.
     * @param name name of the sound to stop.
     */
    public void stop(String name) {
        use(name, sound -> pool.stop(sound.getSoundId()));
    }
    
    private void use(String name, Consumer<RawSound> soundConsumer) {
        if (!isReady.get()) {
            Log.e("Sounds", "Sounds has not ready yet.");
            return;
        }
        
        RawSound sound = rawSounds.get(name);
        
        if (sound != null) {
            soundConsumer.accept(sound);
        } else {
            throw new RuntimeException("Sound with " + name + " does not exist.");
        }
    }
    
    public static Sounds ready() {
        return LazySingletonHolder.instance;
    }

    // Load using assets.
    public static Sounds.Builder preloadFromAssets(AssetManager assets) {
        return new Sounds.AssetsBuilder(assets);
    }
    
    // Unload all sounds.
    public static void unloadAll() {
        SoundPool pool = Sounds.ready().pool;
        Stream.of(Sounds.ready().rawSounds)
            .forEach(entry -> pool.unload(entry.getValue().getSoundId()));
    }
    
    private static class LazySingletonHolder {
        private static Sounds instance = new Sounds();
    }
    
    /**
     * Builder class for pre-load audio.
     */
    public static abstract class Builder {
        protected List<Sound> sounds;
        private int maxStreamSizeAtOnce;
        
        private Builder() {
            sounds = new ArrayList<>();
        }
        
        public Builder setMaxStreamSizeAtOnce(int maxStreamSizeAtOnce) {
            this.maxStreamSizeAtOnce = maxStreamSizeAtOnce;
            return this;
        }
        
        // Should load sounds in another thread because
        // If there're a lot of sounds It may take some time to load them all.
        public void load() {
            SoundPool pool = createSoundPool(maxStreamSizeAtOnce);
            new Thread(() -> {
                Log.e("Sounds", "load sounds..");
                Sounds.ready().isReady.set(false);
                Map<String, RawSound> rawSounds = loadRawSounds(pool);
                Sounds.ready().pool = pool;
                Sounds.ready().rawSounds = rawSounds;
                Sounds.ready().isReady.set(true);
                Log.e("Sounds", "load sounds Complete!");
            }).start();
        }
        
        public abstract Builder addRawSound(Sound sound);
        
        public abstract Map<String, RawSound> loadRawSounds(SoundPool pool);
        
        private SoundPool createSoundPool(int maxStreamSize) {
            SoundPool sound;
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
                
                sound = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
            } else {
                sound = new SoundPool(maxStreamSize, AudioManager.STREAM_MUSIC, 0);
            }
            
            return sound;
        }
    }
    
    private static class AssetsBuilder extends Builder {
        
        private AssetManager assets;
        
        private AssetsBuilder(AssetManager assets) {
            this.assets = assets;
        }
        
        @Override
        public Builder addRawSound(Sound sound) {
            sounds.add(sound);
            return this;
        }
        
        @Override
        public Map<String, RawSound> loadRawSounds(SoundPool pool) {
            return Stream.of(sounds).map(sound -> {
                try {
                    int soundId = pool.load(assets.openFd(sound.getPath()), 1);
                    return new RawSound(sound.getName(), soundId, sound.getPitch());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toMap(RawSound::getName, rawSound -> rawSound));
        }
    }
}
