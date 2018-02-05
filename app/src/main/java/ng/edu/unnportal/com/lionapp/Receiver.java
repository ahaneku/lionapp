package ng.edu.unnportal.com.lionapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver{


    private Intent unnService;

    @Override
    public void onReceive(Context context, Intent data){

        if(data.getAction().equalsIgnoreCase(Intent.ACTION_PACKAGE_FIRST_LAUNCH)
                || data.getAction().equalsIgnoreCase(Intent.ACTION_PACKAGE_DATA_CLEARED)
                ){

            Log.e("Cjay", "justed received broadCast : package_first_launch");
            Toast.makeText(context, "justed received broadCast : package_first_launch", Toast.LENGTH_SHORT).show();

            context.startService(unnService);
            context.startActivity(new Intent("ng.edu.unnportal.com.lionapp.LoginActivity"));

        }
        else if(data.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)){

            Toast.makeText(context, "service started in LionApp.", Toast.LENGTH_SHORT).show();
            unnService = new Intent(context, UnnService.class);

                context.startService(unnService);
        }

    }
}
