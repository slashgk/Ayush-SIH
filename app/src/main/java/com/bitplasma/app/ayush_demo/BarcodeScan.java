package com.bitplasma.app.ayush_demo;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import static junit.framework.Assert.assertNotNull;

public class BarcodeScan extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Ayurveda>{
    public static final String BARCODE_KEY = "BARCODE";
    private static final int AYUSH_LOADER_ID = 1;
    private static final String AYUSH_REQUEST_URL =
            "http://18.220.214.137/?barcode=";
    private Barcode barcodeResult;
    private String barcode;
    public static final String LOG_TAG = BarcodeScan.class.getName();
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        barcode= getIntent().getStringExtra("barcode");
        result = (TextView) findViewById(R.id.barcodeResult);
       // final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assertNotNull(result);
       // assertNotNull(fab);
        networkCall(barcode);
        if(savedInstanceState != null){
            Barcode restoredBarcode = savedInstanceState.getParcelable(BARCODE_KEY);
            if(restoredBarcode != null){
                barcodeResult = restoredBarcode;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void networkCall(String barcodeValue){
        Log.i(LOG_TAG,"TEST: networkCall");
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.i(LOG_TAG,"TEST: Network available");
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            //Bundle bundle = new Bundle();
           // bundle.putString("barc",barcodeResult.toString());
            loaderManager.initLoader(AYUSH_LOADER_ID, null, BarcodeScan.this);
        }
        else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            result.setText(R.string.no_internet_connection);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BARCODE_KEY, barcodeResult);
        super.onSaveInstanceState(outState);
    }



    @Override
    public Loader<Ayurveda> onCreateLoader(int i, Bundle bundle) {
        //String barc=bundle.getString("barc");
        return new AyurvedaLoader(this, AYUSH_REQUEST_URL+barcode);
    }

    @Override
    public void onLoadFinished(Loader<Ayurveda> loader, Ayurveda ayurveda) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        if (ayurveda != null) {
            result.setText(ayurveda.getmName());
        }
        else
            result.setText("No details");
    }

    @Override
    public void onLoaderReset(Loader<Ayurveda> loader) {
        result.setText("");
    }
}
