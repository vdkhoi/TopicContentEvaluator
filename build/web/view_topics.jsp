<%-- 
    Document   : VIDM
    Created on : Mar 20, 2009, 12:46:33 PM
    Author     : Sinh Nguyen Van
--%>


<%@page import="java.nio.file.Paths"%>
<%@page import="java.nio.file.Path"%>
<%@page import="java.nio.file.Files"%>
<%@page import="java.util.stream.Stream"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*,java.util.*,javax.naming.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Select topic</title>
        <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

        <link rel="stylesheet" href="css/evaluate_page.css">
        <script src="scripts/evaluate_page.js"></script>

        <script src="https://code.jquery.com/jquery-1.10.2.js"></script>

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    </head>
    <body>
        <%! public ArrayList<String> listFilesForFolder(File folder) {
                ArrayList<String> l = new ArrayList<String>();
                for (File fileEntry : folder.listFiles()) {
                    if (!fileEntry.getName().startsWith("_")) {
                        l.add(fileEntry.getName());
                    }
                }
                return l;
            }
        %>
        
        <%! public ArrayList<String> listResults(File folder) {
                ArrayList<String> l = new ArrayList<String>();
                for (File fileEntry : folder.listFiles()) {
                    if (fileEntry.getName().startsWith("_")) {
                        l.add(fileEntry.getName());
                    }
                }
                return l;
            }
        %>

        <table class="table table-condensed">
            <caption align="center">List of TOPICS</caption>
            <thead> 
                <tr> 
                    <th>Topic</th> 
                    <th>Selection</th> 
                </tr> 
            </thead>
            <%
                //String data_path = "D:\\topic_data\\";
                String data_path = "/home/khoi/topic_data/";
                ArrayList<String> l = listFilesForFolder(new File(data_path));
                session.setAttribute("PATH", data_path);
                for (int i = 0; i < l.size(); i++) {
                    out.println("<form action=\"ServletController\" method=\"POST\">");
                    out.println("<tr><td class=\"info\">" + l.get(i) + "</td><td class=\"info\">");
                    out.println("<input name=\"topic_id\" type=\"hidden\" value=\"" + l.get(i) + "\">");
                    out.println("<input class=\"btn btn-primary\" type=\"submit\" name=\"action\" value=\"Select Topic\"></td></tr>");
                    out.println("</form>");
                }
            %>
        </table>
        
        <table class="table table-condensed">
            <caption align="center">List of RESULTS</caption>
            <thead> 
                <tr> 
                    <th>Topic</th> 
                    <th>Selection</th> 
                </tr> 
            </thead>
            <%
                //String data_path = "D:\\topic_data\\";
                
                ArrayList<String> lr = listResults(new File(data_path));
                for (int i = 0; i < lr.size(); i++) {
                    out.println("<form action=\"ServletController\" method=\"POST\">");
                    out.println("<tr><td class=\"info\">" + lr.get(i) + "</td><td class=\"info\">");
                    out.println("<input name=\"result_topic_id\" type=\"hidden\" value=\"" + lr.get(i) + "\">");
                    out.println("<input class=\"btn btn-primary\" type=\"submit\" name=\"action\" value=\"Download\"></td></tr>");
                    out.println("</form>");
                }
            %>
        </table>
    </body>
</html>