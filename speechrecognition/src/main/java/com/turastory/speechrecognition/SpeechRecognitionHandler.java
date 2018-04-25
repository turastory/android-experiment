package com.turastory.speechrecognition;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by tura on 2018-04-23.
 * <p>
 * Handling Google's Speech recognition feature.
 */
public class SpeechRecognitionHandler {
    
    private static final int REQUEST_SPEECH_RECOGNITION_DEFAULT = 792;
    
    private String packageName;
    private String language;
    private String promptMessage = "말을 하세요.";
    
    private SpeechListener speechListener;
    private AllSpeechListener allSpeechListener;
    
    private int requestCode = REQUEST_SPEECH_RECOGNITION_DEFAULT;
    
    private SpeechRecognitionHandler(String language) {
        this.language = language;
    }
    
    public static SpeechRecognitionHandler create(String language) {
        return new SpeechRecognitionHandler(language);
    }
    
    public SpeechRecognitionHandler setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }
    
    public SpeechRecognitionHandler setPromptMessage(String promptMessage) {
        this.promptMessage = promptMessage;
        return this;
    }
    
    public SpeechRecognitionHandler setSpeechListener(SpeechListener onResultConsumer) {
        this.speechListener = onResultConsumer;
        return this;
    }
    
    public SpeechRecognitionHandler setAllSpeechListener(AllSpeechListener allSpeechListener) {
        this.allSpeechListener = allSpeechListener;
        return this;
    }
    
    public SpeechRecognitionHandler setRequestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }
    
    public void startSpeechRecognition(Activity activity) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName == null ? activity.getPackageName() : packageName);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language == null ? provideDefaultLanguage() : language);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, promptMessage);
        activity.startActivityForResult(intent, requestCode);
    }
    
    private boolean provideDefaultLanguage() {
        return false;
    }
    
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (speechListener == null && allSpeechListener == null) {
            Log.d("SpeechRecognition", "Please provide result consumer for receive.");
            return;
        }
    
        if (requestCode == REQUEST_SPEECH_RECOGNITION_DEFAULT) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                
                if (results != null) {
                    if (allSpeechListener != null)
                        allSpeechListener.onResults(results);
                    
                    if (results.size() > 0)
                        if (speechListener != null)
                            speechListener.onResult(results.get(0));
                }
            }
        }
    }
    
    public interface SpeechListener {
        void onResult(String result);
    }
    
    public interface AllSpeechListener {
        void onResults(ArrayList<String> results);
    }
}
