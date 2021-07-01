package ch.supsi.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class MainActivity extends AppCompatActivity {

    private TextView gestureView, accuracyView;
    public static ArrayList<Coordinate> acquiredData;
    private SensorManager sensorManager;
    private SensorEventListener accelerometerEventListener;
    public static boolean startAcquisition;
    private updateUiReceiver broadcastReceiver;
    public static final float g = 9.8f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize data
        startAcquisition = false;
        gestureView = findViewById(R.id.gesture);
        accuracyView = findViewById(R.id.accuracy);
        acquiredData = new ArrayList<>();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        final Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // read arff file and create the classifier model
        InputStream is = null;
        try {
            is = getResources().getAssets().open("data.arff");
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStreamReader isReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isReader);
        ArffLoader.ArffReader arff = null;
        try {
            arff = new ArffLoader.ArffReader(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Instances dataSet = arff.getData();
        Classifier cModel = (Classifier) new J48();
        dataSet.setClassIndex(dataSet.numAttributes() - 1);
        try {
            cModel.buildClassifier(dataSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final FetchDataCountDownTimer timer = new FetchDataCountDownTimer(cModel, dataSet, getApplicationContext());

        // add event to accelerometer
         accelerometerEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                Coordinate coordinate = new Coordinate(x, y, z);
                float module = (x*x + y*y + z*z) / (g*g);
                if(module > 2.5f && !startAcquisition){
                    startAcquisition = true;
                    timer.start();
                }
                if(startAcquisition){
                    acquiredData.add(coordinate);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) { }
        };
        sensorManager.registerListener(accelerometerEventListener, sensor, SensorManager.SENSOR_DELAY_UI);

        IntentFilter intent = new IntentFilter();
        intent.addAction(updateUiReceiver.STOP_ACQUISITION);
        intent.addAction(updateUiReceiver.UPDATE_UI);
        broadcastReceiver = new updateUiReceiver();
        registerReceiver(broadcastReceiver, intent);
    }

    @Override
    protected void onDestroy() {
        sensorManager.unregisterListener(accelerometerEventListener);
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    public class updateUiReceiver extends BroadcastReceiver {

        public static final String STOP_ACQUISITION = "STOP_ACQUISITION";
        public static final String UPDATE_UI = "UPDATE_UI";

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case STOP_ACQUISITION: {
                    startAcquisition = false;
                    break;
                }
                case UPDATE_UI:{
                    gestureView.setText(intent.getStringExtra("Gesture"));
                    accuracyView.setText(intent.getStringExtra("Accuracy"));
                    break;
                }
            }
        }
    }
}