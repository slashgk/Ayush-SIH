package com.bitplasma.app.ayush_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button barcode, speech, calculator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barcode=(Button)findViewById(R.id.barcode);
        speech=(Button)findViewById(R.id.voice);
        calculator=(Button)findViewById(R.id.calculator);
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BarcodeScan.class);
               startActivity(intent);
            }
        });
        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SpeechScan.class);
                startActivity(intent);
            }
        });
    }
}
