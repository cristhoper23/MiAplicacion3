package com.cristhoper.miaplicacion3;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private VideoView videoPizza;
    private Button btnOrdernar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoPizza = (VideoView) findViewById(R.id.videoMP4);
        videoPizza.setMediaController(new MediaController(this));
        videoPizza.setVideoURI(Uri.parse("https://ak5.picdn.net/shutterstock/videos/4943765/preview/stock-footage-slicing-home-made-pizza.mp4"));
        videoPizza.start();

        btnOrdernar = (Button) findViewById(R.id.btnOrdenar);
        btnOrdernar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PedidoActivity.class));
            }
        });
    }
}
