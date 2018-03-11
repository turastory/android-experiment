package com.turastory.spannblestringsample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by soldi on 2018-03-12.
 */

public class MainActivity extends AppCompatActivity {
    
    private static final String[] randomTexts = new String[]{"아", "흠", "에", "위"};
    
    int defaultColor = Color.parseColor("#333333");
    int activeColor = Color.parseColor("#ff0000");
    
    String beforeText = null;
    String clickableText = "이건 어떨까?";
    boolean active = false;
    
    private String getRandomText() {
        return randomTexts[new Random().nextInt(randomTexts.length)];
    }
    
    @BindView(R.id.spannableText)
    TextView spannableText;
    @BindView(R.id.toggleButton)
    TextView toggleButton;
    @BindView(R.id.alterTextButton)
    TextView alterTextButton;
    
    ClickableColorSpan span;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    
        span = new ClickableColorSpan(defaultColor, activeColor) {
            @Override
            public void onClicked(@NotNull View widget) {
                Log.e("asdf", "clicked");
                toggleActive();
            }
        };
        
        toggleButton.setOnClickListener(v -> toggleActive());
        
        alterTextButton.setOnClickListener(v -> {
            Log.e("asdf", "alter");
            clickableText = getRandomText();
            setText();
        });
        
        setText();
    }
    
    private void toggleActive() {
        Log.e("asdf", "toggle");
        active = !active;
        span.setActive(active);
        setText();
    }
    
    private void setText() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
    
        builder.append(normalSpannableString("우헤헤"));
        builder.append(clickableSpannableString(span, clickableText));
        builder.append(normalSpannableString("신난다"));
    
        spannableText.setText(builder, TextView.BufferType.SPANNABLE);
        spannableText.setMovementMethod(LinkMovementMethod.getInstance());
    }
    
    private SpannableString normalSpannableString(String string) {
        SpannableString spannable = new SpannableString(string);
        spannable.setSpan(new ForegroundColorSpan(defaultColor), 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }
    
    private SpannableString clickableSpannableString(ClickableColorSpan span, String string) {
        SpannableString spannable = new SpannableString(string);
        spannable.setSpan(span, 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }
}
