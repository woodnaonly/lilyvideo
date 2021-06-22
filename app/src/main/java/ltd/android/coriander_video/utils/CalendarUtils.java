package ltd.android.coriander_video.utils;

import java.util.Calendar;

/**
 * @author by 黄梦 on 2019/3/20.
 */
public class CalendarUtils {

    /**
     * 获取今日开始时间和结束时间
     *
     * @return [0]开始时间 [1]结束时间
     */
    public static long[] getDayStartAndEndTime() {
        long[] times = new long[2];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        times[0] = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        times[1] = calendar.getTimeInMillis();
        return times;
    }

    /**
     * 获得近一周的开始时间和结束时间
     *
     * @return [0]开始时间 [1]结束时间
     */
    public static long[] getDaySevenRange() {
        long[] times = new long[2];
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        times[1] = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, -8 * 24);
        times[0] = calendar.getTimeInMillis();
        return times;
    }

}
