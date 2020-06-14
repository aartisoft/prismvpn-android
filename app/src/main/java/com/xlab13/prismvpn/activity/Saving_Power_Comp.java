package com.xlab13.prismvpn.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.xlab13.prismvpn.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import net.grandcentrix.tray.AppPreferences;


public class Saving_Power_Comp extends Activity {

    DecoView arcView;
    TextView ist,sec,thir,fou,completion;
    ImageView istpic,secpic,thirpic,foupic;
    int check=0;
    InterstitialAd mInterstitialAd;
    View viewOne, viewTwo, viewThree, viewFour, viewFive;
    private String TAG = this.getClass().getSimpleName();

    private TextView textView;

    /// Power Saving Mode is Applied Compeltion Indicator Animation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.powersaving_completion);
        ist=(TextView) findViewById(R.id.ist);
        sec=(TextView) findViewById(R.id.sec);
        thir=(TextView) findViewById(R.id.thi);
        fou=(TextView) findViewById(R.id.fou);
        istpic=(ImageView) findViewById(R.id.istpic);
        secpic=(ImageView) findViewById(R.id.secpic);
        thirpic=(ImageView) findViewById(R.id.thipic);
        foupic=(ImageView) findViewById(R.id.foupic);
        completion=(TextView) findViewById(R.id.completion);

        viewOne= findViewById(R.id.viewone);
        viewTwo= findViewById(R.id.viewtwo);
        viewThree= findViewById(R.id.viewthree);
        viewFour= findViewById(R.id.viewfour);

        arcView = (DecoView) findViewById(R.id.dynamicArcView2);
        final InterstitialAd mInterstitial = new InterstitialAd(this);
        mInterstitial.setAdUnitId(getString(R.string.interstitial_ad_unit));
        //mInterstitial.loadAd(new AdRequest.Builder().build());

        mInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // TODO Auto-generated method stub
                super.onAdLoaded();
                if (mInterstitial.isLoaded()) {
                    //mInterstitial.show();
                }
            }
        });

        arcView.addSeries(new SeriesItem.Builder(Color.parseColor("#FFFFFF"))
                .setRange(0, 100, 100)
                .setInitialVisibility(false)
                .setLineWidth(12f)
                .build());

//Create data series track
        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.parseColor("#FFFFFF"))
                .setRange(0, 100, 0)
                .setLineWidth(20f)
                .build();

        SeriesItem seriesItem2 = new SeriesItem.Builder(Color.parseColor("#2a7af7"))
                .setRange(0, 100, 0)
                .setLineWidth(22f)
                .build();
//
//        int series1Index = arcView.addSeries(seriesItem1);
        int series1Index2 = arcView.addSeries(seriesItem2);

        seriesItem2.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float v, float v1) {


                Float obj = new Float(v1);
                int i = obj.intValue();
                completion.setText(i+"%");

                if(v1>=10 && v1<50)
                {
                    ist.setTextColor(Color.parseColor("#4e5457"));
                    istpic.setImageResource(R.drawable.ic_blue_dot);
//                    viewOne.setVisibility(View.GONE);


                }
                else if(v1>=50 && v1<75)
                {
                    sec.setTextColor(Color.parseColor("#4e5457"));
                    secpic.setImageResource(R.drawable.ic_blue_dot);
//                    viewTwo.setVisibility(View.GONE);
                }
                else if(v1>=75 && v1<90)
                {
                    thir.setTextColor(Color.parseColor("#4e5457"));
                    thirpic.setImageResource(R.drawable.ic_blue_dot);
//                   / viewThree.setVisibility(View.GONE);
                }
                else if(v1>=90 && v1<=100)
                {
                    fou.setTextColor(Color.parseColor("#4e5457"));
                    foupic.setImageResource(R.drawable.ic_blue_dot);
//                    viewFour.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSeriesItemDisplayProgress(float v) {

            }
        });


        arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(0)
                .setDuration(0)
                .setListener(new DecoEvent.ExecuteEventListener() {
                    @Override
                    public void onEventStart(DecoEvent decoEvent) {
//                        bottom.setText("");
//                        top.setText("");
//                        centree.setText("Optimizing...");
                    }

                    @Override
                    public void onEventEnd(DecoEvent decoEvent) {

                    }

                }).build());

        arcView.addEvent(new DecoEvent.Builder(100).setIndex(series1Index2).setDelay(1000).setListener(new DecoEvent.ExecuteEventListener() {
            @Override
            public void onEventStart(DecoEvent decoEvent) {
//                bottom.setText("");
//                top.setText("");
//                centree.setText("Optimizing...");
            }

            @Override
            public void onEventEnd(DecoEvent decoEvent) {
//                bottom.setText("Found");
//                top.setText("Storage");
//                Random ran3 = new Random();
//                ramperct.setText(ran3.nextInt(40) + 20+"%");



                AppPreferences preferences = new AppPreferences(Saving_Power_Comp.this);
                if(preferences.getBoolean("admob",false)) mInterstitialAd.show();


                youDesirePermissionCode(Saving_Power_Comp.this);


                closesall();

                check=1;



            }
        }).build());
    }

    public void closesall()
    {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
        }

//        WifiManager wifiManager = (WifiManager) getApplication().getSystemService(Context.WIFI_SERVICE);
//
//
//        boolean wifiEnabled = wifiManager.isWifiEnabled();
//        if(wifiEnabled)
//        {
//            wifiManager.setWifiEnabled(false);
//        }

        ContentResolver.setMasterSyncAutomatically(false);
    }

    public static void setAutoOrientationEnabled(Context context, boolean enabled) {
        Settings.System.putInt( context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, enabled ? 1 : 0);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    public void youDesirePermissionCode(Activity context){
        boolean permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(context);
        } else {
            permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
//            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 30);
//            setAutoOrientationEnabled(context, false);
        }
        if (permission) {
            //do your code
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 30);
            setAutoOrientationEnabled(context, false);

            finish();
        }  else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivityForResult(intent, 1);
            } else {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_SETTINGS}, 1);
            }
        }
    }
    //
    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && Settings.System.canWrite(this)){
            Log.d("TAG", "CODE_WRITE_SETTINGS_PERMISSION success");


//            Toast.makeText(getApplicationContext(),"onActivityResult",Toast.LENGTH_LONG).show();
            //do your code
            Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 30);
            setAutoOrientationEnabled(this, false);

            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do your code

            Toast.makeText(getApplicationContext(),"onRequestPermissionsResult",Toast.LENGTH_LONG).show();

            Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 30);
            setAutoOrientationEnabled(this, false);

            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(check==1)
        {
            try
            {
                Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 30);
                setAutoOrientationEnabled(this, false);
            }
            catch(Exception e)
            {
                finish();
            }
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
