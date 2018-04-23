package com.turastory.speechrecognition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    
    private static final int REQUEST_SPEECH_RECOGNITION = 100;
    
    @BindView(R.id.text_view)
    TextView textView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        
        findViewById(R.id.button).setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "말을 하세요.");
            startActivityForResult(intent, REQUEST_SPEECH_RECOGNITION);
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == REQUEST_SPEECH_RECOGNITION) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                
                if (results != null) {
                    for (String result : results) {
                        Log.d("Test", result);
                    }
                    
                    if (results.size() > 0)
                        textView.setText(results.get(0));
                }
            }
        }
    }
}
