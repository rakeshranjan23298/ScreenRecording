package mypack;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Getstatus2 extends HttpServlet {
    private Object conn;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();



 String configFilepath = "";
			try
            {
                File f = new File(configFilepath);
                //System.out.println(configFilepath);
                if(f.exists())
                {
                    parser k = new parser();
                    k.parse(f);
                }
                else
                {
                    String rootPath = request.getRealPath("/");
                    f = new File(rootPath+"config.xml");
                    if(f.exists())
                    {
                        parser k = new parser();
                        k.parse(f);
                    }
                    else
                    {
                        rootPath += "\\";
                        f = new File(rootPath+"config.xml");
                        if(f.exists())
                        {
                            parser k = new parser();
                            k.parse(f);
                        }
                        else
                        {
                           
                            out.println("Config file not found");
                            
                        
                        }
                    }
                }
            }
                catch(Exception e)
            {
                
                out.println(e);
               
            }

        
        try {
            String type = request.getParameter("type");
                try {
                    Connection conn = config2.getconnection();
                    if (conn == null) {
                        out.println("Error : Could not connect to database");
                        return;
                    }
                    
                    PreparedStatement prest = null;
                    ResultSet rs = null;
                    
                    String file_name = request.getParameter("file_name");
                    String mobile = request.getParameter("mobile");
                    String call_number = request.getParameter("call_number");
                    String agent_id = request.getParameter("agent_id");
                    String service_id = request.getParameter("service_id");
                    
                    String audio_file_name = request.getParameter("audio_file_name");
                    String video_file_name = request.getParameter("video_file_name");
                    String start_time = request.getParameter("start_time");
                    String end_time = request.getParameter("end_time");

                    
                        String sql = "insert into tbl_screen_recording_data("
                                + "file_name,"
                                + "mobile,"
                                + "call_number,"
                                + "agent_id,"
                                + "service_id,"
                                + "audio_file_name,"
                                + "video_file_name,"
                                + "start_time,"
                                + "end_time"
                                + ") "
                                + "values "
                                + " ("
                                + "'" + file_name + "',"
                                + "'" + mobile + "',"
                                + "'" + call_number + "',"
                                + "'" + agent_id + "',"
                                + "'" + service_id + "',"
                                + "'" + audio_file_name + "',"
                                + "'" + video_file_name + "',"
                                + "'" + start_time + "',"
                                + "'" + end_time + "'"
                                + ")";
                System.out.println(" insert record data Servlet  -- " + sql);
                prest = conn.prepareStatement(sql);
                prest.executeUpdate();                   
                if (prest.getUpdateCount() > 0) {
                    out.println("success");
                }
//                    rs.close();
//                    prest.close();
//                    conn.close(); 
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (prest != null) {
                try {
                    prest.close();
                } catch (Exception e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            out.println("Exception : " + e);
        }
//            }           
//            else {
//                
//            }

} catch (Exception e) {
            out.println(e);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Getstatus2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Getstatus2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
