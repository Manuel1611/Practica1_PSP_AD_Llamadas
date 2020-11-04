package com.example.practica1_psp_ad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityAjustes extends AppCompatActivity {

    // No están terminados los ajustes, no se como acabarlo

    TextView tvMensajeAjustes;
    SwitchCompat btSwitch;
    boolean bool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        init();
    }

    private void init() {
        btSwitch = findViewById(R.id.btSwitch);

        btSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvMensajeAjustes = findViewById(R.id.tvMensajeAjustes);
                btSwitch = findViewById(R.id.btSwitch);

                if(v.getId() == R.id.btSwitch) {
                    if(btSwitch.isChecked()) {
                        tvMensajeAjustes.setText("Se mostrará el listado de la memoria interna");
                        bool = false;
                    }else {
                        tvMensajeAjustes.setText("Se mostrará el listado de la memoria externa");
                        bool = true;
                    }
                }
            }
        });
    }
}