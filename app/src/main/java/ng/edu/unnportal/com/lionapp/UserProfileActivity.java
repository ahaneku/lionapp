package ng.edu.unnportal.com.lionapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.support.design.widget.CollapsingToolbarLayout;

import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.LinearLayoutManager;

public class UserProfileActivity  extends AppCompatActivity{



    @Override
    public void onCreate(Bundle savedState){
        super.onCreate(savedState);
        setContentView(R.layout.user_profile_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout ctbl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_bar);
        ctbl.setTitleEnabled(false);




    }

}
