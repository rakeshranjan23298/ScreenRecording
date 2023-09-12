package desktopscreenrecorder;
import static desktopscreenrecorder.AudioRecorder.mic;
import static desktopscreenrecorder.ScreenRecord.simpleDate;
import static desktopscreenrecorder.ScreenRecord.simpleDateFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.jcodec.api.awt.AWTSequenceEncoder;

public class ScreenRecord  extends Thread
{
// Static Variable Declear
    static String file_name = "";
    static String mobile_number = "";
    static String call_number = "";
    static String agent_id = "";
    static String service_id = "";
    static String audio_file_name = "";
    static String video_file_name = "";
    static String start_time = "";
    static String end_time = "";
    static String  Pattern="yy_MM_dd_HH_mm_ss";
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ScreenRecord.Pattern);
    static String date = simpleDateFormat.format(new Date());
    
    static String  PatternDate="dd-MM-yyyy-HH:mm:ss";
    static SimpleDateFormat simpleDate = new SimpleDateFormat(ScreenRecord.PatternDate);
    static String dateS = simpleDate.format(new Date());    

// end of variable declearation
//    =--------------------------------------------------   Logger methods    ------------------
    public static void log (String message)
    {
       try
        {
                Calendar currentDate = Calendar.getInstance();
                SimpleDateFormat formatter= new SimpleDateFormat("dd-MMM-yyyy_HH");
                SimpleDateFormat formatter1= new SimpleDateFormat("yyyy_MM_dd");            
                String directoryName_1 = "Logs";
                String directoryName_2 = formatter1.format(currentDate.getTime());

                File theDir = new File(directoryName_1);
                File theDir1 = new File(directoryName_1+"/"+directoryName_2);

                if (!theDir.exists()) 
                {
                    System.out.println("creating directory: " + directoryName_1);
                    try
                    {
                        theDir.mkdir();
                    } 
                    catch(SecurityException se)
                    {
                        System.out.println("Exception:--"+se);
                    }        
                }
                if(!theDir1.exists())
                {
                    System.out.println("creating directory: " + directoryName_1+"/"+directoryName_2);

                    try
                    {
                        theDir1.mkdir();
                    } 
                    catch(SecurityException se)
                    {
                        System.out.println("Exception:--"+se);
                    }        
                }
                String dateNow = formatter.format(currentDate.getTime());
                String filename = directoryName_1+"/"+directoryName_2+"/"+dateNow+".log";
                FileWriter fstream = new FileWriter(filename,true);
                BufferedWriter out = new BufferedWriter(fstream);
                formatter = new SimpleDateFormat("dd-MMM-yyyy/HH:mm:ss");
                dateNow = formatter.format(currentDate.getTime());
                out.write(dateNow+"\t: "+message+"\n");
                out.close();
            }
            catch(IOException ie)
            {
                System.out.println("Error: " + ie.getMessage());                
            }
            catch (Exception e)
            {
                System.out.println("Error: " + e.getMessage());
            }        

}
    
