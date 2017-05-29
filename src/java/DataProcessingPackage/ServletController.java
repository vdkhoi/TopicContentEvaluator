/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataProcessingPackage;

import DataModelPackage.DocumentContent;
import DataModelPackage.Event;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

/**
 *
 * @author Khoi
 */
public class ServletController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(true);
            String action = request.getParameter("action");
            //System.out.println("Here" + action);
            if (action.compareTo("Select Topic") == 0){
                String topic_id = request.getParameter("topic_id");
                String path = (String)session.getAttribute("PATH");
                DataAccessPackage.DataInitialize data = new DataAccessPackage.DataInitialize(path + topic_id);
                data.ResultInitialize(path + "_" + topic_id);
                session.setAttribute("TOPIC", topic_id);
                session.setAttribute("DATA", data);
                session.setAttribute("EVENT", new Integer(0));
                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/evaluate_page.jsp");
                dispatcher.forward(request,response);
            } else if (action.compareTo("Save & Next Event") == 0){
                System.out.println("Saving...");
                String topic_id = (String)session.getAttribute("TOPIC");
                String path = (String)session.getAttribute("PATH");
                DataAccessPackage.DataInitialize data = (DataAccessPackage.DataInitialize)session.getAttribute("DATA");
                int currentDoc = 0;
                String labelStr ;
                Integer eventNum = (Integer)session.getAttribute("EVENT");
                
                BufferedWriter bw = new BufferedWriter (new FileWriter(new File(path + "_" + topic_id)));
                for (int i = 0; i < eventNum + 1; i ++) {
                    Event event = data.topicData.getEvent(i);
                    ArrayList<String> domains = event.getDomains();
                    for (int j = 0; j < domains.size(); j ++) {
                        ArrayList<DocumentContent> docs = event.getDomainDoc(j);
                        for (int k = 0; k < docs.size(); k ++){
                            DocumentContent doc = docs.get(k);
                            labelStr = request.getParameter("label" + currentDoc);
                            if (labelStr == null || labelStr.compareTo("null") == 0) {
                                labelStr = "0";
                            }
                            //System.out.println(labelStr + "\t" + topic_id + "/" + i + "\t" +
                            //                    domains.get(j) + "\t" + doc.getTitle() + 
                            //                    "\t" + doc.getDate() + "\t" + doc.getUrl() +
                            //                    "\t" + doc.getSnippet());
                            
                            bw.write(labelStr + "\t" + topic_id.replaceFirst(".txt", "") + "/" + i + 
                                                "\t" + domains.get(j) + "\t" + doc.getTitle() + 
                                                "\t" + doc.getDate() + "\t" + doc.getUrl() +
                                                "\t" + doc.getSnippet() + "\r\n");
                            currentDoc ++;
                        }
                    }
                }
                bw.close();
                
                
                eventNum++;
                session.setAttribute("EVENT", eventNum);
                
                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/evaluate_page.jsp");
                dispatcher.forward(request,response);
            } else if (action.compareTo("Next Event") == 0){
                Integer eventNum = (Integer)session.getAttribute("EVENT");
                eventNum++;
                session.setAttribute("EVENT", eventNum);
                //System.out.println("Event: " + eventNum);
                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/evaluate_page.jsp");
                dispatcher.forward(request,response);
        
            } else if (action.compareTo("Download") == 0){
                String result_topic_id = request.getParameter("result_topic_id");
                String path = (String)session.getAttribute("PATH");
                response.setContentType("text/csv");
                response.setHeader("Content-disposition", "attachment; filename=\"" + result_topic_id + "\"");
                response.setHeader("Cache-Control", "no-cache");
                response.setHeader("Expires", "-1");
                BufferedReader br = new BufferedReader(new FileReader(new File(path + result_topic_id)));
                String line = null;
                while ((line = br.readLine()) != null) {
                    out.write(line + "\r\n");
                }
                br.close();
            }
                    

        } finally {
            out.close();
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
