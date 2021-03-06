package com.example.practica1_psp_ad;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Llamadas implements Comparable<Llamadas> {

    public static final String TAG = MainActivity.class.getName() + "xyzyx";

    private String nombre, numero;
    private int year, mes, dia, hora, minutos, segundos;

    @Override
    public String toString() {
        return "Llamadas{" +
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

    public String toCsvFilesDir() {
        return year + "; " + mes + "; " + dia + "; " + hora + "; " + minutos + "; " + segundos + "; " + numero + "; " + nombre;
    }

    public String toCsvExternalFilesDir() {
        return nombre + "; " + year + "; " + mes + "; " + dia + "; " + hora + "; " + minutos + "; " + segundos + "; " + numero;
    }

    public static Llamadas fromCsvString(String linea, String separator){

        String [] trozos = linea.split(separator);

        Llamadas llamada = null;
        if(trozos.length == 8) {

            llamada = new Llamadas(trozos[0].trim(), Integer.parseInt(trozos[1].trim()) ,Integer.parseInt(trozos[2].trim()),
                    Integer.parseInt(trozos[3].trim()), Integer.parseInt(trozos[4].trim()), Integer.parseInt(trozos[5].trim()), Integer.parseInt(trozos[6].trim()), trozos[7].trim());

        }

        return llamada;

    }

    public Llamadas() {
        this(null, 0, 0, 0, 0, 0, 0, null);
    }

    public Llamadas(String nombre, int year, int mes, int dia, int hora, int minutos, int segundos, String numero) {
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
        Llamadas llamadas = (Llamadas) o;
        return year == llamadas.year &&
                mes == llamadas.mes &&
                dia == llamadas.dia &&
                hora == llamadas.hora &&
                minutos == llamadas.minutos &&
                segundos == llamadas.segundos &&
                numero == llamadas.numero &&
                Objects.equals(nombre, llamadas.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, year, mes, dia, hora, minutos, segundos, numero);
    }

    @Override
    public int compareTo(Llamadas llamadas) {

        int sort = this.nombre.compareTo(llamadas.getNombre());
        if(sort == 0){
            sort = this.dia - llamadas.getDia();
            if (sort == 0) {
                sort = this.mes - llamadas.getMes();
                if(sort == 0) {
                    sort = this.year - llamadas.getYear();
                }
            }
        }
        return sort;
    }
}