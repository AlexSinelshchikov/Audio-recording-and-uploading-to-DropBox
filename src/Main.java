import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        String ACCESS_TOKEN =
                "3gX_B5Ru20YAAAAAAAAAAW7LbDQzyPefwqMkD3XvjWrZ6fq2vssqY0AIqknoLiKp";

        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        JavaSoundRecorder recorder = new JavaSoundRecorder((client));
        recorder.record(30000);

    }
}
