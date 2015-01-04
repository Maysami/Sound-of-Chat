package com.elenoon.sound;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by elenoon on 12/20/2014.
 */
public class AudioRecorderActivity extends Activity {

    private MediaRecorder myAudioRecorder;
    private MediaRecorder recorder;
    private String outputFile = null;
    private Button start,stop,play,btnSendVoice;
    File audioFile = null;
    private static final String TAG = "AudioRecorderActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);

        start = (Button)findViewById(R.id.btnStart);
        stop = (Button)findViewById(R.id.btnStop);
        play = (Button)findViewById(R.id.btnPlay);
        btnSendVoice = (Button) findViewById(R.id.btnSend);

        stop.setEnabled(false);
        play.setEnabled(false);

        btnSendVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                      Toast.makeText(getApplicationContext(), "Your Record Sound has been sent", Toast.LENGTH_SHORT).show();
            finish();

        }

        });
     /*  outputFile = Environment.getExternalStorageDirectory().getAbsolutePath();

        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);*/

    }

    public void start(View view)throws IOException{

        start.setEnabled(false);
        stop.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
        File sampleDir = Environment.getExternalStorageDirectory();

        try{
            audioFile = File.createTempFile("sound", ".mp3", sampleDir);
        }catch(IOException e){
            Toast.makeText(getApplicationContext(), "SD Card Access Error", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Sdcard access error");
            return;
        }

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setAudioEncodingBitRate(16);
        recorder.setAudioSamplingRate(44100);
        recorder.setOutputFile(audioFile.getAbsolutePath());
        recorder.prepare();
        recorder.start();
        

    }

    public void stop(View view){
        recorder.stop();
        recorder.release();
        recorder = null;
        stop.setEnabled(false);
        play.setEnabled(true);     
        addRecordingToMediaLibrary();

        Toast.makeText(getApplicationContext(), "Audio recorded successfully",
                Toast.LENGTH_LONG).show();
    }

    private void addRecordingToMediaLibrary() {
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int)(current/1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp3");
        values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());
        ContentResolver contentResolver = getContentResolver();
        Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(base, values);

        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
        Toast.makeText(this, "Added File" + newUri, Toast.LENGTH_LONG).show();

    }

    /* @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.popup_menu, menu);
         return true;
     }*/
    public void play(View view) throws IllegalArgumentException,
            SecurityException, IllegalStateException, IOException{

        MediaPlayer m = new MediaPlayer();
        m.setDataSource(audioFile.getAbsolutePath());
        m.prepare();
        m.start();
        Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();

    }
    

}