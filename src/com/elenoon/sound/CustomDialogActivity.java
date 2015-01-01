package com.elenoon.sound;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class CustomDialogActivity extends Activity {

    final Context context = this;
    private Button button;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        button = (Button) findViewById(R.id.buttonShowCustomDialog);
        // add button listener
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setTitle("Please Select one Item ...");

                // set the custom dialog components - text, image and button
                TextView textMusic = (TextView) dialog.findViewById(R.id.text);
                textMusic.setText("Music List");
                ImageButton imageMusic = (ImageButton) dialog.findViewById(R.id.imageMusic);
                imageMusic.setImageResource(R.drawable.ic_music);


                TextView textRecord = (TextView) dialog.findViewById(R.id.textRecord);
                textRecord.setText("Record Sound");
                ImageButton imageRecord = (ImageButton) dialog.findViewById(R.id.imageRecord);
                imageRecord.setImageResource(R.drawable.ic_record);

                // if button is clicked, close the custom dialog
                imageMusic.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SoundListActivity.class);
                        startActivity(intent);
                    }
                });
                dialog.show();

                imageRecord.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AudioRecorderActivity.class);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });
    }
}