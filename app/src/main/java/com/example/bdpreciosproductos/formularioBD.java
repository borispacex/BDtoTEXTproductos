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

        try {
            pt.apertura();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrar(View view) {

    }

    public void buscar(View view) {
        long startTime = System.currentTimeMillis();
        try {
            String cad = ecodigo.getText().toString();
            String r = pt.BuscarByCodigo(cad);
            extraerByCodigo(r);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        long difference = System.currentTimeMillis() - startTime;
        eduracion.setText(difference + " milisegundos");
    }
    public void extraerByCodigo(String cadena) {
        String palabra = "", voca;
        String cadMostrar = "Codigo              Fecha Vigencia              Precio\n";
        int pos = 0;
        for (int i=1; i<=cadena.length(); i++) {
            voca = cadena.substring(i-1, i);
            if (voca.compareTo("|")==0) {
                switch(pos) {
                    case 0:
                        cadMostrar = cadMostrar + palabra + "              ";
                        break;
                    case 1:
                        edescripcion.setText(palabra);
                        break;
                    case 2:
                        euniVen.setText(palabra);
                        break;
                    case 3:
                        eunixEnvase.setText(palabra);
                        break;
                    case 4:
                        ecodigoLinea.setText(palabra);
                        break;
                    case 5:
                        eexistencia.setText(palabra);
                        break;
                    case 6:
                        cadMostrar = cadMostrar + palabra + "             ";
                        break;
                    case 7:
                        eprecioVenta.setText(palabra);
                        cadMostrar = cadMostrar + palabra;
                        break;
                    default:
                        System.out.println("error, se desbordo");
                }
                palabra = "";
                pos++;
            }else{
                palabra = palabra + voca;
            }
        }
        tmostrar.setText(cadMostrar);
    }

    public void finalizar(View view) {
        finish();
    }
}
