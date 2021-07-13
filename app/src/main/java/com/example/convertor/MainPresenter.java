package com.example.convertor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.UniversalTimeScale;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import java.time.LocalDate;

public class MainPresenter  implements Presenter{


    private View mainView;
    private static Thread SecondThread;
    private static Runnable runnable;

    @Override
    public void detachView() {

    }

    @Override
    public void attachView(View mainView) {
        this.mainView = mainView;


    }

    @Override
    public void buttonpressed(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Выберите валюту:")
                .setItems(NetworkUtil.countries, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if(mainView.chekleft()) {
                            String sum = mainView.gerrightnum();
                            double summ;
                            summ = Double.parseDouble(sum) * ((NetworkUtil.valuteList.get(mainView.getcurrentvalright()).getValue()/NetworkUtil.valuteList.get(mainView.getcurrentvalright()).getNominal())
                                    / (NetworkUtil.valuteList.get(which).getValue()/NetworkUtil.valuteList.get(which).getNominal()));

                            String format   = String.format("%.3f",summ);
                            mainView.setleftnum(format);
                            mainView.settextleft(NetworkUtil.valuteList.get(which).getCharCode());
                            mainView.setcurrentvalleft(which);
                            mainView.setleftbutt(false);
                        }
                        if(mainView.chekright()) {
                            String sum = mainView.getleftnum();
                            double summ;
                            summ = Double.parseDouble(sum) * ((NetworkUtil.valuteList.get(mainView.getcurrentvalleft()).getValue()/(NetworkUtil.valuteList.get(mainView.getcurrentvalleft()).getNominal()))
                                    / (NetworkUtil.valuteList.get(which).getValue()/ NetworkUtil.valuteList.get(which).getNominal()));
                            mainView.settextright(NetworkUtil.valuteList.get(which).getCharCode());
                            String format   = String.format("%.3f",summ);
                            mainView.setrightnum(format);
                            mainView.setcurrentvalright(which);
                            mainView.setrightbutt(false);
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void leftwindowset(EditText b) {

        b.setOnKeyListener(new android.view.View.OnKeyListener()
                             {
                                 @Override
                                 public boolean onKey(android.view.View v, int keyCode, KeyEvent event) {
                                     if(event.getAction() == KeyEvent.ACTION_DOWN &&
                                             (keyCode == KeyEvent.KEYCODE_ENTER))
                                     {
                                         // сохраняем текст, введённый до нажатия Enter в переменную
                                         String sum = mainView.getleftnum();
                                         double summ;

                                         summ = Double.parseDouble(sum) * ((NetworkUtil.valuteList.get(mainView.getcurrentvalleft()).getValue()/NetworkUtil.valuteList.get(mainView.getcurrentvalleft()).getNominal())
                                                 / (NetworkUtil.valuteList.get(mainView.getcurrentvalright()).getValue()/NetworkUtil.valuteList.get(mainView.getcurrentvalright()).getNominal()));
                                         String format   = String.format("%.3f",summ);
                                        mainView.setrightnum(format);


                                         return true;
                                     }
                                     return false;
                                 }


                             }
        );



    }

    @Override
    public void rightwindowset(EditText o) {


        o.setOnKeyListener(new android.view.View.OnKeyListener()
                              {
                                  @Override
                                  public boolean onKey(android.view.View v, int keyCode, KeyEvent event) {
                                      if(event.getAction() == KeyEvent.ACTION_DOWN &&
                                              (keyCode == KeyEvent.KEYCODE_ENTER))
                                      {
                                          // сохраняем текст, введённый до нажатия Enter в переменную
                                          String sum = mainView.gerrightnum();
                                          double summ;


                                          summ = Double.parseDouble(sum) * ((NetworkUtil.valuteList.get(mainView.getcurrentvalright()).getValue()/NetworkUtil.valuteList.get(mainView.getcurrentvalright()).getNominal())
                                                  / (NetworkUtil.valuteList.get(mainView.getcurrentvalleft()).getValue()/NetworkUtil.valuteList.get(mainView.getcurrentvalleft()).getNominal()));

                                          String format   = String.format("%.3f",summ);

                                          mainView.setleftnum(format);


                                          return true;
                                      }
                                      return false;
                                  }


                              }

        );

    }

    @Override
    public void loading() throws InterruptedException {


            runnable = new Runnable() {
                @Override
                public void run() {
                    try {

                        while (!NetworkUtil.isOnline(mainView.rt()) ) { }

                        NetworkUtil.loadfromcbr();
                        NetworkUtil.Parsing();




                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            SecondThread = new Thread(runnable);
            SecondThread.start();







        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                while (NetworkUtil.doc==null)  {
                    Log.e("qweqw", "nu i che tut");
                }

                mainView.hidepb();

            }
        }, 2000);



    }


}