//==------------------------------------------------------------------------   Loger Class end  ------------------------
    public static void startRecord() throws AWTException, IOException, InterruptedException {
        Runnable r1=new Runnable(){  
            public void run(){
                try {
                    DesktopScreenRecorder.fun();
                } catch (Exception ex) {
                    System.err.println("---Exception Found in DesktopScreen Thread ---"+ex);
                    ScreenRecord.log("---Exception Found in DesktopScreen Thread ---");
                }
            }
        };  
      Runnable r2=new Runnable(){
        public  void run() {
            System.out.println("Soundrecording!!!!.... Thread start ");
            ScreenRecord.log("Soundrecording!!!!.... Thread start ");
            desktopscreenrecorder.AudioRecorder a1=new desktopscreenrecorder.AudioRecorder();
            try {
                a1.initRecording();
            } catch (Exception ex) {
                System.err.println("---Exception Found in SoundRecording Thread ---"+ex);
                ScreenRecord.log("---Exception Found in SoundRecording Thread ---");
            }
            }
        };
      
      Thread t1=new Thread(r1);  
      Thread t2=new Thread(r2);
      t1.start();
      t2.start();  
    }
}
//-------------------------------------------  **        Class AudioRecord Started    **       ----------------------------------------------------------------
class AudioRecorder extends Thread {
    private javax.swing.JLabel labelTimer;
    static boolean startAudio =false;
    static boolean stopAudio =false;
    static boolean isAudio = false;
    public static Rectangle rectangle;
    public static AWTSequenceEncoder encoder;
    public static ScreenRecorderTask recorderTask;
    public static TimerCountTask countTask;
    public static Timer timerRecord;
    public static Timer timerCount;
    public static TargetDataLine mic;
    File f;
    public void initRecording() throws InterruptedException, IOException 
    {
        //define audio format
        try{
            AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
            mic = (TargetDataLine) AudioSystem.getLine(info);
            mic.open();
        }catch(Exception e)
        {
            System.err.println("Mic Not Detected..... ");
            ScreenRecord.log("Mic Not detcted");
        }
        System.out.println("recording initialized... mic Open  ");
        ScreenRecord.log("Recording Initialized .... Mic open");
        statRecording();
    }
    public void statRecording() throws InterruptedException, IOException {
        MonitorSocket1 ms1=new MonitorSocket1("MS1");
        ms1.start();
               
        while(true)
        {
            if(startAudio==true)
            {
                AudioRecorder a1=new AudioRecorder();
                a1.scheduleTimerTasks();
                while(!stopAudio)
                {                    
                    Thread.sleep(1000);
                }
                a1.stopRecording();
            }
            Thread.sleep(1000);
            System.out.println("waiting Audio.....");
            ScreenRecord.log("Waiting For Audio");
        }
    }
    private void scheduleTimerTasks() throws IOException 
    {
        ScreenRecord.start_time = simpleDateFormat.format(new Date());
        ScreenRecord.file_name =ScreenRecord.agent_id+"_"+ScreenRecord.service_id+"_"+ScreenRecord.mobile_number+"_"+ScreenRecord.start_time;
        isAudio = true;
        int delay = 1000 / 24;
        RecordTimer.reset();
        timerRecord = new Timer("Thread TimerRecord");
        timerCount = new Timer("Thread TimerCount");
        recorderTask = new ScreenRecorderTask();
        countTask = new TimerCountTask(labelTimer);
        timerRecord.scheduleAtFixedRate(recorderTask, 0, delay);
        timerCount.scheduleAtFixedRate(countTask, 0, 1000);
    }
    
