package com.example.stas.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class ServiceActivity extends Service {
    MediaPlayer player;
    Handler seekHandler;
    MediaMetadataRetriever mmr;
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        try {
            player.setDataSource(this, Uri.parse("android.resource://com.example.stas.lab5/" + R.raw.song));
        } catch (IOException e) {
            e.printStackTrace();
            this.stopSelf();
        }
        mmr = new MediaMetadataRetriever();
        mmr.setDataSource(this, Uri.parse("android.resource://com.example.stas.lab5/" + R.raw.song));
        MainActivity.artist.setText("Artist: " + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        MainActivity.song_name.setText("Song: " + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        long durationMs = Long.parseLong(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        seekHandler = new Handler();
        long duration = durationMs / 1000;
        long h = duration / 3600;
        long m = (duration - h * 3600) / 60;
        long s = duration - (h * 3600 + m * 60);
        if (h != 0)
        {
            MainActivity.song_end.setText(Long.toString(h) + ":" + Long.toString(m) + ":" + Long.toString(s));
        }
        else
        {
            MainActivity.song_end.setText(Long.toString(m) + ":" + Long.toString(s));
        }
        MainActivity.song_duration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    player.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        player.setVolume(100, 100);
        player.setLooping(true);
        Toast.makeText(this, "Service has been created", Toast.LENGTH_SHORT).show();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            this.stopSelf();
        }
        MainActivity.song_duration.setMax(player.getDuration());
        player.start();
        seekUpdation();
        Toast.makeText(this, "Service has been started", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
        seekHandler.removeCallbacks(run);
        MainActivity.song_duration.setProgress(0);
        Toast.makeText(this, "Service has been destroyed", Toast.LENGTH_SHORT).show();
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            seekUpdation();
        }
    };

    public void seekUpdation() {
        MainActivity.song_duration.setProgress(player.getCurrentPosition());
        seekHandler.postDelayed(run, 1000);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}