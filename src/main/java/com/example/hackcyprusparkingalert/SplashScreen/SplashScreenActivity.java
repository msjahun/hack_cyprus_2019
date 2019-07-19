package com.example.hackcyprusparkingalert.SplashScreen;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.hackcyprusparkingalert.DataStructures.MobileUser;
import com.example.hackcyprusparkingalert.GetttingStarted.GettingStartedActivity;
import com.example.hackcyprusparkingalert.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import io.fabric.sdk.android.Fabric;

public class SplashScreenActivity extends AppCompatActivity {
    protected int _splashTime = 2000;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "SplashScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)  // Enables Crashlytics debugger
                .build();
        Fabric.with(fabric);


        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            //   Toast.makeText(AccountActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();

            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent mIntent = new Intent(SplashScreenActivity.this, GettingStartedActivity.class); //'this' is Activity A
                    mIntent.putExtra("FROM_ACTIVITY", "SplashScreenActivity");
                    startActivity(mIntent);

                    // startActivity(new Intent(SplashScreenActivity.this,GettingStartedActivity.class));

                    finish();
                }
            }, secondsDelayed * 2000);
        }else{


            FirebaseDatabase database = FirebaseDatabase.getInstance("https://parking-alert-main.firebaseio.com/");
            DatabaseReference getUserRef = database.getReference("Users").child(mAuth.getCurrentUser().getUid());

            getUserRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Log.d(TAG, "onDataChange: datasnapshot is" + dataSnapshot);
                    Crashlytics.log(Log.DEBUG, TAG, "onDataChange: datasnapshot is" + dataSnapshot);
                    MobileUser mobileUser = dataSnapshot.getValue(MobileUser.class);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    finish();
                }

            });






        }
    }



}

