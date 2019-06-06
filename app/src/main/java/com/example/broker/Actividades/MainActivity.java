package com.example.broker.Actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.broker.Datos.Accion;
import com.example.broker.Datos.Jugador;
import com.example.broker.R;

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

    Jugador currenJugador;
    double capitalInicial;
    String userName;
    Accion accion;

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


        Bundle extras = getIntent().getExtras();
        userName = extras.getString("nombre");

        txtNombreJugador.setText(userName);
        inicioJuego();
        actualizarPantalla();
    }

    public void inicioJuego(){
        capitalInicial = 1000;
        currenJugador = new Jugador(userName, capitalInicial);
        accion = new Accion("Acciones del Sur", 150);


    }

    public void actualizarPantalla(){
        txtPrecioAccion.setText(accion.getStringValor());
        txtAcciones.setText("Acciones " + currenJugador.getNumeroAcciones());
        double totalAcciones = currenJugador.getNumeroAcciones() * accion.getValor();
        txtTotalAcciones.setText(String.valueOf(totalAcciones));
        txtCapitalEuros.setText(String.valueOf(currenJugador.getCapital()));
        double totalCapital = totalAcciones + currenJugador.getCapital();
        txtTotalCapital.setText(String.valueOf(totalCapital));
    }
}
