package algorithms;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maara on 2017-05-17.
 */

public class HSVTracker extends Algorithms {
    Scalar upperLimit, lowerLimit; //thresholds for HSV tracking
    Mat hsvMat, mask;
    List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    private Rect rect = new Rect();
    private MatOfPoint bestContour = new MatOfPoint();
    Mat mHierarchy = new Mat();

    public HSVTracker(Scalar lowerThresh, Scalar upperThresh, double minA, double maxA){
        super(minA,maxA);
        upperLimit = upperThresh;
        lowerLimit = lowerThresh;
        hsvMat = new Mat();
        mask = new Mat();
    }

    public HSVTracker(){
        super(0,10000);
        hsvMat = new Mat();
        mask = new Mat();
        for(int i=0;i<3;i++){
            lowerLimit.val[i] = 0;
            upperLimit.val[i] = 255;
        }
    }

    public Rect rectTrack(Mat rgbaImage){
        Imgproc.cvtColor(rgbaImage,hsvMat,Imgproc.COLOR_RGB2HSV);
        Core.inRange(hsvMat,lowerLimit,upperLimit,mask);
        rect = null;
        contours.clear();
        Imgproc.findContours(mask, contours, mHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        maximumArea = 0;
        for (int i = 0; i < contours.size(); i++) {
            double a = Imgproc.contourArea(contours.get(i));
            if (a > maximumArea) {
                maximumArea = a;
                bestContour = contours.get(i);
                rect = Imgproc.boundingRect(bestContour);
            }
        }
        return rect;
    }

    public boolean isTracking(){
        if(rect!= null){
            return true;
        }
        return false;
    }

    public boolean updateParams(){

    }
}
