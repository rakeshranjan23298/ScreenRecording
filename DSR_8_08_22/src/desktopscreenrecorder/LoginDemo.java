package desktopscreenrecorder;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginDemo extends JFrame implements ActionListener {

    private static Dialog d;
    static JLabel user_label, password_label, ext_label, message;
    JTextField userName_text, ext_text;
    JPasswordField password_text;
    JButton submit, setting, login;
    static JButton close;
    
    static String url="";
    static String path="";
    static String Filename="";

    static JFrame frame = new JFrame();
    static Image icon=Toolkit.getDefaultToolkit().getImage("test.jpg");
    
    JButton button = new JButton("Click Me");
    static public void disableclose()
    {
        
        message.setText("Login Success");
        message.setFont(new Font("Serif", Font.BOLD, 25));        
        close.setVisible(false);
        
    }
    
    static JFrame f = new JFrame("Sampark Recording ");
    LoginDemo() throws IOException {
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (screenSize.width) / 2 - 225;
        int h = (screenSize.height) / 2 - 225;  

        user_label = new JLabel("AGENT ID", SwingConstants.CENTER);
        userName_text = new JTextField();        
        user_label.setBounds(15, 60, 150, 35);
        userName_text.setBounds(190, 60, 180, 35);
        
        password_label = new JLabel("PASSWORD", SwingConstants.CENTER);
        password_text = new JPasswordField();        
        password_label.setBounds(15, 110, 150, 35);
        password_text.setBounds(190, 110, 180, 35);
        
        ext_label = new JLabel("EXTENSION ", SwingConstants.CENTER);
        ext_label.setBounds(15, 160, 150, 35);
        ext_text = new JTextField();
        ext_text.setBounds(190, 160, 180, 35);
        
        login = new JButton("LOGIN");
        login.setBounds(15, 225, 100, 30);
        setting = new JButton("SETTING");
        setting.setBounds(140, 225, 100, 30);
        close = new JButton("Close");
        close.setBounds(265, 225, 100, 30);
        message = new JLabel();
        message.setBounds(50, 275, 300, 30);
        
        user_label.setFont(new Font("Serif", Font.BOLD, 20));        
        userName_text.setFont(new Font("Serif", Font.BOLD, 20));
        password_label.setFont(new Font("Serif", Font.BOLD, 20));
        password_text.setFont(new Font("Serif", Font.BOLD, 20));
        ext_label.setFont(new Font("Serif", Font.BOLD, 20));       
        ext_text.setFont(new Font("Serif", Font.BOLD, 20));        
        message.setFont(new Font("Serif", Font.BOLD, 20));
        
        user_label.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 233), 2));        
        password_label.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 233), 2));        
        ext_label.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 233), 2));
        userName_text.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 233), 2));        
        ext_text.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 233), 2));        
        password_text.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 233), 2));
        
        f.add(user_label);
        f.add(password_label);
        f.add(ext_label);
        f.add(ext_text);
        f.add(userName_text);
        f.add(password_text);
        f.add(login);
        f.add(setting);
        f.add(message);
        f.add(close);        
//--------------------------------------------------------------------------------------------------------------------------------
        Image icon=Toolkit.getDefaultToolkit().getImage("test.jpg");
       
        f.setIconImage(icon);
        f.getContentPane().setBackground(new java.awt.Color(245, 212, 163));
        
        
        
        JLabel label = new JLabel("Sampark Recordings", SwingConstants.CENTER);
        label.setBounds(75, 5, 200, 50);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setForeground(new Color(0, 138, 230));
        
        

        f.setEnabled(true);
        f.add(label);    
        f.setResizable(false);
        f.setLocation(w, h);
        f.setSize(450, 385);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(f.DO_NOTHING_ON_CLOSE);
        
        
        
        
        
//  =----      ---------------------------------Check For URL & Path File Exists or not--------------------
        File f1=new File("URL_Path.txt");
        Scanner dataReader;
        
        try {
            dataReader = new Scanner(f1);
            
            while(dataReader.hasNextLine())
            {
                path = dataReader.nextLine();
            }
            dataReader.close();

            System.out.println("filePath ="+path+"" +url);
        } catch (FileNotFoundException ex) {
            System.err.println("file NOT FOUND--- For path OR url"+ex);
        }
        
//        ----------------------------------------------------------------------------------Close Button Method -----------
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               System.exit(0);
            }
        });


