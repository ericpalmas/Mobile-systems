package ch.supsi.sensors;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class FetchDataCountDownTimer extends CountDownTimer {
    private Context context;
    private Instances dataSet;
    private Classifier cModel;
    private List<Double> data;
    private ArrayList<Coordinate> acquiredData;
    private List<String> gestureTypes;

    public FetchDataCountDownTimer(Classifier cModel, Instances dataSet, Context context)  {
        super(300, 300);
        this.cModel = cModel;
        this.dataSet = dataSet;
        this.context = context;
        this.gestureTypes = new ArrayList<>(Arrays.asList("Up", "Down","Forward","Backward"));
    }

    @Override
    public void onTick(long millisUntilFinished) { }

    @Override
    public void onFinish() {
        //initialize data
        acquiredData = MainActivity.acquiredData;
        data = new ArrayList<>();

        Intent intent = new Intent();
        context.sendBroadcast(intent);
        intent.setAction(MainActivity.updateUiReceiver.STOP_ACQUISITION);
        calculateMeans();
        calculateVariances();

        // uncomment the line with the movement you want to acquire, do the movement and add the result to the arff file
        //System.out.println(mean_x + "," + mean_y + "," + mean_z + "," + variance_x + "," + variance_y + "," + variance_z + "," + "backward");
        //System.out.println(mean_x + "," + mean_y + "," + mean_z + "," + variance_x + "," + variance_y + "," + variance_z + "," + "forward");
        //System.out.println(mean_x + "," + mean_y + "," + mean_z + "," + variance_x + "," + variance_y + "," + variance_z + "," + "up");
        //System.out.println(mean_x + "," + mean_y + "," + mean_z + "," + variance_x + "," + variance_y + "," + variance_z + "," + "down");

        Instance entry = new DenseInstance(dataSet.numAttributes());
        for (int i=0; i < data.size(); i++) {
            entry.setValue(dataSet.attribute(i), data.get(i));
        }
        MainActivity.acquiredData.clear();

        // The target attribute is set to be missing
        entry.setMissing(dataSet.numAttributes() - 1);

        // Specify that the instance belong to the training set to inherit from the set description
        entry.setDataset(dataSet);

        // realTime classification
        try {
            //returns the index of the target attribute array
            int index = (int) cModel.classifyInstance(entry);
            double[] fDistribution = cModel.distributionForInstance(entry);

            //send result to broadcaster receiver
            Intent gestureAccuracy = new Intent();
            gestureAccuracy.setAction(MainActivity.updateUiReceiver.UPDATE_UI);
            gestureAccuracy.putExtra("Gesture", gestureTypes.get(index));
            gestureAccuracy.putExtra("Accuracy", String.format("%,.2f", fDistribution[index] * 100) + "%");
            context.sendBroadcast(gestureAccuracy);
        } catch (Exception e) {
            Log.e("ERROR", "Error during gesture classification", e);
        }
    }

    private void calculateMeans() {
        double sumX = 0, sumY = 0, sumZ = 0;
        for (Coordinate coordinate : acquiredData) {
            sumX += coordinate.getX();
            sumY += coordinate.getY();
            sumZ += coordinate.getZ();
        }
        data.add(sumX/acquiredData.size());
        data.add(sumY/acquiredData.size());
        data.add(sumZ/acquiredData.size());
    }

    private void calculateVariances() {
        double varianceX = 0, varianceY = 0, varianceZ = 0;
        for (Coordinate a : acquiredData) {
            varianceX += Math.pow(a.getX() - data.get(0), 2);
            varianceY += Math.pow(a.getY() - data.get(1), 2);
            varianceZ += Math.pow(a.getZ() - data.get(2), 2);
        }
        data.add(varianceX/acquiredData.size());
        data.add(varianceY/acquiredData.size());
        data.add(varianceZ/acquiredData.size());
    }
}
