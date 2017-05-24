package features;

import android.content.Context;
import android.content.SharedPreferences;

import org.opencv.core.Scalar;

import maaran.ColorBlobDetectionActivity;

/**
 * Created by maara on 2017-05-17.
 */

public class Parameters {
    //
    public static Scalar lowerThresh;
    public static Scalar upperThresh;

    public void saveData(Context context, String algo){
        SharedPreferences sp = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(algo.equals("hsv")) {
            for (int i = 0; i < 3; i++) {
                editor.putInt("lower" + i, (int) lowerThresh.val[i]);
                editor.putInt("upper" + i, (int)lowerThresh.val[i]);
            }
        }

        editor.commit();
    }

    public void loadData(Context context, String algo){
        SharedPreferences sp = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(algo.equals("hsv")) {
            for (int i = 0; i < 3; i++) {
                upperThresh.val[i] = sp.getInt("upper"+i,(int)upperThresh.val[i]);
                lowerThresh.val[i] = sp.getInt("lower"+i, (int)lowerThresh.val[i]);
            }
        }


    }

}
