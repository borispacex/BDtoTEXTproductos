package com.example.bdpreciosproductos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void inicializar(View view) {

    }

    public void migrar(View view) {

    }

    public void consultartexto(View view) {

        Intent vd = new Intent(this, formulario.class);
        vd.putExtra("peso", 10);
        vd.putExtra("altura", 10);
        vd.putExtra("nombre", "boris");
        startActivity(vd);

    }

    public void consultarbd(View view) {
        Intent vd = new Intent(this, formulario.class);
        vd.putExtra("peso", 15);
        vd.putExtra("altura", 20);
        vd.putExtra("nombre", "juan");
        startActivity(vd);
    }

    public void finalizar(View view) {
        finish();
    }
}
