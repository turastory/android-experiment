package com.turastory.security;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.securepreferences.SecurePreferences;

/**
 * Created by tura on 2018-05-04.
 *
 * TODO: make asynchronous pref - is it possible? Let's try it.
 */
public class MainActivity extends AppCompatActivity {
    
    private static final String SHARED_PREF_NAME = "shared_pref";
    private static final String KEY = "my_string";
    
    private EditText editText;
    private TextView showcaseText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        editText = findViewById(R.id.edit_text);
        showcaseText = findViewById(R.id.showcase);
        
        findViewById(R.id.secure_pref_button).setOnClickListener(v -> putTextTo(getSecurePref()));
        findViewById(R.id.normal_pref_button).setOnClickListener(v -> putTextTo(getNormalPref()));
        
        findViewById(R.id.secure_pref_show_button).setOnClickListener(v -> showText(getTextFrom(getSecurePref())));
        findViewById(R.id.normal_pref_show_button).setOnClickListener(v -> showText(getTextFrom(getNormalPref())));
    }
    
    private void putTextTo(SharedPreferences pref) {
        pref.edit()
            .putString(KEY, editText.getText().toString())
            .apply();
    }
    
    private void showText(String text) {
        showcaseText.setText(text);
    }
    
    private String getTextFrom(SharedPreferences pref) {
        return pref.getString(KEY, "none");
    }
    
    private SharedPreferences getSecurePref() {
        return new SecurePreferences(this);
    }
    
    private SharedPreferences getNormalPref() {
        return getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
    }
}
