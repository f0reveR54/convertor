package com.example.convertor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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


public class NetworkUtil extends BroadcastReceiver {

    static Document doc;
    static String [] countries;
    static List<ValuteInfo> valuteList = new ArrayList<>();

    @Override
    public void onReceive(final Context context, final Intent intent) {


    }

    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    static void loadfromcbr() throws IOException {

        URLConnection conn = new URL("https://www.cbr.ru/scripts/XML_daily.asp").openConnection();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try (InputStream is = conn.getInputStream()) {

            DocumentBuilder dBuilder = dbf.newDocumentBuilder();

            doc = dBuilder.parse(is);



        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }


    static void Parsing()  throws Exception  {

        Element element = doc.getDocumentElement();

        NodeList valuteNodeList = element.getElementsByTagName("Valute");


        ValuteInfo russia = new ValuteInfo();
        russia.setName("Российский рубль");
        russia.setNominal(1);
        russia.setCharCode("RUS");
        russia.setValue(1.0);
        valuteList.add(russia);



        for (int i = 0; i < valuteNodeList.getLength(); i++){
            if (valuteNodeList.item(i).getNodeType() == Node.ELEMENT_NODE){
                Element valueElement = (Element) valuteNodeList.item(i);

                ValuteInfo valute = new ValuteInfo();

                NodeList childNodes = valueElement.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {


                    if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE){
                        Element childElement = (Element) childNodes.item(j);

                        switch (childElement.getNodeName()){
                            case "CharCode":{
                                valute.setCharCode(childElement.getTextContent());
                            } break;

                            case "Nominal": {
                                valute.setNominal(Integer.valueOf(childElement.getTextContent()));
                            } break;

                            case "Name": {
                                valute.setName(childElement.getTextContent());
                            } break;

                            case "Value": {
                                valute.setValue((Double) NumberFormat.getNumberInstance(Locale.FRANCE).parse(childElement.getTextContent()));
                            } break;



                        }
                    }
                }

                valuteList.add(valute);
            }


        }

        countries = new String[valuteList.size()];
        for (int i = 0; i < valuteList.size(); i++) countries[i] = valuteList.get(i).getName();
    }

    static class ValuteInfo {

        String CharCode;
        Integer nominal;
        String name;
        Double value;

        public String getCharCode() {
            return CharCode;
        }

        public void setCharCode(String charCode) {
            CharCode = charCode;
        }

        public Integer getNominal() {
            return nominal;
        }

        public void setNominal(Integer nominal) {
            this.nominal = nominal;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

    }




}
