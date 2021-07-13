package com.example.convertor;

import android.content.Context;

public interface View {

    String getleftnum();
    String gerrightnum();

    int getcurrentvalleft();
    int getcurrentvalright();

    void setcurrentvalleft(int a);
    void setcurrentvalright(int a);

    void settextleft(String t);
    void settextright(String t);

    void setleftnum(String s);
    void setrightnum(String s);

    void setleftbutt(boolean b);
    void setrightbutt(boolean b);

    boolean chekleft();
    boolean chekright();

    void hidepb();

    Context rt();


}
