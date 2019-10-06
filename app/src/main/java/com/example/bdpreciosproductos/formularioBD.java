package com.example.bdpreciosproductos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.database.sqlite.SQLiteDatabase;

public class formularioBD extends AppCompatActivity {

    private EditText ecodigo;
    private EditText edescripcion;
    private EditText eprecioVenta;
    private EditText euniVen;
    private EditText eunixEnvase;
    private EditText ecodigoLinea;
    private EditText eexistencia;

    private EditText eduracion;

    private TextView tmostrar;

    private Productos pt = new Productos(formularioBD.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_bd);

        tmostrar = (TextView)findViewById(R.id.mostrar);
        ecodigo = (EditText)findViewById(R.id.codigo);
        edescripcion = (EditText)findViewById(R.id.descripcion);
        eprecioVenta = (EditText)findViewById(R.id.precioVenta);
        euniVen = (EditText)findViewById(R.id.uniVen);
        eunixEnvase = (EditText)findViewById(R.id.unixEnvase);
        ecodigoLinea = (EditText)findViewById(R.id.codigoLinea);
        eexistencia = (EditText)findViewById(R.id.existencia);

        eduracion = (EditText)findViewById(R.id.duracion);
    }

    public void borrar(View view) {

    }

    public void buscar(View view) {
        try {

            String cad = ecodigo.getText().toString();

            String r = pt.BuscarByCodigo(cad);

            tmostrar.setText(r);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void finalizar(View view) {
        finish();
    }
}
