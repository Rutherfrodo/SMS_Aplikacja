package com.example.frodo.sms;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service  {

    private Toast toast;
    private Timer timer;
    private TimerTask timerTask;
    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            GregorianCalendar calendar = new GregorianCalendar();
            int Godzina = calendar.get(Calendar.HOUR_OF_DAY);
            int Minuta = calendar.get(Calendar.MINUTE);

            if (Godzina == Minuta ) {
                if(Godzina < 10 ) {
                    sms("0"+Godzina + ":" +"0" +Minuta);
                    showToast("SMS");
                }else{
                    sms(Godzina + ":" + Minuta);
                    showToast("SMS");
                }
            }else{
                showToast("Sprawdzam godzine");
            }
        }
    }
    private void showToast(String text) {
        toast.setText(text);
        toast.show();
    }

    private void writeToLogs(String message) {
        Log.d("HelloServices", message);
    }

    void sms(String godzina) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("666952185",null,godzina,null,null);
        Log.d("Wiadomosc", "zostala wyslana");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        writeToLogs("Called onCreate() method.");
        timer = new Timer();
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        writeToLogs("Called onStartCommand() methond");
        clearTimerSchedule();
        initTask();
        timer.scheduleAtFixedRate(timerTask, 4*1000, 40000);
        showToast("Your service has been started");

        return super.onStartCommand(intent, flags, startId);
    }

    private void clearTimerSchedule() {
        if(timerTask != null) {
            timerTask.cancel();
            timer.purge();
        }
    }

    private void initTask() {
        timerTask = new MyTimerTask();
    }

    @Override
    public void onDestroy() {
        writeToLogs("Called onDestroy() method");
        clearTimerSchedule();
        showToast("Your service has been stopped");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
