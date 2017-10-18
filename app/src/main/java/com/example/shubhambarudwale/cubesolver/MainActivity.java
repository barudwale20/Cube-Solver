package com.example.shubhambarudwale.cubesolver;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.*;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.security.Policy;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{

    private static final String TAG = "MainActivity";
    CustomOpenCVJavaCameraView javaCameraView;
    TextView textView;
    int[][] Cube;
    int faceid;
    Mat mRgba;
    BaseLoaderCallback mLoaderCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case BaseLoaderCallback.SUCCESS:{
                    javaCameraView.enableView();
                    break;
                }
                default:{
                    super.onManagerConnected(status);
                    break;
                }
            }

        }
    };

    static {
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "OpenCV not loaded");
        } else {
            Log.d(TAG, "OpenCV loaded");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        javaCameraView = (CustomOpenCVJavaCameraView) findViewById(R.id.java_camera_view);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (javaCameraView!=null)
            javaCameraView.disableView();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (javaCameraView!=null)
            javaCameraView.disableView();
    }
    @Override
    protected  void onResume(){
        super.onResume();
        if (OpenCVLoader.initDebug()) {
            Log.d(TAG, "OpenCV loaded successfully");
            mLoaderCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        } else {
            Log.d(TAG, "OpenCV not loaded");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0,this,mLoaderCallBack);

        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba =new Mat(height,width, CvType.CV_8UC3);
        javaCameraView.imgcols=mRgba.cols();
        javaCameraView.imgrows=mRgba.rows();

    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        return mRgba;
    }
    public void capture(View view)
    {
        Bitmap bitmap = Bitmap.createBitmap(javaCameraView.getWidth() / 4, javaCameraView.getHeight() / 4, Bitmap.Config.ARGB_8888);
        try {
            Log.d("mRgba width",Integer.toString(mRgba.cols()));
            Bundle extras = getIntent().getExtras();
            if(extras!=null)
            {
                faceid=extras.getInt("faceid");
                Object[] objectArray = (Object[]) getIntent().getExtras().getSerializable("Cube");

                Cube = new int[objectArray.length][];
                for(int j=0;j<6;j++)
                {
                    Cube[j]=(int[])objectArray[j];
                }

            }
            else
            {
                Cube=new int[6][9];
                faceid=0;


            }



            Log.d("face0001", Double.toString(mRgba.get(0, 0)[0]));
            bitmap = Bitmap.createBitmap(mRgba.cols(), mRgba.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mRgba, bitmap);
            ImageView mImg;
            mImg = (ImageView) findViewById(R.id.preview);
            //mImg.setImageBitmap(bitmap);




            Intent i = new Intent(this, viewResult.class);
            // your bitmap
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
            Bundle mbundle=new Bundle();
            mbundle.putSerializable("Cube",Cube);
            i.putExtras(mbundle);
            i.putExtra("faceid",faceid);
            i.putExtra("byteArray", bs.toByteArray());
            startActivity(i);


            //Log.d("facer2",Integer.toString(side[3])+Integer.toString(side[4])+Integer.toString(side[5]));
            //Log.d("facer3",Integer.toString(side[6])+Integer.toString(side[7])+Integer.toString(side[8]));
            //Log.i("facer",Integer.toString(side[0]));

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }


    }

}
