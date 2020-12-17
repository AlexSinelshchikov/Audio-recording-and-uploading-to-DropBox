import com.dropbox.core.v2.DbxClientV2;
import javax.sound.sampled.*;
import java.io.*;


public class JavaSoundRecorder {


    DbxClientV2 client;
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    TargetDataLine line;
    DataLine.Info info;
    AudioFormat format;

    String pathToFile;
    String nameDataTime;

    public JavaSoundRecorder(DbxClientV2 clientV2) {
        this.client = clientV2;
        format = getAudioFormat();
        info = new DataLine.Info(TargetDataLine.class, format);
    }

    public void record(int milliseconds) {

        nameDataTime = SimpleDate.date();
        pathToFile = "C:/Users/Алексей/Desktop/JavaSoundRecorder/" + nameDataTime;
        File file = new File(pathToFile);
        start(file);
        finish(file, milliseconds);
    }

    AudioFormat getAudioFormat() {
        float sampleRate = 192000;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        return format;
    }

    public void start(File file) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    if (!AudioSystem.isLineSupported(info)) {

                        System.exit(0);
                    }
                    line = (TargetDataLine) AudioSystem.getLine(info);
                    line.open(format);
                    line.start();
                    AudioInputStream ais = new AudioInputStream(line);
                    AudioSystem.write(ais, fileType, file);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public void finish(File file, int millis) {
             Thread thread = new Thread(){
            @Override
            public void run() {

                try {
                    sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                line.stop();
                line.close();
                record(millis);


                try {
                    sleep(millis);

                    InputStream in = new FileInputStream
                            (pathToFile);
                    client.files().uploadBuilder("/" + nameDataTime)
                            .uploadAndFinish(in);
                    in.close();

                    file.delete();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

             };
        thread.start();
    }
}