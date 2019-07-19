package com.example.hackcyprusparkingalert.GetttingStarted;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.hackcyprusparkingalert.DataStructures.MobileUser;
import com.example.hackcyprusparkingalert.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Date;

public class GettingStartedActivity extends AppCompatActivity {

    private Button btnCreateAccount;
    private Button btnSignInWithEmail;
    //  private ImageView imageViewCloseBtn;
    private SignInButton googleSignInButton;
    private static final int RC_SIGN_IN =1;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private static final String TAG = "GettingStartedActivity";
    private CallbackManager mCallbackManager;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mProgressDialog;
    private Boolean isAvailable=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);
        Crashlytics.log(Log.DEBUG,TAG, "after setcontent page");


        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        btnSignInWithEmail =(Button) findViewById(R.id.btnSignInWithEmail);
        Crashlytics.log(Log.DEBUG,TAG, "after setcontent page");
        //  imageViewCloseBtn = (ImageView) findViewById(R.id.imageViewCloseIcon);
        googleSignInButton = (SignInButton) findViewById(R.id.google_sign_in_button);
        //  imageViewCloseBtn.setVisibility(imageViewCloseBtn.INVISIBLE);
        mProgressDialog = new ProgressDialog(GettingStartedActivity.this);
        mProgressDialog.setTitle("Signing in");
        mProgressDialog.setMessage("Signing user in");
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() !=null){
                    //means user is signed in
                    LogUserToDataBase();
                    mProgressDialog.show();
                    Log.d(TAG, "GetQrCode: Get Qr function called and value of isAvailable is"+isAvailable.toString());
                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://parking-alert-main.firebaseio.com/");
                    DatabaseReference getUserRef = database.getReference("Users").child(mAuth.getCurrentUser().getUid());

                    getUserRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "onDataChange: datasnapshot is"+dataSnapshot);
                            Crashlytics.log(Log.DEBUG,TAG, "onDataChange: datasnapshot is"+dataSnapshot);


                            MobileUser mobileUser = dataSnapshot.getValue(MobileUser.class);

                       //put some conditional statemenets here
                            mProgressDialog.dismiss();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });





                    //qr code is not available



                    //database read if qr exists
                    //navigate to proper activity based on

                }
            }
        };




        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.btnFacebookLogin);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                Crashlytics.log(Log.DEBUG,TAG, "facebook:onSuccess:" + loginResult);
                mProgressDialog.show();
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                Crashlytics.log(Log.DEBUG,TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });






        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(GettingStartedActivity.this, "You got an error", Toast.LENGTH_SHORT).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
                mProgressDialog.show();
            }
        });


     //sign in button listener
     //create an account button listener

    }



    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //   FirebaseUser currentUser = mAuth.getCurrentUser();

        mAuth.addAuthStateListener(mAuthListener);
        //  updateUI(currentUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }else{
            // Pass the activity result back to the Facebook SDK
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }


    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        Crashlytics.log(Log.DEBUG,TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mProgressDialog.dismiss();
                            Log.d(TAG, "signInWithCredential:success");
                            Crashlytics.log(Log.DEBUG,TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Toast.makeText(GettingStartedActivity.this, "success should sign in now", Toast.LENGTH_SHORT).show();
                            //google sign in activity
                            //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(GettingStartedActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            //  Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //     updateUI(null);
                        }

                        // ...
                    }
                });
    }



    //faceboook firebase token handler
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        Crashlytics.log(Log.DEBUG,TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Crashlytics.log(Log.DEBUG,TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            //facebook//database code here
                            ///   updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(GettingStartedActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //  updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void LogUserToDataBase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://parking-alert-main.firebaseio.com/");
        DatabaseReference myRef = database.getReference();

        MobileUser mUser = new MobileUser(
                mAuth.getCurrentUser().getUid(),
                mAuth.getCurrentUser().getEmail(),

                FirebaseInstanceId.getInstance().getToken(),
                null,
                mAuth.getCurrentUser().getProviderId(),
                null,null
        );
        myRef.child("Users").child(mAuth.getCurrentUser().getUid()).child("DeviceId").setValue(mUser.getDeviceId());
        myRef.child("Users").child(mAuth.getCurrentUser().getUid()).child("Email").setValue(mUser.getEmail());
        myRef.child("Users").child(mAuth.getCurrentUser().getUid()).child("Provider").setValue(mUser.getProvider());
        myRef.child("Users").child(mAuth.getCurrentUser().getUid()).child("UID").setValue(mUser.getUID());



    }

}
