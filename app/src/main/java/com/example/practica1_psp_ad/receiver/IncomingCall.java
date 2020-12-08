package com.example.practica1_psp_ad.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.practica1_psp_ad.Comparar;
import com.example.practica1_psp_ad.Llamadas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static com.example.practica1_psp_ad.MainActivity.TAG;

public class IncomingCall extends BroadcastReceiver {

    Context c;
    Llamadas llamadas;
    private String nombre;
    private static String numero;
    int dia, mes, year, hora, minutos, segundos;

    @Override
    public void onReceive(Context context, Intent intent) {
        String estado = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        c = context;

        if(estado.equals(TelephonyManager.EXTRA_STATE_RINGING)) {

            numero = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

        } else if(estado.equals(TelephonyManager.EXTRA_STATE_IDLE)) {

            runHebra();

        }

    }

    private void runHebra() {

        Thread hebraLlamadas = new Thread() {
            @Override
            public void run() {
                super.run();

                Calendar calendar = Calendar.getInstance();

                dia = calendar.get(Calendar.DAY_OF_MONTH);
                mes = calendar.get(Calendar.MONTH) + 1;
                year = calendar.get(Calendar.YEAR);
                hora = calendar.get(Calendar.HOUR);
                minutos = calendar.get(Calendar.MINUTE);
                segundos = calendar.get(Calendar.SECOND);

                nombre = getNombre();
                //Log.v("XYZ", numero + " " + year + " " + mes + " " + dia + " " + hora + " " + minutos + " " + segundos + " " + nombre);
                llamadas = new Llamadas(nombre, year, mes, dia, hora, minutos, segundos, numero);
                saveFilesDir(llamadas, c);
                saveExternalFilesDir(llamadas, c);

            }
        };
        hebraLlamadas.start();
    }

    private String getNombre() {

        nombre = "Desconocido";

        Cursor cursor = c.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while(cursor.moveToNext()) {

            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String nom = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            Cursor cursor2 = c.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", new String[]{id}, null);

            while (cursor2.moveToNext()) {
                String phoneNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String num = "";

                for(int i =0;i<phoneNumber.length();i++){
                    if(Character.isDigit(phoneNumber.charAt(i))){
                        num += phoneNumber.charAt(i);
                    }
                }
                if(num.equals(numero)){
                    nombre = nom;
                }
            }
            cursor2.close();

        }

        return nombre;

    }

    private List<Llamadas> getLlamadas() {
        List<Llamadas> listaDeLlamadas = new ArrayList<>();
        listaDeLlamadas.clear();
        File f = new File(c.getExternalFilesDir(null), "llamadas.csv");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null) {
                listaDeLlamadas.add( Llamadas.fromCsvString(linea, "; "));
            }
            br.close();
        }catch (IOException e){

        }
        return listaDeLlamadas;
    }

    public boolean saveFilesDir(Llamadas llamadas, Context c) {
        boolean result = true;
        File f = new File(c.getFilesDir(), "historial.csv");
        FileWriter fw = null;
        try {
            fw = new FileWriter(f, true);
            fw.write(llamadas.toCsvFilesDir() + "\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    public boolean saveExternalFilesDir(Llamadas llamadas, Context c) {
        List<Llamadas> llamad = getLlamadas();
        llamad.add(llamadas);
        Collections.sort(llamad);
        boolean result = true;
        File f = new File(c.getExternalFilesDir(null), "llamadas.csv");
        FileWriter fw = null;
        try {
            fw = new FileWriter(f);

            for(Llamadas llam: llamad) {
                fw.write(llam.toCsvExternalFilesDir() + "\n");
            }

            fw.flush();
            fw.close();
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

}
