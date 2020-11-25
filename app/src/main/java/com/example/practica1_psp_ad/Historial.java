package com.example.practica1_psp_ad;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.practica1_psp_ad.MainActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Historial{

    public static final String TAG = MainActivity.class.getName() + "xyzyx";

    private String nombre, numero;
    private Date fecha;
    private int year, mes, dia, hora, minutos, segundos;

    @Override
    public String toString() {
        return "Historial{" +
                "nombre='" + nombre + '\'' +
                ", year=" + year +
                ", mes=" + mes +
                ", dia=" + dia +
                ", hora=" + hora +
                ", minutos=" + minutos +
                ", segundos=" + segundos +
                ", numero=" + numero +
                '}';
    }

    /*private String csvFromDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        SimpleDateFormat format = new SimpleDateFormat("yyyy; MM; dd; HH; mm; ss;");
        //format.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        return format.format(calendar.getTime());
    }*/

    public String toCsvFilesDir() {
        return year + "; " + mes + "; " + dia + "; " + hora + "; " + minutos + "; " + segundos + "; " + numero + "; " + nombre;
    }

    public String toCsvExternalFilesDir() {
        return nombre + "; " + year + "; " + mes + "; " + dia + "; " + hora + "; " + minutos + "; " + segundos + "; " + numero;
    }

    public static Historial fromCsvFormatOne(String csv) {
        return null;
    }

    public static Historial fromCsvFormatTwo(String csv) {
        Historial historial = null;
        String[] partes = csv.split(";");
        if(partes.length == 8) {
            String name = partes[0].trim();
            String phone = partes[7].trim();
            String stringDate = partes[1].trim() + "-" + partes[2].trim() +
                    "-" + partes[3].trim() + " " + partes[4].trim() + ":" +
                    partes[5].trim() + ":" + partes[6].trim();
            Date date = null;
            try {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = format.parse(stringDate);
            } catch (ParseException e) {

            }
            //historial = new Historial(name, phone, date);
        }
        return historial;
    }

    public Historial() {
        this(null, 0, 0, 0, 0, 0, 0, null);
    }

    public Historial(String nombre, int year, int mes, int dia, int hora, int minutos, int segundos, String numero) {
        this.nombre = nombre;
        this.year = year;
        this.mes = mes;
        this.dia = dia;
        this.hora = hora;
        this.minutos = minutos;
        this.segundos = segundos;
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Historial historial = (Historial) o;
        return year == historial.year &&
                mes == historial.mes &&
                dia == historial.dia &&
                hora == historial.hora &&
                minutos == historial.minutos &&
                segundos == historial.segundos &&
                numero == historial.numero &&
                Objects.equals(nombre, historial.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, year, mes, dia, hora, minutos, segundos, numero);
    }

}