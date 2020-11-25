package com.example.practica1_psp_ad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    public static final String TAG = MainActivity.class.getName() + "xyzyx";
    TextView tvList;

    boolean queListadoMostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {

        Intent intent = getIntent();
        queListadoMostrar = intent.getBooleanExtra(ActivityAjustes.ESTADOSW, false);

        tvList = findViewById(R.id.tvList);

        Button btPerm = findViewById(R.id.btPerm);
        Button btAjustes = findViewById(R.id.btAjustes);

        btPerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPerm();
            }
        });

        btAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ActivityAjustes.class);
                startActivity(i);
            }
        });
    }

    private void doPerm() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int permReadPhone = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int permReadCall = checkSelfPermission(Manifest.permission.READ_CALL_LOG);
            int permReadContacts = checkSelfPermission(Manifest.permission.READ_CONTACTS);

            if((permReadPhone == PackageManager.PERMISSION_GRANTED) && (permReadCall == PackageManager.PERMISSION_GRANTED) && (permReadContacts == PackageManager.PERMISSION_GRANTED)) {
                seguir();

            }else if(permReadPhone == PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CALL_LOG)) {

                    explainPermission(REQUEST_CODE, Manifest.permission.READ_CALL_LOG);

                } else {

                    requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, REQUEST_CODE);

                }
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {

                    explainPermission(REQUEST_CODE, Manifest.permission.READ_CONTACTS);

                } else {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
                }
            }else if(permReadCall == PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {

                    explainPermission(REQUEST_CODE, Manifest.permission.READ_PHONE_STATE);

                } else {

                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);

                }
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {

                    explainPermission(REQUEST_CODE, Manifest.permission.READ_CONTACTS);

                } else {

                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE);

                }
            }else if(permReadContacts == PackageManager.PERMISSION_GRANTED) {

                if((shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) && (shouldShowRequestPermissionRationale(Manifest.permission.READ_CALL_LOG))) {

                    explainPermission(REQUEST_CODE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG);

                }else {

                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG}, REQUEST_CODE);

                }
            }else {

                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE) && shouldShowRequestPermissionRationale(Manifest.permission.READ_CALL_LOG)
                        && shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {

                    explainPermission(REQUEST_CODE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS);

                }else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {

                    explainPermission(REQUEST_CODE, Manifest.permission.READ_PHONE_STATE);

                }else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CALL_LOG)) {

                    explainPermission(REQUEST_CODE, Manifest.permission.READ_CALL_LOG);

                }else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {

                    explainPermission(REQUEST_CODE, Manifest.permission.READ_CONTACTS);

                }else {

                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
                }
            }
        }
    }

    private void explainPermission(int code, String... permissions) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Se necesitan " + permissions.length + " permisos:");
        String suma = "";
        for(String permission : permissions) {
            suma += permission + ", ";
        }

        builder.setMessage("Esta aplicación requiere de los permisos:\n\n" + suma + "\n\nPor favor, acéptalos todos para continuar.");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(permissions, code);
            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }

    private void seguir() {

        Historial historial = new Historial("Manu", 2020, 11, 16, 17, 35, 55, "123456789");

        if (saveFilesDir(historial, this)) {
            readFile(queListadoMostrar, this);
        }

        if (saveExternalFilesDir(historial, this)) {
            readFile(queListadoMostrar, this);
        }

    }

    public boolean saveFilesDir(Historial historial, Context c) {
        boolean result = true;
        File f = new File(c.getFilesDir(), "historial.csv");
        FileWriter fw = null;
        try {
            fw = new FileWriter(f, true);
            fw.write(historial.toCsvFilesDir() + "\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    public boolean saveExternalFilesDir(Historial historial, Context c) {
        boolean result = true;
        File f = new File(c.getExternalFilesDir(null), "llamadas.csv");
        FileWriter fw = null;
        try {
            fw = new FileWriter(f, true);
            fw.write(historial.toCsvExternalFilesDir() + "\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    public ArrayList<String> readFile(boolean cualMostrar, Context c) {
        ArrayList<String> lista = new ArrayList();
        File f;
        String linea;

        try {
            if (cualMostrar) {
                f = new File(c.getExternalFilesDir(null), "llamadas.csv");
            } else {
                f = new File(c.getFilesDir(), "historial.csv");
            }

            BufferedReader br = new BufferedReader(new FileReader(f));
            if (cualMostrar) {
                lista.add("LLAMADAS.CSV\n");
            } else {
                lista.add("HISTORIAL.CSV\n");
            }
            StringBuffer sb = new StringBuffer();
            while ((linea = br.readLine()) != null) {
                lista.add(linea);
                sb.append(linea);
                sb.append('\n');
                sb.append('\n');
            }

            tvList.setText(sb);
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }
}