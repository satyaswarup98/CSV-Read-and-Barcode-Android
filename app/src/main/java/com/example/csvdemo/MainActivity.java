package com.example.csvdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    TextView path;
    FloatingActionButton btn;
    Intent myFile;
    Uri content_describer;
    static List<DataSample> dataSamples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.csv_list);

//        path = (TextView)findViewById(R.id.path);
        btn = findViewById(R.id.upload);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                myFile = new Intent((Intent.ACTION_GET_CONTENT));
//                myFile.setType("*/*");
//                startActivityForResult(myFile,10);
                myFile = new Intent(Intent.ACTION_GET_CONTENT);
                myFile.addCategory(Intent.CATEGORY_OPENABLE);
                myFile.setType("text/*");
                startActivityForResult(Intent.createChooser(myFile, "Open CSV"), 10);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 10 && resultCode == RESULT_OK){
                 content_describer = data.getData();
                    readData();
            }
    }



    private void readData() {
        dataSamples = new ArrayList<>();
        String line = "";
        String output = "";
        try {
            //InputStream is = getResources().openRawResource(R.raw.data);
            InputStream is = getContentResolver().openInputStream(content_describer);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
            reader.readLine();
            while ((line = reader.readLine()) != null){
                //Split by ','
                String[] tokens = line.split(",");
                //Read the data
                DataSample sample = new DataSample();
                sample.setUnv(tokens[0]);
                if(tokens[1].length() > 0 )
                    sample.setHi(Integer.parseInt(tokens[1]));
                else
                    sample.setHi(0);

                if(tokens.length >= 3 && tokens[2].length() > 0 )
                    sample.setCi(Integer.parseInt(tokens[2]));
                else
                    sample.setCi(0);

                dataSamples.add(sample);
                output+=sample;

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            Log.wtf("MyActivity", "Error on line" + line, e);
            e.printStackTrace();
        }

        CsvAdapter adapter = new CsvAdapter(this, dataSamples);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // DataSample ds = dataSamples.get(position);

             //   btn.setText(""+ds.getUnv());

                Intent intent = new Intent(getBaseContext(), Display.class);
                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });
    }


}