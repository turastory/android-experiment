package com.turastory.progress_management;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
    private BlockingQueue<Call> blockingQueue = new LinkedBlockingQueue<>(10);
    private Call call;
    private NetworkProgress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        requestButton.setOnClickListener(v -> {
            // 큐에 빈자리가 있는지 확인
            if (!isQueueAvailable()) {
                Log.e("asdf", "Queue is not available now.");
                return;
            }

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

    private boolean isQueueAvailable() {
        synchronized (lock) {
            return blockingQueue.remainingCapacity() > 0;
        }
    }

    private void printQueueSize() {
        Log.e("asdf", String.valueOf(blockingQueue.size()));
    }

    private Call createRandomCall() {
        Random random = new Random();
        int n = random.nextInt(2);

        return new Call(this, 1000, n == 0);
    }

    private void addCall(Call call) {
        call.start();

        if (blockingQueue.size() == 0) {
            progress = new NetworkProgress(this);
            progress.show();
        }

        blockingQueue.add(call);
    }

    public void removeCall(Call call) {
        blockingQueue.remove(call);

        if (blockingQueue.size() == 0)
            progress.dismiss();
    }
}
