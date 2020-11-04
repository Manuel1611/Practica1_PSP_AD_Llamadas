package com.example.practica1_psp_ad.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.practica1_psp_ad.MainActivity;

import static com.example.practica1_psp_ad.MainActivity.TAG;

public class IncomingCall extends BroadcastReceiver {

    // Contiene el número de la llamada entrante
    public String phoneNumber;

    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                Log.v(TAG, incomingNumber);
                phoneNumber = incomingNumber;
            }
        },PhoneStateListener.LISTEN_CALL_STATE);

    }

}
