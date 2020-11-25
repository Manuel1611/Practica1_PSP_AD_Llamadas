package com.example.practica1_psp_ad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityAjustes extends AppCompatActivity {

    TextView tvMensajeAjustes;

    SwitchCompat btSwitch;

    boolean stateSwitch;

    SharedPreferences preferences;

    public static final String ESTADOSW = "estadoSw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        init();
    }

    private void init() {

        preferences = getSharedPreferences("PREFS", 0);
        stateSwitch = preferences.getBoolean("estado", false);

        tvMensajeAjustes = findViewById(R.id.tvMensajeAjustes);

        tvMensajeAjustes.setText("Apagado: " + "\n" + "Se mostrará el listado de la memoria interna." + "\n\n" + "Encendido: " + "\n" + "Se mostrará el listado de la memoria externa.");

        btSwitch = findViewById(R.id.btSwitch);

        btSwitch.setChecked(stateSwitch);

        btSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateSwitch = !stateSwitch;
                btSwitch.setChecked(stateSwitch);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("estado", stateSwitch);
                editor.apply();
            }
        });

        Button btGuardar = findViewById(R.id.btGuardar);

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAjustes.this, MainActivity.class);
                intent.putExtra(ESTADOSW, stateSwitch);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ActivityAjustes.this, MainActivity.class);
        intent.putExtra(ESTADOSW, stateSwitch);
        startActivity(intent);
    }
}