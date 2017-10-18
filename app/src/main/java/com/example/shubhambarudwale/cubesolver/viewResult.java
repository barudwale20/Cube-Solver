package com.example.shubhambarudwale.cubesolver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class viewResult extends AppCompatActivity {
    int side[]= new int[9];
    int faceid;
    int Cube[][];
    private BaseLoaderCallback mOpenCVCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    //DO YOUR WORK/STUFF HERE
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);
        if(getIntent().hasExtra("byteArray")) {
            ImageView previewThumbnail = (ImageView)findViewById(R.id.result);
            Bitmap b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
            previewThumbnail.setImageBitmap(b);
            Object[] objectArray = (Object[]) getIntent().getExtras().getSerializable("Cube");
            Bundle extras = getIntent().getExtras();
            faceid=extras.getInt("faceid");
            Cube = new int[objectArray.length][];
            for(int j=0;j<6;j++)
            {
                Cube[j]=(int[])objectArray[j];
            }
            useOpenCv(b);
        }
    }
    public void useOpenCv(Bitmap b)
    {
        Mat face=new Mat(b.getWidth(),b.getHeight(), CvType.CV_8U);
        Utils.bitmapToMat(b, face);
        //Log.d("face000", Double.toString(face.get(0, 0)[0]));
        //Log.d("face.cols", Integer.toString(face.cols()));
        //Log.d("face.rows", Integer.toString(face.rows()));
        side=DetectCol(face);

    }
    public void nextFace(View view)
    {
        Cube[faceid][0]=side[0];
        Cube[faceid][1]=side[1];
        Cube[faceid][2]=side[2];
        Cube[faceid][3]=side[3];
        Cube[faceid][4]=side[4];
        Cube[faceid][5]=side[5];
        Cube[faceid][6]=side[6];
        Cube[faceid][7]=side[7];
        Cube[faceid][8]=side[8];


        if(faceid<5) {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("faceid", faceid + 1);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable("Cube", Cube);
            i.putExtras(mBundle);
            startActivity(i);
        }
        else
        {
            Intent i = new Intent(this, SolveCubeActivity.class);
            i.putExtra("faceid", faceid + 1);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable("Cube", Cube);
            i.putExtras(mBundle);
            startActivity(i);

        }
    }
    public int[] DetectCol(Mat source)
    {
        //Mat uncropped = new Mat(source.rows(),source.cols(),source.type());
        Rect roi = new Rect((source.cols()-source.rows())/2, 0, source.rows(), source.rows());
        Mat cropped = new Mat(source, roi);
        Mat mRgba = new Mat(cropped.rows(),cropped.cols(),cropped.type());
        Imgproc.GaussianBlur(cropped, mRgba, new Size(45, 45), 0);
        int sidetemp[]=new int[9];
        float n_center_x=0;
        float n_center_y=0;
        for(int i=0;i<9;i++)
        {
            int cell_row=(i-i%3)/3+1;
            int cell_col=(i%3)+1;
            int p3x=(int) (n_center_x+mRgba.rows()/6+(cell_row-1)*mRgba.rows()/3);
            int p3y=(int) (n_center_y+mRgba.rows()/6+(cell_col-1)*mRgba.rows()/3);
            int avg_r=(int)(mRgba.get(p3y,p3x)[0]);
            int avg_g=(int)(mRgba.get(p3y,p3x)[1]);
            int avg_b=(int)(mRgba.get(p3y,p3x)[2]);
            if (avg_r >= 130 && avg_r <= 255 && avg_g >= 0 && avg_g <= 29 && avg_b >= 0 && avg_b <= 100)
                sidetemp[i] = 6;//red
            else if (avg_r >= 0 && avg_r <= 50 && avg_g >= 40 && avg_g <= 255 && avg_b >= 0 && avg_b <= 50)
                sidetemp[i] = 1;//green
            else if (avg_r >= 0 && avg_r <= 60 && avg_g >= 0 && avg_g <= 150 && avg_b >= 70 && avg_b <= 255)
                sidetemp[i] = 3;//blue
            else if (avg_r >= 130 && avg_r <= 255 && avg_g >= 130 && avg_g <= 255 && avg_b >= 0 && avg_b <= 50)
                sidetemp[i] = 4;//yellow
            else if (avg_r >= 170 && avg_r <= 255 && avg_g >= 30 && avg_g <= 170 && avg_b >= 0 && avg_b <= 50)
                sidetemp[i] = 5;//orange
            else if (avg_r >= 130 && avg_r <= 255 && avg_g >= 130 && avg_g <= 255 && avg_b >= 130 && avg_b <= 255)
                sidetemp[i] = 2;//white
            else
                sidetemp[i] = -1;
        }

        int[] sides_final=new int[9];
        sides_final[0]=sidetemp[2];
        sides_final[1]=sidetemp[1];
        sides_final[2]=sidetemp[0];
        sides_final[3]=sidetemp[5];
        sides_final[4]=sidetemp[4];
        sides_final[5]=sidetemp[3];
        sides_final[6]=sidetemp[8];
        sides_final[7]=sidetemp[7];
        sides_final[8]=sidetemp[6];
        return sides_final;
    }
    @Override
    protected void onResume() {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this,
                mOpenCVCallBack);
    }
}
