package com.example.frodo.sms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    TextView mTextView;
    Button mButtonStart;
    Button mButtonStop;
   // GregorianCalendar calendar = new GregorianCalendar();
   // int Godzina = calendar.get(Calendar.HOUR_OF_DAY);
   // int Minuta = calendar.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text);
        mButtonStart = (Button) findViewById(R.id.button);
        mButtonStop = (Button) findViewById(R.id.button2);

        if (checkSelfPermission(Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_DENIED) {
            Log.d("permission", "permission denied to SEND_SMS - requesting it");
            String[] permissions = {Manifest.permission.SEND_SMS};
            requestPermissions(permissions, PackageManager.PERMISSION_GRANTED);
        }
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyService();

            }
        });

        mButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMyService();
            }
        });

    }
    private void startMyService() {
        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
    }

    private void stopMyService() {
        Intent serviceIntent = new Intent(this, MyService.class);
        stopService(serviceIntent);
    }

}
