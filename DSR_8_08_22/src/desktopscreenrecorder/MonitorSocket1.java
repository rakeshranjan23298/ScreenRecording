package desktopscreenrecorder;

//import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.IOException;
import java.net.ServerSocket;


public class MonitorSocket1 extends Thread
{
    static boolean li=true;

    public MonitorSocket1(String Name) 
    {
        this.setName(Name);
        //set name here

//        run();
    }
    
    @Override
    public void run()
    {
        ServerSocket listener = null;
        try 
        {
            listener = new ServerSocket(9696);
        } 
        catch (IOException ex) 
        {
            System.err.println("MonitorSocket--1-"+ex);
        }
        try 
        {
            while (true) 
            {
//                System.out.println("listner  in MonitorSocket ="+listener);
                if(listener != null)
                {
                    new MonitorThread(listener.accept()).start();  
                }
            }
        } 
        catch (IOException ex) 
        { 
            System.err.println("MonitorSocket  -1  ="+ex);
        }
        finally 
        {
            try 
            {
                if(listener != null)
                {
                    listener.close();
                }
            } 
            catch (IOException ex) 
            {
                System.err.println("in MonitorSocket--1 IO-"+ex);
                
            }
        }
    }
}