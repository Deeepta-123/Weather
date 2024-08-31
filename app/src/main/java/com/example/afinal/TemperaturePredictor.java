package com.example.afinal;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.util.Log;
import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TemperaturePredictor {

    private static final String TAG = "TemperaturePredictor";
    private Interpreter interpreterMax;
    private Interpreter interpreterMin;

    public TemperaturePredictor(Context context) throws IOException {
        interpreterMax = new Interpreter(loadModelFile(context, "model_max.tflite"));
        interpreterMin = new Interpreter(loadModelFile(context, "model_min.tflite"));
    }

    private MappedByteBuffer loadModelFile(Context context, String modelName) throws IOException {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd(modelName);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        Log.d(TAG, "Loading model file: " + modelName);
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public float[] predict(float rain, int year, int month, int day) {
        float[] input = new float[] {rain, year, month, day};
        float[][] outputMax = new float[1][1];
        float[][] outputMin = new float[1][1];

        interpreterMax.run(input, outputMax);
        interpreterMin.run(input, outputMin);

        return new float[] {outputMax[0][0], outputMin[0][0]};
    }

    public void close() {
        if (interpreterMax != null) {
            interpreterMax.close();
            interpreterMax = null;
        }
        if (interpreterMin != null) {
            interpreterMin.close();
            interpreterMin = null;
        }
    }
}
