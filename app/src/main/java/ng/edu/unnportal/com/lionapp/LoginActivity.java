package ng.edu.unnportal.com.lionapp;

import ng.edu.unn.UnnParser.UnnParser;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.ProgressBar;
import android.app.ProgressDialog;

import android.os.AsyncTask;
import android.widget.Toast;

import android.provider.Settings;

public class LoginActivity extends AppCompatActivity {


    public static final String REG_NO_PATTERN = "[0-9]{4}/[0-9]{6}";
    public static final String PASS_PATTERN = "[0-9a-zA-Z]+";
    public static final Pattern RegNoPat = Pattern.compile(REG_NO_PATTERN);
    public static final Pattern PassPat = Pattern.compile(PASS_PATTERN);

    private static EditText LoginRegNo;
    private static EditText LoginPassword;
    private static Button Login;
    private static ProgressBar loginProg;

    private UnnParser unnParser;
    private ng.edu.unnportal.com.lionapp.Settings settings;

    private Thread loginThread;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.login_activity);


      /*  settings = new ng.edu.unnportal.com.lionapp.Settings(getBaseContext());

        boolean firstLaunch = Boolean.valueOf(settings.getUserParamValue(ng.edu.unnportal.com.lionapp.Settings.FIRST_LAUNCH, String.valueOf(true)));

        Log.e("Cjay", "first launch : "+firstLaunch );
        if (firstLaunch) {

            settings.setUserParamValue(ng.edu.unnportal.com.lionapp.Settings.FIRST_LAUNCH, false);

        } else {

            moveToNext();
        }   */


        LoginRegNo = (EditText) findViewById(R.id.loginRegNo);
        LoginPassword = (EditText) findViewById(R.id.loginPassword);
        Login = (Button) findViewById(R.id.login);

        Login.setEnabled(false);
        loginProg = (ProgressBar) findViewById(R.id.loginProg);

        loginProg.setVisibility(View.GONE);
        loginProg.setDrawingCacheBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));


        Watcher watcher = new Watcher();

        LoginRegNo.addTextChangedListener(watcher);
        LoginPassword.addTextChangedListener(watcher);


        Login.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {


                if (settings.checkNetwork()) {

                    new LogInTask().execute(LoginRegNo.getText().toString(), LoginPassword.getText().toString());

                    Login.setEnabled(false);

                } else {

                    showState(view, getResources().getString(R.string.no_connect),
                            getResources().getString(R.string.on_connect), Color.green(R.color.colorPrimary));

                    moveToNext();

                }


            }
        });

        showState(Login, "Welcome To LionApp ...", null, -1);


    }


    public void moveToNext() {


        Intent mainActivity = new Intent(LoginActivity.this, LionApp.class);
        startActivity(mainActivity);

        finish();
    }


    public class LogInTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {

            loginProg.setVisibility(View.VISIBLE);


        }

        @Override
        protected String doInBackground(String... data) {

            unnParser = UnnParser.getInstance();


            String respond = unnParser.logIn(data[0], data[1], 1);

            return respond;
        }


        @Override
        protected void onPostExecute(String data) {

            Toast.makeText(getBaseContext(), data, Toast.LENGTH_SHORT).show();

            loginProg.setVisibility(View.GONE);


            moveToNext();
        }
    }


    public static boolean recognizePat(String reg_no) {

        Matcher matcher = RegNoPat.matcher(reg_no);

        return matcher.matches();
    }


    public static class Watcher implements TextWatcher {


        @Override
        public void onTextChanged(CharSequence txt, int a, int b, int c) {


            if (LoginRegNo.getText().toString().matches(REG_NO_PATTERN) && LoginPassword.getText().toString().matches(PASS_PATTERN)) {

                Login.setEnabled(true);
            } else {
                Login.setEnabled(false);
            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


    //method to show application state in snackBar
    public void showState(View from, String value, String action, int actionColor) {

        Snackbar snackB = null;

        if (action == null) {

            snackB = Snackbar.make(from, value, Snackbar.LENGTH_LONG);
            snackB.show();
        } else {

            snackB = Snackbar.make(from, value, Snackbar.LENGTH_LONG);

            if (actionColor != -1) {

                snackB.setActionTextColor(actionColor);
            }

            snackB.setAction(action, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent onNetwrk = new Intent(Settings.ACTION_WIRELESS_SETTINGS);

                    if (onNetwrk.resolveActivity(getPackageManager()) != null) {

                        startActivity(onNetwrk);
                    }

                }
            });

            snackB.show();
        }

    }
} 

