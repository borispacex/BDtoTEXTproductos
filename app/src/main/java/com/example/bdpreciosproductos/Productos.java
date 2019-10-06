package com.example.bdpreciosproductos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Productos {


    public static final String NBD = "Laboratorio.db";

    public static final String COD = "codigo";
    public static final String PRO = "producto";
    public static final String UNV = "univen";
    public static final String UNM = "uniem";
    public static final String LINE = "linea";
    public static final String EXI = "preciou";

    private static final String NTB = "productos";  // nombre de la tabla


    public static final String ID = "identificador";
    public static final String CODP = "codigop";
    public static final String TIP = "tipop";
    public static final String FEC = "fechap";
    public static final String PRE = "preciop";

    private static final String NTB1 = "precios";  // nombre de la tabla

    private static final int VERSION = 16;        // cambiar la version, cada vez que se hace un cambio

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
                    PRO + " TEXT NOT NULL, " +
                    UNV + " TEXT NOT NULL, " +
                    UNM + " INTEGER NOT NULL, " +
                    LINE + " INTEGER NOT NULL, " +
                    EXI + " INTEGER NOT NULL);");

            db.execSQL("CREATE TABLE " + NTB1 + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CODP + " INTEGER NOT NULL, " +
                    TIP + " TEXT NOT NULL, " +
                    FEC + " DATETIME NOT NULL, " +
                    PRE + " DECIMAL(6,2) NOT NULL);");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int a, int n) {    // a: antigua n: nueva
            db.execSQL("DROP TABLE IF EXISTS " + NTB);
            db.execSQL("DROP TABLE IF EXISTS " + NTB1);
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
    public long InsertarProducto(String qCod, String qPro, String qUnv, String qUnm, String qLine, String qExi) {
        ContentValues reg = new ContentValues();
        reg.put(COD, qCod);
        reg.put(PRO, qPro);
        reg.put(UNV, qUnv);
        reg.put(UNM, qUnm);
        reg.put(LINE, qLine);
        reg.put(EXI, qExi);
        return pBD.insert(NTB, null, reg);
    }
    public long InsertarPrecio(String qCod, String qTip, String qFec, Double qPre) {
        ContentValues reg = new ContentValues();
        reg.put(CODP, qCod);
        reg.put(TIP, qTip);
        reg.put(FEC, qFec);
        reg.put(PRE, qPre);
        return pBD.insert(NTB1, null, reg);
    }
    public String ListarProductos() {
        String[] columnas = new String[] {COD, PRO, UNV, UNM, LINE, EXI};
        Cursor c = pBD.query(NTB, columnas,null,null,null,null,null);
        String res = "";
        int iCod = c.getColumnIndex(COD);
        int iPro = c.getColumnIndex(PRO);
        int iUnv = c.getColumnIndex(UNV);
        int iUnm = c.getColumnIndex(UNM);
        int iLine = c.getColumnIndex(LINE);
        int iExi = c.getColumnIndex(EXI);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            res = res + c.getString(iCod) + " " + c.getString(iPro) + " " + c.getString(iUnv) + " " + c.getString(iUnm)
                    + " " + c.getString(iLine) + " " + c.getString(iExi) + "\n";
        }
        return  res;
    }
    public String BuscarByCodigo(String codigo) {
        Cursor c = pBD.rawQuery("SELECT * FROM " + NTB + " WHERE codigo='" + codigo +"'", null);
        String res = "";
        int iCod = c.getColumnIndex(COD);
        int iPro = c.getColumnIndex(PRO);
        int iUnv = c.getColumnIndex(UNV);
        int iUnm = c.getColumnIndex(UNM);
        int iLine = c.getColumnIndex(LINE);
        int iExi = c.getColumnIndex(EXI);

        if (c.moveToFirst()) {
            res = res + c.getString(iCod) + "|" + c.getString(iPro) + "|" + c.getString(iUnv) + "|" + c.getString(iUnm)
                    + "|" + c.getString(iLine) + "|" + c.getString(iExi) + "|";
        }

        Cursor c1 = pBD.rawQuery("SELECT preciop, MAX(substr(fechap, 7, 4)||'/'||substr(fechap, 4,2)||'/'||substr(fechap, 1,2)) AS fecha FROM " + NTB1 + " WHERE codigop='" + codigo +"'", null);
        int iFec = c1.getColumnIndex("fecha");
        int iPre = c1.getColumnIndex("preciop");
        if (c1.moveToFirst()) {
            res = res + c1.getString(iFec) + "|" + c1.getDouble(iPre) + "|";
        }

        return  res;
    }
    public String ListarPrecios() {
        String[] columnas = new String[] {CODP, TIP, FEC, PRE};
        Cursor c = pBD.query(NTB1, columnas,null,null,null,null,null);
        String res = "";
        int iCod = c.getColumnIndex(CODP);
        int iTip = c.getColumnIndex(TIP);
        int iFec = c.getColumnIndex(FEC);
        int iPre = c.getColumnIndex(PRE);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            res = res + c.getString(iCod) + " " + c.getString(iTip) + " " + c.getString(iFec) + " " + c.getString(iPre) + "\n";
        }
        return  res;
    }
    public boolean borrarBaseDeDatos() {
        pBD.execSQL("delete from " + NTB);
        pBD.execSQL("delete from " + NTB1);
        return true;
    }

}
