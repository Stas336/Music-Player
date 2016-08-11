package com.example.stas.musicplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button start_button, stop_button;
    static TextView song_name, artist, song_start, song_end;
    static SeekBar song_duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_button = (Button) findViewById(R.id.start_button);
        stop_button = (Button) findViewById(R.id.stop_button);
        song_name = (TextView) findViewById(R.id.song_name);
        artist = (TextView) findViewById(R.id.artist);
        song_duration = (SeekBar) findViewById(R.id.song_duration);
        song_start = (TextView) findViewById(R.id.song_start);
        song_end = (TextView) findViewById(R.id.song_end);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startService(new Intent(MainActivity.this, ServiceActivity.class));
            }
        });
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, ServiceActivity.class));
            }
        });
    }
}
