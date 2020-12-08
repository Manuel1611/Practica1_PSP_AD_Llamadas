package com.example.practica1_psp_ad;

import java.util.Comparator;

public class Comparar implements Comparator<Llamadas> {
    @Override
    public int compare(Llamadas llamada1, Llamadas llamada2) {

        int sort = llamada1.getNombre().compareTo(llamada2.getNombre());
        if(sort == 0) {
            sort = llamada1.getDia() - llamada2.getDia();
            if (sort == 0) {
                sort = llamada1.getMes() - llamada2.getMes();
                if (sort == 0) {
                    sort = llamada1.getYear() - llamada2.getYear();
                }
            }
        }
        return sort;
    }
}
