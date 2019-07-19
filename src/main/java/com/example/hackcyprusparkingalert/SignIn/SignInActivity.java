package com.example.hackcyprusparkingalert.SignIn;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import com.example.hackcyprusparkingalert.R;
import com.example.hackcyprusparkingalert.Registration.RegistrationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private ImageView imageViewCloseBtn;
    private Button btnCreateAccount;
    private Button btnSignInWithEmail;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog progressDialog;
    private static final String TAG = "SignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        imageViewCloseBtn = (ImageView) findViewById(R.id.imageViewCloseIcon);
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        btnSignInWithEmail =(Button) findViewById(R.id.btnSignInWithEmail);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);



        progressDialog.setMessage("Signing in user...");





        btnSignInWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                StartSignIn();
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(SignInActivity.this, RegistrationActivity.class));
                finish();
            }
        });




        imageViewCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void StartSignIn(){
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){


            Toast.makeText(SignInActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
            Crashlytics.log(Log.DEBUG,TAG, "Fields are empty");
        }else{

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(SignInActivity.this, "Sign in problem", Toast.LENGTH_SHORT).show();
                    }else {

                        int secondsDelayed = 1;
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                // startActivity(new Intent(SignInActivity.this, QrCodeActivity.class));

                                finish();
                            }
                        }, secondsDelayed * 2000);


                    }
                }
            });

        }


    }




}
