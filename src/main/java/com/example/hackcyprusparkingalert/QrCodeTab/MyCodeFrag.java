package com.example.hackcyprusparkingalert.QrCodeTab;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.example.hackcyprusparkingalert.DataStructures.QrCode;
import com.example.hackcyprusparkingalert.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class MyCodeFrag extends Fragment {
    private TextView textViewCarPlateNo;
    private TextView textViewCarModelNo;
    private View rootView;
    private static final String TAG = "MyCodeFrag";
    private Context mContext;
    private TextView textViewEmail;
    private FirebaseAuth mAuth;
    private ImageView profilePhoto;
    private RelativeLayout relativeLayoutQrCode;
    private   ImageView imageView_QrCode;
    private Button btnGetQrCode;
    private  int count=0;

    private String array[]={
            "#880e4f",
            "#4a148c",
            "#311b92",
            "#1a237e",
            "#0d47a1",
            "#01579b",
            "#006064",
            "#004d40",
            "#006064",
            "#3e2723",
            "#212121",
            "#263238"
    };
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://parking-alert-main.firebaseio.com/");
    Bitmap bitmap;
    String text2QR;
    String previousActivity;
    private String mQrCodeId;
    private String    mCarPlateNo;
    private String mCarModelNo;

    private String passedQrCode;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_my_code, container, false);
        mContext = getContext();
        Intent mIntent = getActivity().getIntent();
        previousActivity= mIntent.getStringExtra("FROM_ACTIVITY");
        passedQrCode= mIntent.getStringExtra("QrCodeId");
        // dataInit();
        //initRecycleView(rootView);
        mAuth = FirebaseAuth.getInstance();
        final Button btn_saveImage = (Button) rootView.findViewById(R.id.btn_SaveImage);
        relativeLayoutQrCode = (RelativeLayout) rootView.findViewById(R.id.RelativeLayoutQrCode);
        profilePhoto =(ImageView) rootView.findViewById(R.id.imageViewProfilePhoto);
        textViewEmail = (TextView) rootView.findViewById(R.id.textViewEmail);
        btnGetQrCode = (Button) rootView.findViewById(R.id.btn_GetQrCode);
        textViewCarPlateNo= (TextView) rootView.findViewById(R.id.textViewCarPlateNo);
        textViewCarModelNo = (TextView) rootView.findViewById(R.id.textViewCarModel);


        Uri profileUri = mAuth.getCurrentUser().getPhotoUrl();
        if (profileUri == null &&  mAuth.getCurrentUser().getPhotoUrl() != null) {
            profileUri =  mAuth.getCurrentUser().getPhotoUrl();
        }

        if(profileUri!=null){
            Glide.with(mContext)
                    .asBitmap()
                    .load(profileUri)
                    .into(profilePhoto);
        }



        if (mAuth.getCurrentUser() == null) {
            //Toast.makeText(AccountActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(AccountActivity.this, DebugActivity.class));
        }else{
            textViewEmail.setText(mAuth.getCurrentUser().getEmail());
        }

        btnGetQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), qrCodeClaimActivity.class));
            }
        });

        relativeLayoutQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int rand = r.nextInt(12 - 0) + 0;
                Log.d(TAG, "onClick: Random number is "+rand);
                Crashlytics.log(Log.DEBUG,TAG, "onClick: Random number is "+rand);
                imageView_QrCode.setColorFilter(Color.parseColor(array[rand]), PorterDuff.Mode.LIGHTEN);
            }
        });


        btn_saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename="ParkingAlertQRCode";
                //saving file to storage
       /* try  {

           // Assume block needs to be inside a Try/Catch block.
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            OutputStream fOut = null;
            Integer counter = 0;
            File file = new File(path, "FitnessGirl"+counter+".jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
            fOut = new FileOutputStream(file);

            Bitmap pictureBitmap = bitmap; // obtaining the Bitmap
            pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fOut.flush(); // Not really required
            fOut.close(); // do not forget to close the stream

            MediaStore.Images.Media.insertImage(getContext().getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
            FileOutputStream out = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (IOException e) {
            e.printStackTrace();
        }*/
            }
        });




        //if qr code exist
        if(passedQrCode != null) {
            //Toast.makeText(mContext, "PassedQrCode"+passedQrCode, Toast.LENGTH_SHORT).show();
            DatabaseReference getQrCodeRef = database.getReference("QrCode").child(passedQrCode);

            getQrCodeRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d(TAG, "onDataChange: DataSnapshopt getQrCodeRef " + dataSnapshot);
                    Crashlytics.log(Log.DEBUG,TAG, "onDataChange: DataSnapshopt getQrCodeRef " + dataSnapshot);
                    QrCode qrCode1 = dataSnapshot.getValue(QrCode.class);

                    //QrCode(String id, String initiator, String carPlateNo, String carModelNo, MobileUser currentMobileUser, Boolean isActive)
                    // showSnackbar("QrId "+qrCode1.getId()+" initiator"+ qrCode1.getInitiator()+" carplate No "+qrCode1.getCarPlateNo()
                    //     +"car model NO:"+qrCode1.getCarModelNo()+" user email "+qrCode1.getCurrentMobileUser().getEmail());

                    try{

                        mQrCodeId = qrCode1.getId();
                        mCarPlateNo = qrCode1.getCarPlateNo();
                        mCarModelNo = qrCode1.getCarModelNo();
                        textViewCarPlateNo.setText(mCarPlateNo);
                        textViewCarModelNo.setText(mCarModelNo);
                        text2QR = mQrCodeId;

                        imageView_QrCode = (ImageView) rootView.findViewById(R.id.imageView_qrCode);




                        //     text2QR ="/-LNA2yaiOurp11t_cA14";

                        //take the entire qrcode object

                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                        try {
                            //write qr matrix to imageView
                            BitMatrix bitMatrix = multiFormatWriter.encode(text2QR, BarcodeFormat.QR_CODE, 300, 300);
                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

                            bitmap = barcodeEncoder.createBitmap(bitMatrix);
                            imageView_QrCode.setImageBitmap(bitmap);
                            imageView_QrCode.setColorFilter(Color.parseColor("#212121"), PorterDuff.Mode.LIGHTEN);


                        } catch (WriterException e) {

                        }

                    }
                    catch(Exception e){

                        Intent nIntent = new Intent(getContext(),qrCodeClaimActivity.class); //'this' is Activity A
                        nIntent.putExtra("FROM_ACTIVITY", "SplashScreenActivity");
                        startActivity(nIntent);


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }else {
            //if it qr code doesn't exist and the previous activity is not qrCodeClaimActivity fireup qrCodeClaimActivity

            //    Toast.makeText(GettingStartedActivity.this, "onChildAdded: key is:"+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();

            relativeLayoutQrCode.setVisibility(relativeLayoutQrCode.GONE);
            btn_saveImage.setVisibility(btn_saveImage.GONE);
            btnGetQrCode.setVisibility(btnGetQrCode.VISIBLE);
            textViewCarPlateNo.setVisibility(textViewCarPlateNo.INVISIBLE);
            textViewCarModelNo.setVisibility(textViewCarModelNo.INVISIBLE);
            textViewEmail.setPadding(0,20,0,0);


            if (!previousActivity.equals("qrCodeClaimActivity")){

                Intent mIntent2 = new Intent(getActivity(),qrCodeClaimActivity.class); //'this' is Activity A
                mIntent.putExtra("FROM_ACTIVITY", "QrCodeActivity");
                startActivity(mIntent2);
                // startActivity(new Intent(getActivity(),qrCodeClaimActivity.class));
            }






        }



        return rootView;
    }



}
