package desktopscreenrecorder;

import java.io.IOException;
import static jdk.nashorn.internal.objects.NativeRegExp.exec;
public class ExecFfmpeg {
    

     public static void executeFfMpeg(String exec) {
              Runtime runtime = Runtime.getRuntime();

        try {
            Process process;
            process = runtime.exec(exec);
            int exitVal = process.waitFor();
            System.out.println("Exited with error code " + exitVal);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
    
}
