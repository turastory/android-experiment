package com.turastory.speechrecognition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    
    @BindView(R.id.text_view)
    TextView textView;
    
    private SpeechRecognitionHandler speechRecognizer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    
        speechRecognizer = SpeechRecognitionHandler.create("ko-KR")
            .setPackageName(getPackageName())
            .setAllSpeechListener(results -> {
                for (String result : results)
                    Log.d("Test", result);
            })
            .setSpeechListener(result ->
                runOnUiThread(() -> textView.setText(result)));
    
        findViewById(R.id.button).setOnClickListener(v ->
            speechRecognizer.startSpeechRecognition(this));
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        speechRecognizer.handleActivityResult(requestCode, resultCode, data);
    }
}
