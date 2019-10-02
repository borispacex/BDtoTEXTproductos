package com.example.bdpreciosproductos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class formulario extends AppCompatActivity {


    private EditText ecodigo;
    private EditText edescripcion;
    private EditText eprecioVenta;
    private EditText euniVen;
    private EditText eunixEnvase;
    private EditText ecodigoLinea;
    private EditText eexistencia;

    private EditText eduracion;

    private TextView tmostrar;

    private static int vuelta = 0;
    private static int vuelta2 = 0;
    private static int vuelta3 = 0;

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

    private static int dia[] = new int[3000];
    private static int mes[] = new int[3000];
    private static int anio[] = new int[3000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        tmostrar = (TextView)findViewById(R.id.mostrar);
        ecodigo = (EditText)findViewById(R.id.codigo);
        edescripcion = (EditText)findViewById(R.id.descripcion);
        eprecioVenta = (EditText)findViewById(R.id.precioVenta);
        euniVen = (EditText)findViewById(R.id.uniVen);
        eunixEnvase = (EditText)findViewById(R.id.unixEnvase);
        ecodigoLinea = (EditText)findViewById(R.id.codigoLinea);
        eexistencia = (EditText)findViewById(R.id.existencia);

        eduracion = (EditText)findViewById(R.id.duracion);

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

            tmostrar.setText(res2);

        } catch (Exception e) {  }

    }

    public void buscar(View view) {

        long startTime = System.currentTimeMillis();

        String codigoBuscar = ecodigo.getText().toString();
        for (int i = 0; i < vuelta2 - 1; i++) {
            if (codigoBuscar.equals(codigoProducto[i])) {
                edescripcion.setText(producto[i] + "");
                euniVen.setText(univen[i] + "");
                eunixEnvase.setText(uniem[i] + "");
                ecodigoLinea.setText(linea[i] + "");
                eexistencia.setText(existencia[i] + "");
            }
        }
        String cad = "Codigo              Fecha Vigencia              Precio\n";
        String fechaMay = "";
        int diaMay = 0;
        int mesMay = 0;
        int anioMay = 0;
        String cadMay = "";
        for (int i = 0; i < vuelta - 1; i++) {
            if (codigoBuscar.equals(codigoPrecio[i])) {
                // cad = cad + codigoPrecio[i] + "           " + fecha[i] + "           " + precio[i] + "\n";
                eprecioVenta.setText(precio[i] + "");
                String fechaModi = fecha[i] + "/";
                String fechas[] = fechaModi.split("/");
                for (int j = 0; j < fechas.length; j++) {
                    if ( vuelta3 == 0) {
                        dia[vuelta3] = Integer.parseInt(fechas[j]); diaMay = dia[vuelta3]; j++;
                        mes[vuelta3] = Integer.parseInt(fechas[j]); mesMay = mes[vuelta3];  j++;
                        anio[vuelta3] = Integer.parseInt(fechas[j]); anioMay = anio[vuelta3];
                        fechaMay = fecha[j];
                        cadMay = codigoPrecio[i] + "           " + fechaMay + "           " + precio[i] + "\n";
                    } else {
                        dia[vuelta3] = Integer.parseInt(fechas[j]); j++;
                        mes[vuelta3] = Integer.parseInt(fechas[j]); j++;
                        anio[vuelta3] = Integer.parseInt(fechas[j]);
                        if (anio[j] > anioMay) {
                            diaMay = dia[vuelta3];
                            mesMay = mes[vuelta3];
                            anioMay =  anio[vuelta3];
                            fechaMay = fecha[j];
                            cadMay = codigoPrecio[i] + "           " + fechaMay + "           " + precio[i] + "\n";
                        } else {
                            if (mes[j] > mesMay && anio[j]==anioMay) {
                                diaMay = dia[vuelta3];
                                mesMay = mes[vuelta3];
                                anioMay =  anio[vuelta3];
                                fechaMay = fecha[j];
                                cadMay = codigoPrecio[i] + "           " + fechaMay + "           " + precio[i] + "\n";
                            } else {
                                if (dia[j] > diaMay && mes[j]==mesMay && anio[j]==anioMay) {
                                    diaMay = dia[vuelta3];
                                    mesMay = mes[vuelta3];
                                    anioMay =  anio[vuelta3];
                                    fechaMay = fecha[j];
                                    cadMay = codigoPrecio[i] + "           " + fechaMay + "           " + precio[i] + "\n";
                                }
                            }
                        }
                    }
                    vuelta3++;
                }
            }
        }

        tmostrar.setText(cad + cadMay);
        long difference = System.currentTimeMillis() - startTime;
        eduracion.setText(difference+ " milisegundos");
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

    public void borrar(View view) {
        ecodigo.setText("");
        edescripcion.setText("");
        eprecioVenta.setText("");
        euniVen.setText("");
        eunixEnvase.setText("");
        ecodigoLinea.setText("");
        eexistencia.setText("");
        eduracion.setText("");
    }

    public void finalizar(View view) {
        finish();
    }
}
