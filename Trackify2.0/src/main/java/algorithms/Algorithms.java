package algorithms;

import org.opencv.core.Algorithm;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

/**
 * Created by maara on 2017-05-17.
 */

public abstract class Algorithms {
    double minimumArea, maximumArea;
    boolean isTracking = false;
    //default should just be initializing with min and max area
    public Algorithms(double min, double max){
        minimumArea = min;
        maximumArea = max;
        isTracking = false;
    }
    //all tracking algorithms used should at least be able to track rectangles
    abstract public Rect rectTrack(Mat rgbaImage);

    //check if it is tracking anything using this method
    abstract public boolean isTracking();

    //updates parameters
    abstract public void updateParams();


}
