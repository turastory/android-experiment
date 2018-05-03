package com.turastory.security;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.securepreferences.SecurePreferences;

/**
 * Created by tura on 2018-05-04.
 * <p>
 * NOTE: It take a lot of time to create SecurePreferences instance, rather than use it.
 *       -> Singleton!
 */
public class MainActivity extends AppCompatActivity {
    
    private static final String SHARED_PREF_NAME = "shared_pref";
    private static final String KEY = "my_string";
    
    private static SecurePreferences securePref;
    
    private TextView runningIndicator;
    
    private EditText editText;
    private TextView showcaseText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        bindViews();
        setupListeners();
    
        hideLoading();
    }
    
    private void bindViews() {
        runningIndicator = findViewById(R.id.running_indicator_text);
        editText = findViewById(R.id.edit_text);
        showcaseText = findViewById(R.id.showcase);
    }
    
    private void setupListeners() {
        findViewById(R.id.secure_pref_button).setOnClickListener(v -> putTextTo(getSecurePref()));
        findViewById(R.id.normal_pref_button).setOnClickListener(v -> putTextTo(getNormalPref()));
        
        findViewById(R.id.secure_pref_show_button).setOnClickListener(v -> showText(getTextFrom(getSecurePref())));
        findViewById(R.id.normal_pref_show_button).setOnClickListener(v -> showText(getTextFrom(getNormalPref())));
    }
    
    private void putTextTo(SharedPreferences pref) {
        showLoading();
        Exec.backgronud().execute(() -> {
            pref.edit()
                .putString(KEY, editText.getText().toString())
                .apply();
        
            Exec.main().execute(this::hideLoading);
        });
    }
    
    private SharedPreferences getSecurePref() {
        if (securePref == null)
            securePref = new SecurePreferences(this);
        
        return securePref;
    }
    
    private SharedPreferences getNormalPref() {
        return getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
    }
    
    private void showText(String text) {
        showcaseText.setText(text);
    }
    
    private String getTextFrom(SharedPreferences pref) {
        return pref.getString(KEY, "none");
    }
    
    private void hideLoading() {
        Log.e("TEST", "hide");
        runningIndicator.setVisibility(View.INVISIBLE);
    }
    
    private void showLoading() {
        Log.e("TEST", "show");
        runningIndicator.setVisibility(View.VISIBLE);
    }
}
