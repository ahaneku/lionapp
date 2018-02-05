package ng.edu.unnportal.com.lionapp;


import android.app.Service;
import android.content.Intent;
import android.content.Context;

import android.os.IBinder;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;



public class UnnService extends Service {

    private Context context;
    private Settings setting;

    @Override
    public void onCreate(){

        Toast.makeText(context, "service started.", Toast.LENGTH_SHORT).show();
        context = getBaseContext();
        setting = new Settings(getBaseContext());

    }


    @Override
    public IBinder onBind(Intent intent){

        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        TimerTask tTask = new TimerTask(){

            @Override
            public void run(){


                if(setting.checkNetwork()){

                    if(!LionApp.downloadState_getFeeds){
                        Intent serviceUrl = new Intent(context, IService.class);
                        serviceUrl.putExtra(IService.FROM, LionApp.GET_FROM_URL);
                        startService(serviceUrl);
                    }
                }

            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(tTask, 10000, 120000);

        return START_STICKY;
    }


    @Override
    public void onDestroy(){

    }


}
