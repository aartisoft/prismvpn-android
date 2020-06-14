package com.xlab13.prismvpn.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xlab13.prismvpn.activity.MainActivity;

public class PwrConnected extends BroadcastReceiver {

    // bROAD CAST THAT lISTEN fOR charger Connected Events

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i=new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
