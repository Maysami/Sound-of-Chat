package com.elenoon.sound;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageButton;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class SoundListActivity extends Activity {
    // SDCard Path
    final String MEDIA_PATH = new String("/sdcard/");
    private ArrayList<HashMap<String, String>> songsList =new ArrayList<HashMap<String, String>>();

    final Context context = this;
    private ImageButton btnBack;
    private ImageButton btnHome;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soundlist);

        for (Object o : songsList) {
            System.out.println(o);
        }

        /*addListenerOnButton();*/
    }

    /*Function to read all mp3 files from sdcard and store the details in ArrayList*/

    public ArrayList<HashMap<String, String>> getPlayList(){

        File home = new File(MEDIA_PATH);
        if (home.listFiles(new FileExtensionFilter()).length > 0) {
            for (File file : home.listFiles(new FileExtensionFilter())) {
                HashMap<String, String> song = new HashMap<String, String>();
                song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
                song.put("songPath", file.getPath());

                // Adding each song to SongList
                songsList.add(song);
            }
        }
        // return songs list array
        return songsList;
    }

    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name)
        {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }

//button back && Home
 /*   public void addListenerOnButton() {
        btnBack = (ImageButton) findViewById(R.id.imageBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
               finish();
            }
        });
        btnHome = (ImageButton) findViewById(R.id.imageHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }
    });

    }*/
}