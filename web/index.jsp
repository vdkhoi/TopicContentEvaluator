<%-- 
    Document   : VIDM
    Created on : Mar 20, 2009, 12:46:33 PM
    Author     : Sinh Nguyen Van
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*,java.util.*,javax.naming.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
   
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Evaluate content</title>
        <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    </head>
    <body style="height: 5000px; min-height: 100%;">

        <%
            String user_name = "";
            byte query_id = 0;
            long doc_id = 0;
            String query_content = "";
            String doc_url = "";
            byte labeled = -1;
            String timestamp = "";

            if ((user_name = request.getParameter("user_name")) != null) {
                query_id = Byte.parseByte(request.getParameter("query_id"));
                doc_id = Long.parseLong(request.getParameter("doc_id"));
                labeled = Byte.parseByte(request.getParameter("labeled"));
                DataProcessingPackage.URLManager.Update(user_name, doc_id, query_id, labeled);
            }


            ResultSet rs = DataProcessingPackage.URLManager.Select("khoi");
            boolean disabled = false;
            if (rs.next()) {
                user_name = rs.getString("user_name");
                query_id = rs.getByte("query_id");
                query_content = rs.getString("query_content");
                doc_id = rs.getLong("doc_id");
                doc_url = rs.getString("doc_url");
                timestamp = rs.getString("doc_valid_timestamp");
            }
            else {
                disabled = true;
            }
            rs.close();
        %>
        <div style="float:top; top:0px; left:0px; width:100%; height:1%; border:1px; margin:0; padding:0">
        <%  if (! disabled) {%>
            <form action ="view_url.jsp" method="GET">
                <table style="width: 100%; border: 2px; border-color: blue">
                    <tr>
                        <td style="width: 15%; background-color: activecaption">
                            User: <strong style="font-weight: bold"><%=user_name %></strong>
                            <input type="hidden" name="user_name" value="<%=user_name%>"/>
                        </td>
                        <td style="width: 70%; background-color: azure">
                            <input type="radio" name="labeled" value="0"/> Completely Irrelevant
                            <input type="radio" name="labeled" value="1"/> Irrelevant
                            <input type="radio" name="labeled" value="2"/> Neutral
                            <input type="radio" name="labeled" value="3"/> Relevant
                            <input type="radio" name="labeled" value="4"/> Completely Relevant
                        </td>
                        <td colspan="2" style="width: 15%; background-color: lavender">
                            Query: <strong style="font-weight: bold"><%=query_content %></strong>
                            <input type="hidden" name="query_id" value="<%=query_id %>"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" style="width: 90%; background-color: lavender; overflow: hidden">
                            URL: <strong style="font-weight: bold"><%=doc_url %></strong>
                            <input type="hidden" name="doc_id" value="<%=doc_id %>"/>
                        </td>
                        <td style="background-color: lavender">
                            <input type="Submit" value="Next" name="Add"/>
                        </td>
                    </tr>

                </table>
            </form>
        <%  } else { out.print("No more document"); } %>
        </div>
        <div style="float: bottom; height: 100%; min-height: 100%; display: flex;">
            <div style="width:15%; border:1px; border-color: lightslategrey; margin:0; padding:0; background-color: lightsteelblue ">
                Topic 01
                <ul type="disc">
                    <li> Event 1
                    <li> Event 2
                </ul>
            </div>
            <div style="width:85%; border:1px; border-color: lightslategrey; margin:0; padding:0">
            <%  if (! disabled) {%>    
            <iframe seamless = "seamless" src="<%="https://web.archive.org/web/" + timestamp.substring(0, 4) + timestamp.substring(5, 7) + timestamp.substring(8, 10) + timestamp.substring(11, 13) + timestamp.substring(14, 16) + timestamp.substring(17, 19) + "/" + doc_url %>" style="float:left; top:0px; left:0px; bottom:0px; right:0px; width:100%; height:100%; border:none; margin:0; padding:0; overflow:hidden; z-index:10;"/>
            <%  } else { out.print("No more document"); } %>
            </div>
        </div>
    </body>
</html>