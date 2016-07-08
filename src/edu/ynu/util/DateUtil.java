package edu.ynu.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil{
    public static String getNow(String formatStr) {
        String str=null;
        try{
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            str = format.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    public static int compareTo(String data1,String data2,String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date1 = null;
        Date date2= null;
        try {
            date1 = format.parse(data1);
            date2= format.parse(data2);
            if(date1.before(date2)) {
                return -1;//小于
            }else if(date1.after(date2)){
                return 1;//大于
            }
            else {
                return 0;//等于
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
            return -2;
        }
    }
}

