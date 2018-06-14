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
    public int Godzina;
    public int Minuta;
    public String czas;
    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            GregorianCalendar calendar = new GregorianCalendar();
            Godzina = calendar.get(Calendar.HOUR_OF_DAY);
            Minuta = calendar.get(Calendar.MINUTE);
            Godzina = Godzina + 1;

            if (Godzina == Minuta ) {
                if(Godzina < 10 ) {
                    sms("0"+Godzina + ":" +"0" +Minuta+" czasu Greckiego");
                    showToast("SMS");
                   //mTextView.setText(Godzina+ ":"+Minuta+" czasu Greckiego");
                }else if(Godzina==24){
                    Godzina=0;
                    sms(Godzina + ":" + Minuta +" czasu Greckiego");
                    showToast("SMS");
                    //mTextView.setText(Godzina+ ":"+Minuta+" czasu Greckiego");
                }else{
		    sms(Godzina + ":" + Minuta +" czasu Greckiego");
                    showToast("SMS");
                }
            }else{
                showToast("Sprawdzam godzine");
            }


        }
    }
    public String PanelGodziny(){
        final int minuta = this.Minuta;
        final int godzina = this.Godzina;
        String timy =godzina + ":"+ minuta;
        return timy;
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
        sms.sendTextMessage("xxxxxxxxx",null,godzina,null,null); // xxxxxxxxx nr telefonu
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
        timer.scheduleAtFixedRate(timerTask, 4*1000, 60000);
        showToast("Zaczynam wysyłać SMSki");

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
        showToast("SMSki wyłączone");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
