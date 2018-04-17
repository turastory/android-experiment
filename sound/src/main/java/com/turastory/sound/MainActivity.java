package com.turastory.sound;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.turastory.sound.sound.Sound;
import com.turastory.sound.sound.Sounds;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        setupGlobal();
        setupCustom();
    }
    
    private void setupGlobal() {
        String[] songNames = {"cartoon_enlarge", "cartoon_slip", "laugh", "sad_trombone", "yee_ha"};
        Random random = new Random();
        
        findViewById(R.id.play_button).setOnClickListener(v -> {
            Sounds.global().play(songNames[random.nextInt(songNames.length)]);
        });
        
        findViewById(R.id.stop_button).setOnClickListener(v -> {
            Sounds.global().stop();
        });
        
        findViewById(R.id.play_sequential_button).setOnClickListener(v -> {
            Sounds.global().playSequentially(songNames);
        });
        
        findViewById(R.id.stop_sequential_button).setOnClickListener(v -> {
            Sounds.global().stopSequential();
        });
    }
    
    private void setupCustom() {
        Sounds sounds = Sounds.preloadFromAssets(this)
            .addRawSound(new Sound("Hello World", "Cartoon Enlarge.wav", 2))
            .load();
        
        findViewById(R.id.play_custom_button).setOnClickListener(v -> {
            sounds.play("Hello World");
        });
        
        findViewById(R.id.stop_custom_button).setOnClickListener(v -> {
            sounds.stop();
        });
    }
}
