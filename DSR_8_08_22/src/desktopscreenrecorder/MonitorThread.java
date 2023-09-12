package desktopscreenrecorder;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.net.Socket;

public class MonitorThread extends Thread
{
    final private Socket socket;
    final private String hostip;
    final private String res = "HTTP/1.1 200 OK\r\nDate: "+new Date()+"\r\nContent-Type: application/xml; charset=UTF-8"
                               + "\r\nContent-Length: len\r\nAccess-Control-Allow-Origin: *\r\n\r\nsendtext"; 
    private String request = "";
    private Object res_text;
    private String restext;
    private String acdip;
    private int acdport;
    public MonitorThread(Socket accept) 
    {
        this.socket = accept;
        this.hostip = socket.getRemoteSocketAddress().toString().substring(0, socket.getRemoteSocketAddress().toString().lastIndexOf(":")).replace("/", "");
    }
    @Override 
    @SuppressWarnings("ConvertToTryWithResources")
    public void run()
    {
        try 
        {
            InputStreamReader in = new InputStreamReader(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            
            BufferedReader inr = new BufferedReader(in);
            String line = inr.readLine();
            String ipAddress = socket.getRemoteSocketAddress().toString();
            while(!line.isEmpty())
            {
                if(line.contains("GET") || line.contains("POST"))
                {
                    if(!line.contains("favicon"))
                    {
                        line = line.replace("GET", "").replace("POST", "").replace("HTTP/1.0", "").replace("HTTP/1.1", "").replace("/", "").replace("?", "").trim();
                        request = line;
                    }
                }
                line = inr.readLine();
            }
            System.out.println("request-"+request);
            res_text = "received";
            if(request.contains("&"))
            {
                String[] reqparse = request.split("&");
                String apirequest = reqparse[0].split("=")[1].trim();
                if(apirequest.equalsIgnoreCase("login"))
                {
                    desktopscreenrecorder.LoginDemo.disableclose();
                }
                if(apirequest.equalsIgnoreCase("startrecord"))
                {
                    desktopscreenrecorder.AudioRecorder.startAudio=true;
                    desktopscreenrecorder.AudioRecorder.stopAudio=false;
                    desktopscreenrecorder.DesktopScreenRecorder.startRecording=true;
                    desktopscreenrecorder.DesktopScreenRecorder.stopRecording=false;
                    ScreenRecord.agent_id = reqparse[1].split("=")[1].trim();
                    ScreenRecord.service_id = reqparse[2].split("=")[1].trim();
                    ScreenRecord.mobile_number = reqparse[3].split("=")[1].trim();
                    ScreenRecord.call_number = reqparse[4].split("=")[1].trim();
                }
                else if(apirequest.equalsIgnoreCase("stoprecord"))
                {
                    System.out.println("stop record in montore theread");
                    desktopscreenrecorder.DesktopScreenRecorder.startRecording=false;
                    desktopscreenrecorder.DesktopScreenRecorder.stopRecording=true;
                    desktopscreenrecorder.AudioRecorder.startAudio=false;
                    desktopscreenrecorder.AudioRecorder.stopAudio=true;
                }
                else if(apirequest.equalsIgnoreCase("logout"))
                {
                    System.exit(0);
                }
            }
            int length = res_text.toString().length();
            String httpResponse = res.replace("len", Integer.toString(length)).replace("sendtext", res_text.toString());
            out.write(httpResponse.getBytes("UTF-8"));
            
            Thread.sleep(100);
            inr.close();
            in.close();
            out.close();
            socket.close();
        }
        catch(Exception ex)
        {
            System.out.println("MonitorThread -"+ex);
            
        }
    }
}
