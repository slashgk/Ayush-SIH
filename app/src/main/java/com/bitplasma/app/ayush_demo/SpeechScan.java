package com.bitplasma.app.ayush_demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.zagum.speechrecognitionview.RecognitionProgressView;
import com.github.zagum.speechrecognitionview.adapters.RecognitionListenerAdapter;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class SpeechScan extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION_CODE = 1;
    EditText otext;
    TextView ctext;
    Button bt;
    private SpeechRecognizer speechRecognizer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_scan);
        int[] colors = {
                ContextCompat.getColor(this, R.color.color1),
                ContextCompat.getColor(this, R.color.color2),
                ContextCompat.getColor(this, R.color.color3),
                ContextCompat.getColor(this, R.color.color4),
                ContextCompat.getColor(this, R.color.color5)
        };

        int[] heights = { 20, 24, 18, 23, 16 };

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        final RecognitionProgressView recognitionProgressView = (RecognitionProgressView) findViewById(R.id.recognition_view);
        recognitionProgressView.setSpeechRecognizer(speechRecognizer);
        recognitionProgressView.setRecognitionListener(new RecognitionListenerAdapter() {
            @Override
            public void onResults(Bundle results) {
                showResults(results);
            }
        });
        recognitionProgressView.setColors(colors);
        recognitionProgressView.setBarMaxHeightsInDp(heights);
        recognitionProgressView.setCircleRadiusInDp(2);
        recognitionProgressView.setSpacingInDp(2);
        recognitionProgressView.setIdleStateAmplitudeInDp(2);
        recognitionProgressView.setRotationRadiusInDp(10);
        recognitionProgressView.play();

        otext=(EditText) findViewById(R.id.text);
        ctext=(TextView) findViewById(R.id.ctext);

        Button listen = (Button) findViewById(R.id.listen);
        Button reset = (Button) findViewById(R.id.reset);

        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(SpeechScan.this,
                        Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    startRecognition();
                    recognitionProgressView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startRecognition();
                        }
                    }, 50);
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recognitionProgressView.stop();
                recognitionProgressView.play();
            }
        });
        bt = (Button)findViewById(R.id.convert_speech);
        bt.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                String inp_text=otext.getText().toString();
                String[] inp_ary = inp_text.split(" ");
                for(int i=0;i<inp_ary.length;i++){
                    for(int j=0;j<UnitValues.time_units.length;j++){
                        inp_ary[i]=inp_ary[i].toUpperCase();
                        if(inp_ary[i].equals(UnitValues.time_units[j])&&i!=0){
                            new DecimalFormat("#.##").format(UnitValues.time_values[j]);
                            double insec=Double.parseDouble(inp_ary[i-1])*UnitValues.time_values[j];
                            inp_ary[i-1]=Double.toString(insec);
                            inp_ary[i]="SECOND ";
                        }
                    }
                    for(int j=0;j<UnitValues.weight_units.length;j++){
                        inp_ary[i]=inp_ary[i].toUpperCase();
                        if(inp_ary[i].equals(UnitValues.weight_units[j])&&i!=0){
                            new DecimalFormat("#.##").format(UnitValues.weight_values[j]);
                            double insec=Double.parseDouble(inp_ary[i-1])*UnitValues.weight_values[j];
                            inp_ary[i-1]=Double.toString(insec);
                            inp_ary[i]="MILIGRAM ";
                        }
                    }
                    for(int j=0;j<UnitValues.length_units.length;j++){
                        inp_ary[i]=inp_ary[i].toUpperCase();
                        if(inp_ary[i].equals(UnitValues.length_units[j])&&i!=0){
                            new DecimalFormat("#.##").format(UnitValues.length_values[j]);
                            double insec=Double.parseDouble(inp_ary[i-1])*UnitValues.length_values[j];
                            inp_ary[i-1]=Double.toString(insec);
                            inp_ary[i]="CENTIMETER ";
                        }
                    }
                }
                StringBuilder builder = new StringBuilder();
                for(String s : inp_ary) {
                    builder.append(s+" ");
                }
                String str = builder.toString();
                ctext.setText(str);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
        super.onDestroy();
    }

    private void startRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IN");
        speechRecognizer.startListening(intent);
    }

    private void showResults(Bundle results) {
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        otext.setText(matches.get(0));
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO)) {
            Toast.makeText(this, "Requires RECORD_AUDIO permission", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.RECORD_AUDIO },
                    REQUEST_RECORD_AUDIO_PERMISSION_CODE);
        }
    }
}
