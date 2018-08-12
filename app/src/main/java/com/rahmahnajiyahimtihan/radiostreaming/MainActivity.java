package com.rahmahnajiyahimtihan.radiostreaming;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    Button play, stop;
    MediaPlayer mediaPlayer;
    String url_radio = "";//ibarat gelas
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ui = user interface = tampilan aplikasi =  lgsg dengan user
        UI();
        setPutar(); //alt enter
    }

    private void setPutar() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(url_radio);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                progressBar.setIndeterminate(false); //tak tentu = inderteminate
                progressBar.setSecondaryProgress(100);
            }
        });
    }

    private void UI() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100); //for the loading
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setIndeterminate(true);

        //declarate
        play = (Button) findViewById(R.id.btnPlay);
        stop = (Button) findViewById(R.id.btnStop);

        play.setOnClickListener(this);
        stop.setOnClickListener(this);

        stop.setEnabled(false);
        url_radio = "http://103.16.199.177:9160/";

    }

    @Override
    public void onClick(View view) {
        if (view == play) {
            Memutar();//alt enter

        }else if (view == stop) {
            Berhenti();//alt enter
            play.setEnabled(true);
            stop.setEnabled(false);
            progressBar.setVisibility(View.INVISIBLE);

        }
    }

    private void Berhenti() {
        if (mediaPlayer == null) return;
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
            setPutar();
        }catch (IllegalStateException e) {

        }
    }

    private void Memutar() {
        play.setEnabled(false);
        stop.setEnabled(true);
        progressBar.setVisibility(View.VISIBLE);

        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {//if there is listener pasti new o
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();

            }
        });
    }
}