//------------------------------------------------------------- Login Button ( Call Agent Url & start jdk)--- -----------------------------
            login.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) 
                {
                    String line1="";
                    String line2="";
                    int i=0;
                try(BufferedReader br = new BufferedReader(new FileReader("URL_Path.txt"))) 
                {
                    if(i==0)    // Url
                    {
                        line1 = br.readLine();
                        i++;
                    }
                    if(i==1)    // Path
                    {
                        line2=br.readLine();
                        i++;
                    }
                }catch (IOException ex) 
                {
                    Logger.getLogger(LoginDemo.class.getName()).log(Level.SEVERE, null, ex);
                }                    
                System.out.println("urll length ="+url.length());
                if(i!=0)
                {
                    String userName = userName_text.getText();
                    String password = password_text.getText();
                    String ext = ext_text.getText();
                    message.setText("Welcome!!!!");
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        try {
                            Desktop.getDesktop().browse(new URI(line1+"?agent_id=" + userName + "&agent_pwd=" + password + "&ext_num=" + ext));
                        } catch (Exception ex) {
                            ScreenRecord.log("agent URL not Found in File"+ex);
                            System.err.println("agent URL not Found in File"+ex);
                        }
                    }
                    f.setDefaultCloseOperation(f.DO_NOTHING_ON_CLOSE);
                    try {
                        ScreenRecord.startRecord();

                    } catch (Exception ex) {
                        Logger.getLogger(LoginDemo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else
                    message.setText("Path || Agent Url Not Found ");
            }
        });
        setting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            f.setEnabled(false);
                
                JFrame settingFrame = new JFrame(" Setting ");
                JLabel password_label1 = new JLabel("PASSWORD", SwingConstants.CENTER);
                password_label1.setBounds(15, 60, 120, 35);
                JPasswordField password_text1 = new JPasswordField();
                password_text1.setBounds(160, 60, 250, 35);
                
                JButton browse = new JButton("Recording Path");
                browse.setBounds(15, 110, 120, 35);
                browse.setSize(120, 35);
                browse.setEnabled(false);

                JTextField path_text = new JTextField();
                path_text.setBounds(160, 110, 250, 35);
                path_text.setEnabled(false);

                JLabel url_label = new JLabel("Url:- ", SwingConstants.CENTER);
                url_label.setBounds(15, 160, 120, 35);
                JTextField url_text = new JTextField();
                url_text.setBounds(160, 160, 250, 35);
                url_text.setEnabled(false);

                JButton verify = new JButton("Verify");
                verify.setBounds(50, 250, 150, 30);

                JButton save = new JButton("Save");
                save.setBounds(250, 250, 150, 30);
                save.setEnabled(false);
                
                JLabel message1 = new JLabel();
                message1.setBounds(50, 200, 200, 30);

                settingFrame.add(password_label1);
                settingFrame.add(password_text1);
                
                settingFrame.add(path_text);
                settingFrame.add(browse);

                settingFrame.add(url_label);

                settingFrame.add(url_text);

                settingFrame.add(verify);
                settingFrame.add(save);
                settingFrame.add(message1);
                
                settingFrame.setIconImage(icon);
                settingFrame.setSize(490, 410);
                            
        password_label1.setFont(new Font("Serif", Font.BOLD, 15));        
        password_text1.setFont(new Font("Serif", Font.BOLD, 15));
        browse.setFont(new Font("Serif", Font.BOLD, 15));
        
        path_text.setFont(new Font("Serif", Font.BOLD, 15));
        url_label.setFont(new Font("Serif", Font.BOLD, 15));       
        url_text.setFont(new Font("Serif", Font.BOLD, 15));        
        
        
settingFrame.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            f.setEnabled(true);
            settingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    }
});
        message1.setFont(new Font("Serif", Font.BOLD, 20));
        
        password_label1.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 233), 2));        
        password_text1.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 233), 2));        
        browse.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 233), 2));
        path_text.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 233), 2));        
        url_label.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 233), 2));        
        url_text.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 233), 2));
        
        settingFrame.getContentPane().setBackground(new java.awt.Color(245, 212, 163));                
        settingFrame.getRootPane().setBorder(
        BorderFactory.createMatteBorder(8, 8, 8, 8, Color.getHSBColor(159, 96, 96))
        );
        
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int w = (screenSize.width) / 2 - 225;
                int h = (screenSize.height) / 2 - 225;
                settingFrame.setLocation(w, h);
                settingFrame.setLayout(null);
                settingFrame.setVisible(true);
                
                settingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//-----------------------------------------------------------------------------------------read path and URL and display    ----------------------------
                try(BufferedReader br = new BufferedReader(new FileReader("URL_Path.txt"))) 
                {
                    int i=0;
                    if(i==0)
                    {
                    String line = br.readLine();
                    url_text.setText(line);  
                    i++;
                    }
                    if(i==1)
                    {
                        String line=br.readLine();
                        path_text.setText(line);
                    }
                }catch (IOException ex) 
                {
                    Logger.getLogger(LoginDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
//---------------------------------------------------------------------------------end to read from realtive path file  ---------------------------------------------------------------------------
                verify.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String password = password_text1.getText();
                        
                        if (password.trim().equals("admin")) 
                        {
                            path_text.setEnabled(true);
                            browse.setEnabled(true);
                            url_text.setEnabled(true);
                            save.setEnabled(true);
                            message1.setText(" Login Success!! ");
                            verify.setEnabled(false);
                        }
                        else 
                                message1.setText(" Wrong Password!!   Try Again....");
                    }
                });
//=-------------------------------------------------    to pass path and  url       ------------------------------------            
                browse.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser filechooser=new JFileChooser();
                        filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        int option=filechooser.showOpenDialog(frame);
                        if(option == JFileChooser.APPROVE_OPTION)
                        {
                            File file=filechooser.getSelectedFile();
                            path_text.setText(file.toString());
                        }
                    }
                });
//         -----------------------------------  ---------------- Save button to save path & url as file  --------------
                save.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {   
                        f.setEnabled(true);
                        path = path_text.getText();
                        path+="\\";
                        System.out.println("path="+path);
                        url= url_text.getText();
                        Filename="URL_Path";

                    File myObj = new File(Filename + ".txt");  
                    try {
                        if (myObj.createNewFile()) {
                            System.out.println("File created: " + myObj.getName());
                            System.out.println("Absolute path: " + myObj.getAbsolutePath());

                            FileWriter urlWrite = new FileWriter(Filename + ".txt",true); 
                            urlWrite.write(url+"\n");
                            urlWrite.write(path+"\n");
                            urlWrite.close();                            
                            System.out.println("url="+url+"path="+path);            

                        } else {
                            System.out.println("File already exists.");  
                            myObj.delete();
                            FileWriter urlWrite = new FileWriter(Filename + ".txt",true); 
                            urlWrite.write(url+"\n");
                            urlWrite.write(path+"\n");
                            urlWrite.close();                     
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(LoginDemo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        settingFrame.dispose();
                    }
                });
            }
        });
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        new LoginDemo();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
