package nju.sephidator.yummybackend.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {

    public static Double getDistance(Integer coordX1, Integer coordY1, Integer coordX2, Integer coordY2) {
        Double x1 = coordX1.doubleValue() * 0.1;
        Double y1 = coordY1.doubleValue() * 0.1;
        Double x2 = coordX2.doubleValue() * 0.1;
        Double y2 = coordY2.doubleValue() * 0.1;
        Double result = Math.sqrt(((x1 - y1) * (x1 - y1) + (x2 - y2) * (x2 - y2)));
        return scaledDouble(result, 1);
    }

    public static Double scaledDouble(Double number, int scale) {
        return new BigDecimal(number).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }
}
