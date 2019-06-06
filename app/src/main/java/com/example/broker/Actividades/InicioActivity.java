package com.example.broker.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.broker.R;

public class InicioActivity extends AppCompatActivity {

    EditText etNombre;
    Button btnJugar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        etNombre = findViewById(R.id.etNombre);
        btnJugar =  findViewById(R.id.btnJugar);

        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNombre.getText().toString().isEmpty()){
                    Toast.makeText(InicioActivity.this, "Introduce tu nombre", Toast.LENGTH_LONG).show();
                    etNombre.setError("Nombre");
                }else{
                    Intent intent = new Intent(InicioActivity.this, MainActivity.class);
                    intent.putExtra("nombre", etNombre.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
