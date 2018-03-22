import java.util.Calendar;

/**
 * Created by wenjusun on 7/4/2017.
 */
public class Poc {
    public static void main(String args[]){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
//        calendar.add(Calendar.DATE, 1);
        calendar.add(Calendar.HOUR, 16);

        System.out.println(calendar.getTime());
    }
}
