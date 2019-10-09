package com.example.bdpreciosproductos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {


    private static int vuelta = 0;
    private static int vuelta2 = 0;

    private static String codigoPrecio [] = new String[3000];
    private static String letra [] = new String[3000];
    private static String fecha [] = new String[3000];
    private static Float precio [] = new Float[3000];

    private static String codigoProducto [] = new String[3000];
    private static String producto [] = new String[3000];
    private static String univen [] = new String[3000];
    private static int uniem [] = new int[3000];
    private static String linea [] = new String[3000];
    private static int existencia [] = new int[3000];

    private TextView tmostrar;


    public Productos pt = new Productos(MainActivity.this);
    private long c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tmostrar = (TextView) findViewById(R.id.mostrar);
    }

    public void inicializar(View view) {
        try {
            pt.borrarBaseDeDatos();
            Toast.makeText(this, "Se borro la base datos", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "La base de datos no existe, procemos a crear.", Toast.LENGTH_SHORT).show();
        }
        try {
            pt.apertura();  // abro
            Toast.makeText(this, "Iniciamos la base de datos.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void migrar(View view) {
        try {


            String estado = Environment.getExternalStorageState();

            if (!estado.equals(Environment.MEDIA_MOUNTED)) {
                Toast.makeText(this, "No hay SD Card!", Toast.LENGTH_SHORT).show();
                finish();
            }
            try {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                File dir = Environment.getExternalStorageDirectory();
                File pt = new File(dir.getAbsolutePath() + File.separator + "precios1.csv");
                BufferedReader lee = new BufferedReader(new FileReader(pt));
                String res = "", linea;
                while ((linea=lee.readLine())!=null) {
                    if (vuelta == 0) linea=lee.readLine();
                    res = res + linea+ ";\n";
                    extraer(linea+";");
                }
                // tmostrar.setText(res);

                File pt2 = new File(dir.getAbsolutePath() + File.separator + "productos1.csv");
                BufferedReader lee2 = new BufferedReader(new FileReader(pt2));
                String res2 = "", linea2;
                while ((linea2=lee2.readLine())!=null) {
                    if (vuelta2 == 0) linea2=lee2.readLine();
                    res2 = res2 + linea2+ ";\n";
                    extraer2(linea2+";");
                }
            } catch (Exception e) {  }

            // recorremos productos
            for (int i = 0; i < vuelta2 - 1; i++) {
                c = pt.InsertarProducto(codigoProducto[i], producto[i], univen[i], uniem[i] + "", linea[i], existencia[i] + "");
            }
            // recorremos precios
            for (int i = 0; i < vuelta - 1; i++) {
                c = pt.InsertarPrecio(codigoPrecio[i], letra[i], fecha[i], Double.parseDouble(precio[i] + ""));
            }
            String r = pt.ListarProductos();
            String r1 = pt.ListarPrecios();
            String r2 = pt.BuscarByCodigo("2003");

            tmostrar.setText(r + "\n \n" + r1 + "\n \n" + r2);
            tmostrar.setText(r2);

            Toast.makeText(this, "Migración finalizada", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void consultartexto(View view) {

        Intent vd = new Intent(this, formulario.class);
        vd.putExtra("nombre", "boris");
        startActivity(vd);

    }

    public void consultarbd(View view) {
        Intent vd = new Intent(this, formularioBD.class);
        vd.putExtra("nombre", "juan");
        startActivity(vd);
    }

    public void finalizar(View view) {
        finish();
    }

    public Productos getProducto(){
        return this.pt;
    }

    public static void extraer(String cadena) {
        String palabra = "", voca;
        int pos = 0;
        for (int i=1; i<=cadena.length(); i++) {
            voca = cadena.substring(i-1, i);
            if (voca.compareTo(";")==0) {
                switch(pos) {
                    case 0:
                        codigoPrecio[vuelta] = palabra;
                        break;
                    case 1:
                        letra[vuelta] = palabra;
                        break;
                    case 2:
                        fecha[vuelta] = palabra;
                        break;
                    case 3:
                        precio[vuelta] = Float.parseFloat(palabra);
                        break;
                    default:
                        System.out.println("error dando valor a vector");
                }
                palabra = "";
                pos++;
            }else{
                palabra = palabra + voca;
            }
        }
        vuelta++;
    }
    public static void extraer2(String cadena) {
        String palabra = "", voca;
        int pos = 0;
        for (int i=1; i<=cadena.length(); i++) {
            voca = cadena.substring(i-1, i);
            if (voca.compareTo(";")==0) {
                switch(pos) {
                    case 0:
                        codigoProducto[vuelta2] = palabra;
                        break;
                    case 1:
                        producto[vuelta2] = palabra;
                        break;
                    case 2:
                        univen[vuelta2] = palabra;
                        break;
                    case 3:
                        uniem[vuelta2] = Integer.parseInt(palabra);
                        break;
                    case 4:
                        linea[vuelta2] = palabra;
                        break;
                    case 5:
                        existencia[vuelta2] = Integer.parseInt(palabra);
                        break;
                    default:
                        System.out.println("Error dando valor a vector");
                }
                palabra = "";
                pos++;
            }else{
                palabra = palabra + voca;
            }
        }
        vuelta2++;
    }
}
