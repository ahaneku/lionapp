package ng.edu.unnportal.com.lionapp;


import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;


public class Settings {

    public static final String USER_PARAMS_PREF = "COM_UNN_USER_PARAM_PREF";
    public static final String USER_NAME_KEY = "user_name";
    public static final String USER_PASS_KEY = "user_password";
    public static final String USER_REG_NO_KEY = "user_reg_no";
    public static final String USER_LOGGED_IN_STATE_KEY = "logged_in";
    public static final String USER_ID_KEY = "user_id";
    public static final String DATABASE_COUNT = "db_c_state";
    public static final String FIRST_LAUNCH = "first_launch";
    public static final String USER_DEPARTMENT_KEY = "user_department";


    private Context context;
    private SharedPreferences sharedPref;


    public Settings(Context context){

        this.context = context;
        this.sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

    }


    //gets user_param setting
    public final String getUserParamValue(String key, Object defaultValue){

        String value = "";


        if(key.equals(Settings.DATABASE_COUNT) || key.equals(Settings.USER_LOGGED_IN_STATE_KEY)
                || key.equals(Settings.FIRST_LAUNCH)){

            value = String.valueOf( sharedPref.getBoolean( key, Boolean.valueOf( String.valueOf(defaultValue) )) );

        }else{

            value = sharedPref.getString(key, "null");
        }

        return value;
    }


    //sets values in the user_param setting
    public void setUserParamValue(String key, Object value){


        Editor editPref = sharedPref.edit();


        if(key.equals(Settings.DATABASE_COUNT) || key.equals(Settings.USER_LOGGED_IN_STATE_KEY)
                || key.equals(Settings.FIRST_LAUNCH) ){

            editPref.putBoolean(key, Boolean.getBoolean(String.valueOf(value)));
            editPref.apply();

        }else{
            editPref.putString(key, String.valueOf(value));
            editPref.apply();
        }



    }





    //for checking nextwork state
    public boolean checkNetwork() {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni != null && ni.isConnected()) {
            return true;
        }

        return false;
    }

}
