package com.example.broker.Actividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.broker.Datos.Accion;
import com.example.broker.Datos.Jugador;
import com.example.broker.R;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    TextView txtNombreJugador;
    TextView txtPrecioAccion;
    Button btnComprar;
    Button btnVender;
    TextView txtAcciones;
    TextView txtTotalAcciones;
    TextView txtCapital;
    TextView txtCapitalEuros;
    TextView txtTotalCapital;
    ImageView imgTendencia;

    Jugador currenJugador;
    double capitalInicial;
    String userName;
    Accion accion;

    MediaPlayer mediaPlayerError;

    Handler handler;
    Hilo hilo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombreJugador = findViewById(R.id.txtNombre);
        txtPrecioAccion = findViewById(R.id.txtPrecio);
        btnComprar = findViewById(R.id.btnCompro);
        btnVender = findViewById(R.id.btnVendo);
        txtAcciones = findViewById(R.id.txtAcciones);
        txtTotalAcciones = findViewById(R.id.txtTotalAcciones);
        txtCapital = findViewById(R.id.txtCapital);
        txtCapitalEuros = findViewById(R.id.txtCapitalEuros);
        txtTotalCapital = findViewById(R.id.txtTotalCapital);
        imgTendencia = findViewById(R.id.imgTendencia);


        Bundle extras = getIntent().getExtras();
        userName = extras.getString("nombre");

        txtNombreJugador.setText(userName);

        //preparo el sonido
        if(mediaPlayerError != null){
            mediaPlayerError.release();
        }
        mediaPlayerError = MediaPlayer.create(this, R.raw.error);

        inicioJuego();
        actualizarPantalla();
    }

    public void inicioJuego(){

        capitalInicial = 1000;
        currenJugador = new Jugador(userName, capitalInicial);
        accion = new Accion("Acciones del Sur", 150);
        handler = new Controlador();

        hilo = new Hilo(50, 1000, accion.getValor());
        hilo.start();

    }

    public void actualizarPantalla(){
        DecimalFormat formatoNum = new DecimalFormat("#.00");

        txtPrecioAccion.setText(formatoNum.format(accion.getValor()));

        txtAcciones.setText("Acciones " + currenJugador.getNumeroAcciones());

        double totalAcciones = currenJugador.getNumeroAcciones() * accion.getValor();

        txtTotalAcciones.setText(formatoNum.format(totalAcciones));

        txtCapitalEuros.setText(formatoNum.format(currenJugador.getCapital()));

        double totalCapital = totalAcciones + currenJugador.getCapital();

        txtTotalCapital.setText(formatoNum.format(totalCapital));

        if(accion.getTendencia() >= 0){
            imgTendencia.setImageResource(R.drawable.ic_flecha_sube);
        }else{
            imgTendencia.setImageResource(R.drawable.ic_flecha_baja);
        }
    }


    public class Hilo extends Thread {

        int maximo;
        int tiempo;
        double valorAccion;
        double cotaVariacion;

        public Hilo(int n, int t, double valorAccion){
            maximo = n;
            tiempo = t;
            this.valorAccion = valorAccion;
            cotaVariacion = valorAccion / 2;
        }
        public void run(){
            //instrucciones a ejecutar
            boolean finJuego = false;
            Message msg;
            Bundle b;
            for(int i = 0 ; i <= maximo ; i++){
                try{
                    Thread.sleep(tiempo);
                }catch (InterruptedException e){

                }
                //genero un número aleatorio entre 0 y el 10% del valor de la accion
                double valorAleatorio = Math.random()*(cotaVariacion+cotaVariacion)+(-cotaVariacion);
                Log.i("ramdom", valorAleatorio + " ");
                //construye el mensaje para enviar al controlador
                msg = handler.obtainMessage();
                //inserta datos en el mensaje
                b = new Bundle();
                b.putDouble("valor", valorAleatorio);
                b.putBoolean("finJuego", finJuego);
                msg.setData(b);

                //envia el mensaje
                handler.sendMessage(msg);
            }
            //se ha terminado el juego
            finJuego = true;
            msg = handler.obtainMessage();
            b = new Bundle();
            b.putBoolean("finJuego", finJuego);
            msg.setData(b);
            handler.sendMessage(msg);
        }
    }


    //controlador para recibir mensajes del hilo
    class Controlador extends Handler {

        @Override
        public void handleMessage(Message msg){
            double variacion;
            variacion = msg.getData().getDouble("valor");
            boolean finJuego = msg.getData().getBoolean("finJuego");

            if(!finJuego){//el juego no ha terminado
                //comprobar que el valor de la acción no baje de 10
                if((accion.getValor() + variacion) > 10){
                    accion.setValor(accion.getValor() + variacion);
                }
                accion.setTendencia(variacion);
                actualizarPantalla();
            }else{//el juego ha terminado
                terminarJuego();
            }

        }
    }

    public void comprar(View view){
        //comprobar si tiene dinero para comprar
        if(currenJugador.getCapital() >= accion.getValor()){
            currenJugador.setNumeroAcciones(currenJugador.getNumeroAcciones()+1);
            currenJugador.setCapital(currenJugador.getCapital() - accion.getValor());
            actualizarPantalla();
        }else{
            Toast.makeText(MainActivity.this, "No tienes suficiente dinero para comprar", Toast.LENGTH_LONG).show();
            //sonido error
            mediaPlayerError.seekTo(0);
            mediaPlayerError.start();
        }

    }

    public void vender(View view){
        //comprobar si tiene acciones para vender
        if(currenJugador.getNumeroAcciones() > 0){
            currenJugador.setNumeroAcciones(currenJugador.getNumeroAcciones()-1);
            currenJugador.setCapital(currenJugador.getCapital() + accion.getValor());
            actualizarPantalla();
        }else{
            Toast.makeText(MainActivity.this, "No tienes acciones para vender", Toast.LENGTH_LONG).show();
            //sonido error
            mediaPlayerError.seekTo(0);
            mediaPlayerError.start();
        }

    }

    public void terminarJuego(){

        //accedo a los datos guardados en preferencias
        SharedPreferences prefs =
                getSharedPreferences(
                        "recordBroker",
                        Context.MODE_PRIVATE);
        int record = prefs.getInt("record", 0);
        String resultado = "";
        //compruebo si se ha batido un record
        if(currenJugador.getCapital() > record){//se ha batido el record
            resultado = "ganador";
            record = (int) currenJugador.getCapital();
            //guardo el nuevo record en preferencias
            SharedPreferences pref = getSharedPreferences(
                    "recordBroker",
                    Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("record", (int) currenJugador.getCapital());
            editor.commit();
        }else{//no se ha batido el record
            resultado = "perdedor";
        }
        Intent intent = new Intent(MainActivity.this, FinJuegoActivity.class);
        intent.putExtra("resultado", resultado);
        intent.putExtra("record", record);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayerError != null){
            mediaPlayerError.release();
        }

    }
}
