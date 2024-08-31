package com.example.afinal;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private TemperaturePredictor predictor;
    private EditText editTextRain;
    private DatePicker datePicker;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextRain = findViewById(R.id.editTextRain);
        datePicker = findViewById(R.id.datePicker);
        resultText = findViewById(R.id.resultText);
        Button buttonPredict = findViewById(R.id.buttonPredict);

        try {
            predictor = new TemperaturePredictor(this);
            Log.d(TAG, "Predictor initialized successfully.");
        } catch (IOException e) {
            Log.e(TAG, "Error initializing predictor: " + e.getMessage(), e);
            resultText.setText("Failed to initialize predictor. Check if model files are correctly placed in assets.");
        }

        buttonPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                predictTemperature();
            }
        });
    }

    private void predictTemperature() {
        if (predictor == null) {
            resultText.setText("Predictor is not initialized.");
            return;
        }

        String rainInput = editTextRain.getText().toString();
        if (rainInput.isEmpty()) {
            resultText.setText("Please enter the amount of rain.");
            return;
        }

        float rain;
        try {
            rain = Float.parseFloat(rainInput);
        } catch (NumberFormatException e) {
            resultText.setText("Invalid rain input. Please enter a number.");
            return;
        }

        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1; // DatePicker month is 0-based
        int day = datePicker.getDayOfMonth();

        try {
            float[] predictions = predictor.predict(rain, year, month, day);
            resultText.setText("Predicted Temperatures:\n" +
                    "Temp Max: " + predictions[0] + "°C\n" +
                    "Temp Min: " + predictions[1] + "°C");
        } catch (Exception e) {
            Log.e(TAG, "Error predicting temperature: " + e.getMessage(), e);
            resultText.setText("Error predicting temperature.");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (predictor != null) {
            predictor.close();
        }
    }
}
