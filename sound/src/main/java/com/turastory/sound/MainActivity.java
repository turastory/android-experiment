package com.turastory.sound;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.turastory.sound.sound.Sound;
import com.turastory.sound.sound.Sounds;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    
    private Sounds sounds;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        setupGlobal();
        setupCustom();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sounds.unloadAll();
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
            Sounds.global().playSequentially(songNames, () -> runOnUiThread(() -> {
                TextView textView = findViewById(R.id.sample_text);
                String prev = textView.getText().toString();
                textView.setText("sequential call complete");
        
                new Handler().postDelayed(() ->
                    textView.setText(prev), 2000);
            }));
        });
        
        findViewById(R.id.stop_sequential_button).setOnClickListener(v -> {
            Sounds.global().stopSequential();
        });
    }
    
    private void setupCustom() {
        sounds = Sounds.loadFromAssets(this)
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
