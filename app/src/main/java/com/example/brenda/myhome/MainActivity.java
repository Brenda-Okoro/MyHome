package com.example.brenda.myhome;

import android.Manifest;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.pddstudio.talking.Talk;
import com.pddstudio.talking.model.SpeechObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import ai.wit.sdk.IWitListener;
import ai.wit.sdk.Wit;
import ai.wit.sdk.model.WitOutcome;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements IWitListener {
    private ImageView imageView;
    Wit wit;
    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String mFileName = null;
    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;
    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) finish();

    }

    private void onRecord(boolean start) {
//        if (start) {
//            startRecording();
//        } else {
//            stopRecording();
//            uploadRecording();
//        }

        try {
            wit.toggleListening();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadRecording() {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody audioRequestBody = RequestAudioUtil.create(MediaType.parse("audio/raw"), new FileInputStream(new File(mFileName)));
            Request request = new Request.Builder()
                    .url("https://api.wit.ai/speech?v=20160526")
                    .post(audioRequestBody)
                    .addHeader("content-type", "audio/raw")
                    .addHeader("authorization", "Bearer OODMVYPEJE3HAJQXXOOIK4WWEMQHVRCV")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    Log.d(LOG_TAG, response.body().string());
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }


    @Override
    public void witDidGraspIntent(ArrayList<WitOutcome> response, String message, Error error) {
        Log.d(LOG_TAG, String.valueOf(response.size()));

        Log.d(LOG_TAG, new Gson().toJson(response, ArrayList.class));

    }

    @Override
    public void witDidStartListening() {
        Log.d(LOG_TAG, "witting...");

    }

    @Override
    public void witDidStopListening() {
        Log.d(LOG_TAG, "Stop witting...");

    }

    @Override
    public void witActivityDetectorStarted() {

    }

    @Override
    public String witGenerateMessageId() {
        return "1";
    }

    class RecordButton extends Button {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Record to the external cache directory for visibility
        String accessToken = "OODMVYPEJE3HAJQXXOOIK4WWEMQHVRCV";
        wit = new Wit(accessToken, this);
        //wit.enableContextLocation(getApplicationContext());
        mFileName = getExternalCacheDir().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";

        if (new File(mFileName).exists()) {
            Log.d(LOG_TAG, "file exists");
        }

        imageView = (ImageView) findViewById(R.id.speaker_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Please Confirm what you just said", Toast.LENGTH_LONG).show();

                ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
                LinearLayout ll = new LinearLayout(MainActivity.this);
                mRecordButton = new RecordButton(MainActivity.this);
                ll.addView(mRecordButton,
                        new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                0));
                setContentView(ll);

            }

        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }


    }
}


