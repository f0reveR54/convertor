package com.example.convertor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;


import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, com.example.convertor.View {


    private int currentvaluteleft = 0;
    private int currentvaluteright = 0;
    private com.example.convertor.View mainView;


    TextView text,text3;
    Button but1, but2;
    boolean leftbutt = false;
    boolean rightbutt = false;
    EditText num,num2;
    ProgressBar load;
    MainPresenter mp = new MainPresenter();




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        text = (TextView) findViewById(R.id.textView);

        text3 = (TextView) findViewById(R.id.textView3);

        but1 = (Button) findViewById(R.id.button);

        but2 = (Button) findViewById(R.id.button2);

        num = (EditText) findViewById(R.id.editTextNumberDecimal);

        num2 = (EditText) findViewById(R.id.editTextNumberDecimal2);

        load = (ProgressBar) findViewById(R.id.progressBar3);



        mp.attachView(this);

        try {
            mp.loading();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        but1.setOnClickListener(this);
        but2.setOnClickListener(this);

        mp.leftwindowset(num);
        mp.rightwindowset(num2);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button: mp.buttonpressed(this); leftbutt=true; break;
            case R.id.button2: mp.buttonpressed(this); rightbutt=true; break;

        }

    }


    @Override
    public void onDestroy() {
        mp.detachView();
        super.onDestroy();
    }


    @Override
    public String getleftnum() {
        return num.getText().toString().replace(',', '.');
    }

    @Override
    public String gerrightnum() {
        return num2.getText().toString().replace(',', '.');
    }


    @Override
    public int getcurrentvalleft() {
        return currentvaluteleft;
    }

    @Override
    public int getcurrentvalright() {
        return currentvaluteright;
    }

    @Override
    public void setcurrentvalleft(int a) {
        currentvaluteleft = a;
    }

    @Override
    public void setcurrentvalright(int a) {
        currentvaluteright = a;
    }

    @Override
    public void settextleft(String t) {
        text.setText(t);
    }

    @Override
    public void settextright(String t) {
        text3.setText(t);
    }

    @Override
    public void setleftnum(String s) {
        num.setText(s);
    }

    @Override
    public void setrightnum(String s) {
        num2.setText(s);
    }

    @Override
    public void setleftbutt(boolean b) {
        leftbutt = b;

    }

    @Override
    public void setrightbutt(boolean b) {
        rightbutt = b;
    }

    @Override
    public boolean chekleft() {
        return leftbutt;
    }

    @Override
    public boolean chekright() {
        return rightbutt;
    }

    @Override
    public void hidepb() {

        load.setVisibility(View.GONE);
        num.setVisibility(View.VISIBLE);
        num2.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
        text3.setVisibility(View.VISIBLE);
        but1.setVisibility(View.VISIBLE);
        but2.setVisibility(View.VISIBLE);

    }

    @Override
    public Context rt() {
        Context c = this;
        return c;
    }


}