       public void stopRecording() throws IOException {
        RecordTimer.stop();
        timerCount.cancel();
        timerCount.purge();
        timerRecord.cancel();                              
        timerRecord.purge();
        recorderTask.cancel();
        countTask.cancel();
        try{
            mic.stop();
            mic.close();
            System.out.println("Recording ended  & Mic Closed");     
            ScreenRecord.log("Mic Closed");
        }catch(Exception e)
        {
            ScreenRecord.log("Exceeption Caught while stop Sound Recording");
            System.err.println("Exception Caught while stop Sund Recording");
        }
    }      
}
//--------------------------------------  **          AudioRecord Class End    **       --------------------------------------------------------------------
//--------------------------------------  **          Class  DesktopScreenRecord Start   **       ----------------------------------------------------------------
class DesktopScreenRecorder
{
    private javax.swing.JLabel labelTimer;
    Rectangle rectangle;
    AWTSequenceEncoder encoder;
    ScreenRecorderTask1 recorderTask1;
    TimerCountTask countTask;
    public static Timer timerRecord;
    Timer timerCount;
    File f;
    boolean isRecording = false;
    static boolean startRecording = false;
    static boolean stopRecording = true;
    // End of variables declaration   
    public static void fun() throws AWTException, IOException ,InterruptedException
    {
        MonitorSocket1 ms = new MonitorSocket1("MS");
        ms.start();
        Image icon=Toolkit.getDefaultToolkit().getImage("test.jpg");
        while(true)
        {
            if(startRecording)
            {
                icon=Toolkit.getDefaultToolkit().getImage("test3.jpg");
                DesktopScreenRecorder ds = new DesktopScreenRecorder();
                ds.scheduleTimerTasks();
                while(!stopRecording)
                {
                    Thread.sleep(1000);
                }
                icon=Toolkit.getDefaultToolkit().getImage("test2.jpg");
                ds.stopScreenRecording();
                startRecording = false;
                stopRecording = false;
            }
            icon=Toolkit.getDefaultToolkit().getImage("test.jpg");
            Thread.sleep(1000);
            System.out.println("Waiting ScreenRecord");
            ScreenRecord.log("Waiting ScreenRecord");
        }
    }
    private void scheduleTimerTasks() 
    {
        ScreenRecord.start_time = simpleDateFormat.format(new Date());
        ScreenRecord.file_name =ScreenRecord.agent_id+"_"+ScreenRecord.service_id+"_"+ScreenRecord.mobile_number+"_"+ScreenRecord.start_time;
        try 
        {
            f = new File(LoginDemo.path+""+ScreenRecord.file_name +""+ScreenRecord.date+ ".mp4");
            rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            encoder = AWTSequenceEncoder.createSequenceEncoder(f, 24 / 8);
        } 
        catch (IOException ex) 
        {
            System.out.println("Error on File Creation || Path Not Found -"+ex);
            ScreenRecord.log("Error on File Creation || Path Not Found -"+ex);
        }
        isRecording = true;
        int delay = 1000 / 24;
        RecordTimer.reset();
        timerRecord = new Timer("Thread TimerRecord");
        timerCount = new Timer("Thread TimerCount");
        recorderTask1 = new ScreenRecorderTask1(encoder, rectangle);
        countTask = new TimerCountTask(labelTimer);
        timerRecord.scheduleAtFixedRate(recorderTask1, 0, delay);
        timerCount.scheduleAtFixedRate(countTask, 0, 1000);
    }
    private void stopScreenRecording() 
    {
        System.out.println("stop recording call");
        ScreenRecord.log("stop recording call");
        ScreenRecord.end_time = simpleDate.format(new Date());
        LoginDemo.icon=Toolkit.getDefaultToolkit().getImage("test2.jpg");
        ScreenRecord.start_time=ScreenRecord.dateS;
        System.out.println("start time"+ScreenRecord.start_time+"end time video---"+ScreenRecord.end_time+"file_name"+ScreenRecord.file_name+"start_time="+ScreenRecord.agent_id);
        ScreenRecord.log("start time"+ScreenRecord.start_time+"end time video---"+ScreenRecord.end_time+"file_name"+ScreenRecord.file_name+"start_time="+ScreenRecord.agent_id);
        try {   
            URL url = new URL("http://192.168.1.223:8989/DSR/Getstatus2?file_name="+ScreenRecord.file_name+"&mobile="+ScreenRecord.mobile_number+"&call_number="+ScreenRecord.call_number+"&agent_id="+ScreenRecord.agent_id+"&service_id="+ScreenRecord.service_id+"&audio_file_name="+ScreenRecord.file_name+"&video_file_name="+ScreenRecord.file_name+"&start_time="+ScreenRecord.start_time+"&end_time="+ScreenRecord.end_time);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("charset", "utf-8");
            conn.connect();
            int responseCode = conn.getResponseCode();
        } catch (Exception e) {
            System.err.println("URL to save Data into Database Not Work Check it manualy");
            ScreenRecord.log("URL to save Data into Database Not Work Check it manualy");
        } 
        RecordTimer.stop();
        //cancle the timer for time counting here
        timerCount.cancel();
        timerCount.purge();
        // stop the timer from executing the task here
        timerRecord.cancel();
        timerRecord.purge();
        recorderTask1.cancel();
        countTask.cancel();
        try 
        {
            encoder.finish();// finish  encoding
            System.out.println("encoding finish " + (RecordTimer.getTimeInSec()) + "s");
            ScreenRecord.log("encoding finish " + (RecordTimer.getTimeInSec()) + "s");
        } 
        catch (IOException ex) 
        {
            System.out.println("error-"+ex);
            ScreenRecord.log("error-"+ex);
        }
    }
    public static void makeVideoFromImages(List<BufferedImage> imageList, File file) throws IOException {
        AWTSequenceEncoder sequenceEncoder = AWTSequenceEncoder.createSequenceEncoder(file, 3);

        for (int i = 0; i < imageList.size(); i++) {

            System.out.println("list encode " + i);
            sequenceEncoder.encodeImage(imageList.get(i));
        }
        sequenceEncoder.finish();
    }
}



