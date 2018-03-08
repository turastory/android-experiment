package com.turastory.progress_management;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button requestButton;
    @BindView(R.id.cancel_button)
    Button cancelButton;

    private Call call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        requestButton.setOnClickListener(v -> {
            Random random = new Random();
            int n = random.nextInt(2);

            if (n == 0) {
                call = new Call(1000, this::onSuccess);
                call.start();
            } else {
                call = new Call(1000, this::onFailure);
                call.start();
            }
        });

        cancelButton.setOnClickListener(v -> {
            if (call != null && !call.isCanceled()) {
                call.cancel();
            }
        });
    }

    private void onSuccess() {
        Log.e("asdf", "Success!");
    }

    private void onFailure() {
        Log.e("asdf", "Failure!");
    }
}
