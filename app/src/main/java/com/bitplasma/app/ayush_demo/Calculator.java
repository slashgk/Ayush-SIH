package com.bitplasma.app.ayush_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.HashMap;

public class Calculator extends AppCompatActivity {
    public static final String LOG_TAG = AyurvedaLoader.class.getName();
    Spinner sp1,sp2;
    RadioGroup rg;
    EditText inp;
    TextView output;
    Button converter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Log.e(LOG_TAG,Integer.toString(UnitValues.time_units.length)+" "+Integer.toString(UnitValues.time_values.length));
        rg=(RadioGroup)findViewById(R.id.RGroup);
        sp1=(Spinner)findViewById(R.id.sp);
        sp2=(Spinner)findViewById(R.id.output_sp);
        inp=(EditText)findViewById(R.id.inp);
        converter=(Button)findViewById(R.id.convert_bt);
       // converter.setOnClickListener();
        String arraySpinner[] = getResources().getStringArray(R.array.time_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Calculator.this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);
        sp2.setAdapter(adapter);
        output=(TextView)findViewById(R.id.output_tv);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) findViewById(i);
                if(radioButton.getText().toString().equals("Length")){
                    String arraySpinner[] = getResources().getStringArray(R.array.length_array);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Calculator.this,
                            android.R.layout.simple_spinner_item, arraySpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp1.setAdapter(adapter);
                    sp2.setAdapter(adapter);
                }
                if(radioButton.getText().toString().equals("Time")){
                    String arraySpinner[] = getResources().getStringArray(R.array.time_array);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Calculator.this,
                            android.R.layout.simple_spinner_item, arraySpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp1.setAdapter(adapter);
                    sp2.setAdapter(adapter);
                }
                if(radioButton.getText().toString().equals("Weight")){
                    String arraySpinner[] = getResources().getStringArray(R.array.weight_array);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Calculator.this,
                            android.R.layout.simple_spinner_item, arraySpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp1.setAdapter(adapter);
                    sp2.setAdapter(adapter);
                }
            }
        });
            converter.setOnClickListener(new Button.OnClickListener()
             {

            @Override
            public void onClick(View view) {
                if (inp.getText().toString().equals("")) {
                    Toast.makeText(getBaseContext(),"Enter Value",Toast.LENGTH_SHORT).show();
                }
                else{
                    double input = Double.valueOf(inp.getText().toString());
                    String inp_unit = sp1.getSelectedItem().toString();
                    String out_unit = sp2.getSelectedItem().toString();
                    int selectedId = rg.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton) findViewById(selectedId);
                    Log.e(LOG_TAG, "1");
                    String rad = radioButton.getText().toString();
                    if (rad.equals("Time")) {
                        int i = 0;
                        for (i = 0; i < UnitValues.time_units.length; i++) {
                            if (inp_unit.equals(UnitValues.time_units[i])) {
                                break;
                            }
                        }
                        Log.e(LOG_TAG, Integer.valueOf(i).toString());
                        double seconds = input * UnitValues.time_values[i];
                        Log.e(LOG_TAG, "3");
                        for (i = 0; i < UnitValues.time_units.length; i++) {
                            if (out_unit.equals(UnitValues.time_units[i])) {
                                break;
                            }
                        }
                        double out_unitss = seconds / UnitValues.time_values[i];
                        output.setText(BigDecimal.valueOf(out_unitss).toPlainString());
                    } else if (rad.equals("Length")) {
                        int i = 0;
                        for (i = 0; i < UnitValues.length_units.length; i++) {
                            if (inp_unit.equals(UnitValues.length_units[i])) {
                                break;
                            }
                        }
                        Log.e(LOG_TAG, Integer.valueOf(i).toString());
                        double seconds = input * UnitValues.length_values[i];
                        Log.e(LOG_TAG, "3");
                        for (i = 0; i < UnitValues.time_units.length; i++) {
                            if (out_unit.equals(UnitValues.length_units[i])) {
                                break;
                            }
                        }
                        double out_unitss = seconds / UnitValues.length_values[i];
                        output.setText(BigDecimal.valueOf(out_unitss).toPlainString());
                    } else if (rad.equals("Weight")) {
                        int i = 0;
                        for (i = 0; i < UnitValues.weight_units.length; i++) {
                            if (inp_unit.equals(UnitValues.weight_units[i])) {
                                break;
                            }
                        }
                        Log.e(LOG_TAG, Integer.valueOf(i).toString());
                        double seconds = input * UnitValues.weight_values[i];
                        Log.e(LOG_TAG, "3");
                        for (i = 0; i < UnitValues.time_units.length; i++) {
                            if (out_unit.equals(UnitValues.weight_units[i])) {
                                break;
                            }
                        }
                        double out_unitss = seconds / UnitValues.weight_values[i];
                        output.setText(BigDecimal.valueOf(out_unitss).toPlainString());
                    }
                }
            }
        });

    }

}
