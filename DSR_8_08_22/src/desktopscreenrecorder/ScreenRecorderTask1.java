
package desktopscreenrecorder;

import static desktopscreenrecorder.LoginDemo.icon;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import org.jcodec.api.awt.AWTSequenceEncoder;
public class ScreenRecorderTask1 extends TimerTask {
    AWTSequenceEncoder encoder;
    Robot robot;
    Rectangle screenDimension;
    BufferedImage image;
    public ScreenRecorderTask1(AWTSequenceEncoder sequenceEncoder, Rectangle rectangle) {
        encoder = sequenceEncoder;
        screenDimension = rectangle;
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(ScreenRecorderTask1.class.getName()).log(Level.SEVERE, null, ex);
        }
        RecordTimer.start();
    }
    @Override
    public void run() {
                LoginDemo.icon=Toolkit.getDefaultToolkit().getImage("test3.jpg");
                LoginDemo.f.setIconImage(icon);
        System.out.println("capturing..screen.");
        image = robot.createScreenCapture(screenDimension);
       try {
            encoder.encodeImage(image);
        } catch (IOException ex) {
            Logger.getLogger(ScreenRecorderTask1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
