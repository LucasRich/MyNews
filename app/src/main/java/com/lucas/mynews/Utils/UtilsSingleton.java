package com.lucas.mynews.Utils;

import android.widget.CheckBox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilsSingleton {

    private static UtilsSingleton instance;

    private UtilsSingleton() { }

    public static UtilsSingleton getInstance() {
        if (instance == null) {
            instance = new UtilsSingleton();
        }
        return instance;
    }

    public String CheckBoxChecked (CheckBox checkBox, String NameOfCheckBox){
        String value;

        if (checkBox.isChecked() == true){
            value = NameOfCheckBox;
        }
        else {
            value = "";
        }

        return value;
    }

    public String DisplaySectionAndSubsectionIfNoNull(String section, String subsection){
        String result;

        if (subsection.equals("")){
            result = section;
        }else {
            result =section + " > " + subsection;
        }
        return result;
    }

    public String getGoodFormatDate(String publishedDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yy-mm-dd");
        String dateInString = publishedDate;

        try {
            Date date = formatter.parse(dateInString.replaceAll("Z$", "+0000"));
            publishedDate = formatter.format(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        StringBuilder myName = new StringBuilder(publishedDate);
        myName.setCharAt(2, '/');
        myName.setCharAt(5, '/');
        publishedDate = myName.toString();

        return publishedDate;
    }

    public String createParamQuery (String article, CheckBox checkBox1, CheckBox checkBox2, CheckBox checkBox3, CheckBox checkBox4,
                                    CheckBox checkBox5, CheckBox checkBox6, String box1, String box2, String box3, String box4, String box5, String box6){
        String value = (article + "," +
                CheckBoxChecked(checkBox1, box1) + "," +
                CheckBoxChecked(checkBox2, box2) + "," +
                CheckBoxChecked(checkBox3, box3) + "," +
                CheckBoxChecked(checkBox4, box4) + "," +
                CheckBoxChecked(checkBox5, box5) + "," +
                CheckBoxChecked(checkBox6, box6));

        return value;
    }

    public String getGoodFormatUrl(String url){

        StringBuilder myUrl = new StringBuilder(url);
        myUrl.insert(4, 's');
        url = myUrl.toString();

        return url;
    }

    public String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd ");
        String currentDate = dateFormat.format(calendar.getTime());
        return currentDate;
    }
}
