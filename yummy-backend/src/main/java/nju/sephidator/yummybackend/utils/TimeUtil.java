package nju.sephidator.yummybackend.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String timeFormat(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
