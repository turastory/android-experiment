package com.turastory.sound;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.turastory.sound.sound.Sounds;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        String[] songNames = {"cartoon_enlarge", "cartoon_slip", "laugh", "sad_trombone", "yee_ha"};
        Random random = new Random();
        
        findViewById(R.id.play_button).setOnClickListener(v -> {
            Sounds.ready().play(songNames[random.nextInt(songNames.length)]);
        });
    
        findViewById(R.id.stop_button).setOnClickListener(v -> {
            Sounds.ready().stop();
        });
    
        findViewById(R.id.play_sequential_button).setOnClickListener(v -> {
            Sounds.ready().playSequentially(songNames);
        });
    
        findViewById(R.id.stop_sequential_button).setOnClickListener(v -> {
            Sounds.ready().stopSequential();
        });
    }
}
