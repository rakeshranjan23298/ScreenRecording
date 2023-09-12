package desktopscreenrecorder;
import static desktopscreenrecorder.LoginDemo.icon;
import static desktopscreenrecorder.ScreenRecord.date;
import static desktopscreenrecorder.ScreenRecord.file_name;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import org.jcodec.api.awt.AWTSequenceEncoder;

public class ScreenRecorderTask extends TimerTask {

AudioInputStream audioInputStream;
    ScreenRecorderTask() throws IOException {        
        RecordTimer.start();
    }
    ScreenRecorderTask(AWTSequenceEncoder encoder, Rectangle rectangle) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void run() {
        String path=LoginDemo.path+""+desktopscreenrecorder.ScreenRecord.file_name+"_"+date+ ".wav";
        File f = new File(path);
    try {
        f.createNewFile();
    } catch (IOException ex) {
        Logger.getLogger(ScreenRecorderTask.class.getName()).log(Level.SEVERE, null, ex);
    }
        try {
            desktopscreenrecorder.AudioRecorder.mic.open();
            desktopscreenrecorder.AudioRecorder.mic.start();
            Thread.sleep(1000);
            
            LoginDemo.icon=Toolkit.getDefaultToolkit().getImage("test3.jpg");
            LoginDemo.f.setIconImage(icon);
            
            System.out.println("capturing. sound..");
            ScreenRecord.log("Capturing Sound");
            AudioInputStream audioInputStream = new AudioInputStream( desktopscreenrecorder.AudioRecorder.mic);
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, f);
            System.out.println("done writing to file");
            ScreenRecord.log("Done Audio File");

            } catch (Exception ex) {
                
                Logger.getLogger(ScreenRecorderTask.class.getName()).log(Level.SEVERE, null, ex);
            } 
    }

}
