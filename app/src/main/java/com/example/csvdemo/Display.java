package com.example.csvdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.w3c.dom.Text;

public class Display extends AppCompatActivity {

    TextView unv;
    TextView hi;
    TextView ci;
    ImageView imageView;
    String bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_display);

        int pos = getIntent().getIntExtra("pos",0);

        DataSample ds = MainActivity.dataSamples.get(pos);
        bar = ""+ds.getCi();

        unv = (TextView) findViewById(R.id.unv1);
        unv.setText(""+ds.getUnv());

        hi = (TextView) findViewById(R.id.hi1);
        hi.setText(""+ds.getHi());

        ci = (TextView) findViewById(R.id.ci1);
        ci.setText(""+ds.getCi());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                barCodeButton();
            }
        }, 100);

        imageView = findViewById(R.id.imageView);
       // barCodeButton();

        FloatingActionButton print = findViewById(R.id.print);
        //imageView.setDrawingCacheEnabled( true );


        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintHelper photoPrinter = new PrintHelper(Display.this);
                photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
                //Bitmap bitmap = imageView.getDrawingCache(  );

                LinearLayout layout = (LinearLayout) findViewById(R.id.screen);
                layout.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(layout.getDrawingCache());

                layout.setDrawingCacheEnabled(false);
//                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                photoPrinter.printBitmap("test print",bitmap);
            }
        });
    }

    public void barCodeButton(){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            //ci.getText().toString()
            BitMatrix bitMatrix = multiFormatWriter.encode(bar, BarcodeFormat.CODE_128, imageView.getWidth(), imageView.getHeight());
            Bitmap bitmap = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.RGB_565);
            for (int i = 0; i<imageView.getWidth(); i++){
                for (int j = 0; j<imageView.getHeight(); j++){
                    bitmap.setPixel(i,j,bitMatrix.get(i,j)? Color.BLACK:Color.WHITE);
                }
            }
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}