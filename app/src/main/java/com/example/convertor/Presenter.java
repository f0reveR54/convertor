package com.example.convertor;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

public interface Presenter {

    void detachView();

    void attachView(View mainView);

    void buttonpressed(Context c);

    void leftwindowset(EditText o);

    void rightwindowset(EditText o);

    void loading() throws InterruptedException;



}
