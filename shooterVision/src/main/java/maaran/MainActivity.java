package maaran;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.imgproc.Imgproc;
import shooterVision.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.SurfaceView;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends Activity {
    private static final String  TAG = "Comms"; //Filter by this tag to see specific printouts

    private final int port = 3800;
    private final String host = "localhost";

    public static String messageToSend = null;

    private Socket socket;

    private Mat rgbaColors; //frame
    private Mat smallerFrame;
    private ColorBlobDetector colorDetector;
    private Scalar contourColor;
    private ViewGroup sliderView;
    private ViewGroup presetView;

    private RangeSeekBar hSlider;
    private RangeSeekBar sSlider;
    private RangeSeekBar vSlider;

    private  boolean cameraCreated;

    Rect rect;
    Point topL;
    Point botR;


    private Scalar upperLimit = new Scalar(0);
    private Scalar lowerLimit = new Scalar(0);

    private View option;

    boolean sliderShow = false;

    private CameraBridgeViewBase cvCameraView;
    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    cvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public MainActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.color_blob_detection_surface_view);
    }

    @Override
    public void onPause() {
        super.onDestroy();
        super.onPause();
        if (cvCameraView != null)
            cvCameraView.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (cvCameraView != null)
            cvCameraView.disableView();
    }

    public void selfDestruct(View view){

    }

}
