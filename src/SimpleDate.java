import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDate {

    public static String date () {
        String FILE_EXTENSION = ".wav";
        DateFormat df = new java.text.SimpleDateFormat("yyyyMMdd_hhmmss");
        String filename = df.format(new Date()) + FILE_EXTENSION;

        return filename;
    }




}
