package com.turastory.record;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RecordActivity extends AppCompatActivity {
    
    private static final int PERMISSION_REQUEST = 10020;
    
    private TextView timeText;
    private Button recordButton;
    private Button playButton;
    
    private AudioRecord audioRecord;
    private AudioTrack audioTrack;
    private int sampleRate = -1;
    private byte[] buffer;
    
    private boolean isRecording;
    private boolean isPlaying;
    
    private int fileCount = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        
        timeText = findViewById(R.id.time_text);
        recordButton = findViewById(R.id.button_record);
        playButton = findViewById(R.id.button_play);
        
        recordButton.setOnClickListener(v -> record(getFilePath(fileCount)));
        playButton.setOnClickListener(v -> playRecord(getFilePath(fileCount)));
        
        acquirePermission(Manifest.permission.RECORD_AUDIO);
        acquirePermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        acquirePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
    
    private void record(File path) {
        if (isRecording) {
            stopRecording();
        } else {
            startRecording();
            createAudioRecord();
            
            try {
                OutputStream outputStream = new FileOutputStream(path);
                
                audioRecord.startRecording();
                
                new Thread(() -> {
                    while (isRecording) {
                        final int size = audioRecord.read(buffer, 0, buffer.length);
                        if (isInvalid(size))
                            return;
                        
                        try {
                            outputStream.write(buffer, 0, buffer.length);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    audioRecord.stop();
                    audioRecord.release();
                    audioRecord = null;
                    
                    try {
                        outputStream.close();
                        Log.e("RecordActivity", "File Stored in " + path.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void acquirePermission(String permission) {
        if (permissionRequired(permission)) {
            ActivityCompat.requestPermissions(this,
                new String[]{permission},
                PERMISSION_REQUEST);
        }
    }
    
    private boolean permissionRequired(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED;
    }
    
    private void stopRecording() {
        recordButton.setText("START");
        isRecording = false;
    }
    
    private void startRecording() {
        recordButton.setText("STOP");
        isRecording = true;
    }
    
    private void createAudioRecord() {
        final int[] sampleRateCandidates = {44100, 16000, 11025, 22050};
        
        final int audioSource = MediaRecorder.AudioSource.MIC;
        final int channelCount = AudioFormat.CHANNEL_IN_STEREO;
        final int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        
        audioRecord = null;
        
        for (int sampleRate : sampleRateCandidates) {
            int bufferSize = AudioTrack.getMinBufferSize(sampleRate, channelCount, audioFormat);
            if (bufferSize == AudioRecord.ERROR_BAD_VALUE)
                continue;
            
            audioRecord = new AudioRecord(audioSource, sampleRate, channelCount, audioFormat, bufferSize);
            
            if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
                this.sampleRate = sampleRate;
                buffer = new byte[bufferSize];
            } else {
                audioRecord.release();
            }
        }
    }
    
    @NonNull
    private File getFilePath(int fileCount) {
        return new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "파일" + fileCount);
    }
    
    private boolean isInvalid(int size) {
        return size < AudioRecord.SUCCESS;
    }
    
    private void playRecord(File path) {
        if (sampleRate == -1)
            return;
        
        if (isPlaying) {
            stopPlaying();
        } else {
            startPlaying();
            audioTrack = createAudioTrack();
            
            new Thread(() -> {
                try {
                    byte[] writeData = new byte[buffer.length];
                    FileInputStream fileInputStream = new FileInputStream(path);
                    DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                    
                    audioTrack.play();
                    
                    while (isPlaying) {
                        try {
                            int size = dataInputStream.read(writeData, 0, buffer.length);
                            if (isInvalid(size)) {
                                runOnUiThread(this::stopPlaying);
                                break;
                            }
                            
                            audioTrack.write(writeData, 0, size);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    audioTrack.stop();
                    audioTrack.release();
                    
                    dataInputStream.close();
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
    
    private void stopPlaying() {
        playButton.setText("PLAY");
        isPlaying = false;
    }
    
    private void startPlaying() {
        playButton.setText("STOP");
        isPlaying = true;
    }
    
    private AudioTrack createAudioTrack() {
        return new AudioTrack(
            AudioManager.STREAM_MUSIC,
            sampleRate,
            AudioFormat.CHANNEL_IN_STEREO,
            AudioFormat.ENCODING_PCM_16BIT,
            buffer.length,
            AudioTrack.MODE_STREAM);
    }
}
