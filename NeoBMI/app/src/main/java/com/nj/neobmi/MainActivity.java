package com.nj.neobmi;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private DecimalFormat df = new DecimalFormat(".##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText input_height = (EditText)findViewById(R.id.input_height);
                double height = Double.parseDouble(input_height.getText().toString());
                double height_100 = height / 100;

                EditText input_weight = (EditText)findViewById(R.id.input_weight);
                double weight = Double.parseDouble(input_weight.getText().toString());

                double bmi = weight / (height_100 * height_100);

                TextView result = (TextView)findViewById(R.id.result);
                result.setText(getText(R.string.bmi_result) + df.format(bmi));
            }
        });
    }
}