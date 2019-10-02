package com.example.bdpreciosproductos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Productos {


    public static final String NBD = "Ventas.db";

    public static final String COD = "codigo";
    public static final String DES = "descripcion";
    public static final String UNV = "univen";
    public static final String PRU = "preciou";
    private static final String NTB = "productos";  // nombre de la tabla




    private static final String NTB1 = "precios";  // nombre de la tabla

    private static final int VERSION = 1;        // cambiar la version, cada vez que se hace un cambio

    private Creactua Control;
    private final Context nContexto;
    private SQLiteDatabase pBD;

    private static class Creactua extends SQLiteOpenHelper {
        public Creactua( Context context) {
            super(context, NBD, null, VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + NTB + " (" +
                    COD + " INTEGER PRIMARY KEY, " +
                    DES + " TEXT NOT NULL, " +
                    UNV + " TEXT NOT NULL, " +
                    PRU + " DECIMAL(6,2) NOT NULL);");
            db.execSQL("CREATE TABLE " + NTB1 + " (" +
                    COD + " INTEGER PRIMARY KEY, " +
                    DES + " TEXT NOT NULL, " +
                    UNV + " TEXT NOT NULL, " +
                    PRU + " DECIMAL(6,2) NOT NULL);");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int a, int n) {    // a: antigua n: nueva
            db.execSQL("DROP TABLE IF EXISTS " + NTB);
            onCreate(db);
        }
    }
    public Productos(Context c) {
        nContexto = c;
    }
    public Productos apertura() throws Exception {
        Control = new Creactua(nContexto);
        pBD = Control.getWritableDatabase();
        return this;
    }
    public void cerrar() {
        Control.close();
    }
    public long Insertar(String qCod, String qDes, String qUnv, Double qPrv) {
        ContentValues reg = new ContentValues();
        reg.put(COD, qCod);
        reg.put(DES, qDes);
        reg.put(UNV, qUnv);
        reg.put(PRU, qPrv);
        return pBD.insert(NTB, null, reg);
    }
    public String Listar() {
        String[] columnas = new String[] {COD, DES, PRU};
        Cursor c = pBD.query(NTB, columnas,null,null,null,null,null);
        String res = "";
        int iCod = c.getColumnIndex(COD);
        int iDes = c.getColumnIndex(DES);
        int iPru = c.getColumnIndex(PRU);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            res = res + c.getString(iCod) + " " + c.getString(iDes) + " " + c.getString(iPru) + "\n";
        }
        return  res;
    }
    public boolean borrarBaseDeDatos() {
        pBD.execSQL("delete from " + NTB);
        return true;
    }

}
