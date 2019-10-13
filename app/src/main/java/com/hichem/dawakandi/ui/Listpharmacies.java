package com.hichem.dawakandi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.hichem.dawakandi.R;

import java.util.ArrayList;

public class Listpharmacies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpharmacies);
        ArrayList<Object> l = new ArrayList<>();

        Intent i = getIntent();
        Bundle b = i.getExtras();

/*
        if( b != null){
*/
            Double lat = (Double) b.getDouble("lat");
            Double lng = (Double) b.getDouble("lng");
            Log.d("LAt", String.valueOf(lat));
            Log.d("lng", String.valueOf(lng));
/*        }else{
             b = i.getExtras();
        }
*/
    }
}
