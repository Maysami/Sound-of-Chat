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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setTitle("Please Select one Item ...");


                Button imageMusic = (Button) dialog.findViewById(R.id.imageMusic);

                Button imageRecord = (Button) dialog.findViewById(R.id.imageRecord);


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

    }