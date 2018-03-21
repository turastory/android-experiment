package com.turastory.progress_management;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final Object lock = new Object();
    @BindView(R.id.button)
    Button requestButton;
    @BindView(R.id.cancel_button)
    Button cancelButton;
    @BindView(R.id.sequential_button)
    Button sequentialButton;
    @BindView(R.id.pending_request_button)
    Button pendingRequestButton;
    
    private Set<Call> calls = new HashSet<>();
    private Call call;
    private NetworkProgress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        requestButton.setOnClickListener(v -> {
            call = createRandomCall();
            addCall(call);
        });

        cancelButton.setOnClickListener(v -> {
            if (call != null && !call.isCanceled()) {
                call.cancel();
            }
        });

        sequentialButton.setOnClickListener(v -> {
            new Handler().postDelayed(() -> addCall(createRandomCall()), 300);
            new Handler().postDelayed(() -> addCall(createRandomCall()), 600);
            new Handler().postDelayed(() -> addCall(createRandomCall()), 900);
            new Handler().postDelayed(() -> addCall(createRandomCall()), 1200);
            new Handler().postDelayed(() -> addCall(createRandomCall()), 1500);
        });

        pendingRequestButton.setOnClickListener(v -> {
            new Handler().postDelayed(() -> addCall(createRandomCall()), 1500);
            startActivity(new Intent(this, NewActivity.class));
            finish();
        });
        
        new Thread(() -> {
            try {
                while (true) {
                    printQueueSize();
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        Log.e("asdf", "MainActivity is Stopped.");
    }
    
    private void printQueueSize() {
        Log.e("asdf", String.valueOf(calls.size()));
    }

    private Call createRandomCall() {
        Random random = new Random();
        int n = random.nextInt(2);

        return new Call(this, 1000, n == 0);
    }

    private void addCall(Call call) {
        call.start();
        
        synchronized (lock) {
            calls.add(call);
            
            if (calls.size() == 1 && !isFinishing()) {
                progress = new NetworkProgress(this);
                progress.show();
            }
        }
    }

    public void removeCall(Call call) {
        synchronized (lock) {
            calls.remove(call);
            
            if (calls.size() == 0 && progress != null)
                progress.dismiss();
        }
    }
}
