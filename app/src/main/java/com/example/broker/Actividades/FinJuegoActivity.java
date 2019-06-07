package com.example.broker.Actividades;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.broker.R;

public class FinJuegoActivity extends AppCompatActivity {

    TextView txtMensajeFinJuego;
    TextView txtMensajeFinJuego2;
    TextView txtRecord;
    ImageView imgTriunfo;
    Button btnJugarOtraVez;
    Button btnSalir;

    MediaPlayer mediaPlayerGanar;
    MediaPlayer mediaPlayerPerder;

    int record;
    String resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_juego);

        txtMensajeFinJuego = findViewById(R.id.txtMensajeFinJuego);
        txtMensajeFinJuego2 = findViewById(R.id.txtMensajeFinJuego2);
        txtRecord = findViewById(R.id.txtRecord);
        imgTriunfo = findViewById(R.id.imgTriunfo);
        btnJugarOtraVez = findViewById(R.id.btnJugarOtraVez);
        btnSalir = findViewById(R.id.btnSalir);

        Bundle extras = getIntent().getExtras();
        resultado = extras.getString("resultado");
        record = extras.getInt("record", 0);

        if(resultado.equals("ganador")){
            juegoGanado();
        }else{
            juegoPerdido();
        }

        btnJugarOtraVez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinJuegoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }



    public void juegoGanado(){
        txtRecord.setText(String.valueOf(record));

        if(mediaPlayerGanar != null){
            mediaPlayerGanar.release();
        }
        mediaPlayerGanar = MediaPlayer.create(this, R.raw.ganar);
        mediaPlayerGanar.seekTo(0);
        mediaPlayerGanar.start();
    }

    public void juegoPerdido(){
        txtMensajeFinJuego.setText(R.string.el_record_es);
        txtRecord.setText(String.valueOf(record));
        txtMensajeFinJuego2.setText(R.string.intentalo_de_nuevo);
        imgTriunfo.setImageResource(R.drawable.banda);

        if(mediaPlayerPerder != null){
            mediaPlayerPerder.release();
        }
        mediaPlayerPerder = MediaPlayer.create(this, R.raw.perder);
        mediaPlayerPerder.seekTo(0);
        mediaPlayerPerder.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayerGanar != null){
            mediaPlayerGanar.release();
        }

        if(mediaPlayerPerder != null){
            mediaPlayerPerder.release();
        }
    }
}
