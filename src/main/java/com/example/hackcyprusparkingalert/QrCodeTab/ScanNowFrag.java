package com.example.hackcyprusparkingalert.QrCodeTab;
import android.app.Activity;

import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hackcyprusparkingalert.R;
import com.google.zxing.integration.android.IntentIntegrator;


import java.util.ArrayList;

public class ScanNowFrag extends Fragment {
    private Context mContext;

    Button btnScan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_scan_now, container, false);
        mContext = getContext();
        // dataInit();
        //initRecycleView(rootView);

        btnScan = (Button) rootView.findViewById(R.id.btn_ScanCode);


        final Activity activity =getActivity();






        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setOrientationLocked(true);

                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });



        return rootView;
    }


}

