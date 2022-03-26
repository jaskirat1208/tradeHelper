package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
    public static double parseDoubleWCommasRemoved(String d) {
        return Double.parseDouble(d.replace(",", ""));
    }

    public static String YYYYMMDD(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(date);
    }
}
