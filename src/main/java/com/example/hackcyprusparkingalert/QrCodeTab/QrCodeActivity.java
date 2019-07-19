package com.example.hackcyprusparkingalert.QrCodeTab;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.hackcyprusparkingalert.DataStructures.MobileUser;
import com.example.hackcyprusparkingalert.DataStructures.QrCode;
import com.example.hackcyprusparkingalert.DataStructures.UserUsage;
import com.example.hackcyprusparkingalert.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Date;

public class QrCodeActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private FirebaseAuth mAuth;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private static final String TAG = "QrCodeActivity";
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://parking-alert-main.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        getSupportActionBar().setTitle("  Parking Alert Code");

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_qr_code, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(QrCodeActivity.this,SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    //deleted placeholder fragment class
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position){
                case 0:
                    ScanNowFrag mScanNowFrag = new ScanNowFrag();
                    return   mScanNowFrag;
                case 1:
                    MyCodeFrag mMyCodeFrag = new MyCodeFrag();
                    return  mMyCodeFrag;

                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "You cancelled scanning", Toast.LENGTH_LONG).show();



            }else{
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

                //scan was successful so check database to see if id is valid

                //if id is valid, and is active
                // spawn this alertdialog

                //else alert user that this is not a valid qr code

                scanQrCode(result.getContents());
            }


        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }



    public void scanQrCode(String string){
        final  String  input = trimAndRemoveSpaces(string);
        // input =trimAndRemoveSpaces( input);

        Log.d(TAG, "onItemClick: Valid trimedNoSpacesInput: "+input);
        Crashlytics.log(Log.DEBUG,TAG, "onItemClick: Valid trimedNoSpacesInput: "+input);

        DatabaseReference ScanQrExistRef= database.getReference("QrCode").child(input);
        ScanQrExistRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //exist
                    final QrCode qrCode = dataSnapshot.getValue(QrCode.class);
                    //save in database
                    //show dialog
                    Toast.makeText(QrCodeActivity.this, "input: "+input, Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(QrCodeActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.alert_dialog_msg, null);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();


                    Button btnAlertCancel = (Button) mView.findViewById(R.id.btnAlertCancel);
                    Button btnAlertSend = (Button) mView.findViewById(R.id.btnAlertSend);
                    TextView textViewCarPlateNo =(TextView) mView.findViewById(R.id.textViewCarPlateNo);
                    TextView textViewCarModelNo =(TextView) mView.findViewById(R.id.textViewCarModel);
                    ImageView imageView_QrCode = (ImageView) mView.findViewById(R.id.imageView_qrCode);
                    final RadioGroup radioGroupParkingMessages = (RadioGroup) mView.findViewById(R.id.radioGroupParkingMessages);





                    String text2QR = qrCode.getId();

                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        //write qr matrix to imageView
                        BitMatrix bitMatrix = multiFormatWriter.encode(text2QR, BarcodeFormat.QR_CODE, 300, 300);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        imageView_QrCode.setImageBitmap(bitmap);
                        imageView_QrCode.setColorFilter(Color.parseColor("#212121"), PorterDuff.Mode.LIGHTEN);


                    } catch (WriterException e) {
                        //   showSnackbar("Error in rendering qrCode");
                        //   showSnackbar("Error in rendering qrCode");

                        Toast.makeText(QrCodeActivity.this, "Error in rendering qrCode", Toast.LENGTH_SHORT).show();
                        Crashlytics.log(Log.DEBUG,TAG, "Error rendering Qrcode");
                    }


                    textViewCarModelNo.setText("Car model no: "+qrCode.getCarModelNo());
                    textViewCarPlateNo.setText("Car plate no: "+qrCode.getCarPlateNo());
                    btnAlertCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    btnAlertSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();//
                            //save to databasetable that will trigger cloud function
                                   /*public UserUsage(String id,
                                    MobileUser scannedBy,
                                    QrCode scannedQrCode,
                                    Date createdOn) {
                                Id = id; //ID SHOULD BE RANDOM PRIMARY
                                ScannedBy = scannedBy;
                                ScannedQrCode = scannedQrCode;
                                CreatedOn = createdOn;
                            }*/

                            int selectedId = radioGroupParkingMessages.getCheckedRadioButtonId();

                            RadioButton radioButtonParkingMsg;
                            //find the radiobutton by returned id
                            radioButtonParkingMsg = (RadioButton) findViewById(selectedId);

                            MobileUser mUser = new MobileUser(
                                    mAuth.getCurrentUser().getUid(),
                                    mAuth.getCurrentUser().getEmail(),

                                    FirebaseInstanceId.getInstance().getToken(),
                                    null,
                                    mAuth.getCurrentUser().getProviderId(),
                                    null,null
                            );

                            //add qr code to the database
                            DatabaseReference userUsageRef= database.getReference("UserUsage");

                            String userUsageId = userUsageRef.push().getKey();

                            // String parkingMessage = radioButtonParkingMsg.getText().toString();
                            String parkingMessage = "testing";

                            UserUsage userUsage = new UserUsage(
                                    userUsageId,
                                    mUser,
                                    qrCode,
                                    parkingMessage
                            );

                            userUsageRef.child(userUsageId).setValue(userUsage);

                            Toast.makeText(QrCodeActivity.this, parkingMessage+"Alert sent to the Car Owner", Toast.LENGTH_SHORT).show();
                            Crashlytics.log(Log.DEBUG,TAG,"alert sent to car owner");

                        }
                    });
                    //dialog end



                }else{

                    Toast.makeText(QrCodeActivity.this, "Qr code is invalid or not activated, please scan another qr code", Toast.LENGTH_SHORT).show();
                    Crashlytics.log(Log.DEBUG,TAG,"Invalid Qr code, or not activated qr code, please scan another qr code");
                    //does not exist
                    //tell user what he/she scanned is invalid

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public String trimAndRemoveSpaces(String input){

        input = input.trim();
        input = input.replace(" ", "");

        return input;
    }





}
