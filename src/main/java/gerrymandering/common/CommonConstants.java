package gerrymandering.common;

/**
 * Created by yisuo on 11/13/17.
 */
public class CommonConstants {
    public static Double PERCENT = 100.00;
    public static Integer FIRST_ELEMENT = 0;
    public static Integer CONTIGUOUS = 1;
    public static String LASTEST_ELECTION = "2016";
    public static Double TTEST_THRESHOLD = 0.01;
    public static Double EFFICIENCYGAP_THRESHOLD = 0.08;
    public static Long UNCONTESTED = -1L;
    public static Double EARTH_RADIUS_MI = 3959.00;
    public static Double FULL_CIRCLE = 360.00;
    public static Double EARTH_DEGREE_LENGTH = EARTH_RADIUS_MI * 2 * Math.PI / FULL_CIRCLE;
    public static Double POLSBY_THRESHOLD = 0.5;
}
