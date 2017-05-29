<%-- 
    Document   : VIDM
    Created on : Mar 20, 2009, 12:46:33 PM
    Author     : Duy Khoi Vo
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
        <form action="ServletController" method="POST">
            <%
                //String topic_file = (String) session.getAttribute("TOPIC");
                //String path = (String) session.getAttribute("PATH");
                Integer eventNum = (Integer)session.getAttribute("EVENT");
                if (eventNum == null) {
                    eventNum = new Integer(0);
                    session.setAttribute("EVENT", eventNum);
                }
                DataAccessPackage.DataInitialize data = (DataAccessPackage.DataInitialize)session.getAttribute("DATA");
                DataModelPackage.Event event = data.topicData.getEvent(eventNum);
            %>
            <div class="navbar navbar-inverse fixed-top" data-spy="affix" data-offset-top="0"> 
            
                <table class="table table-condensed">
                    <tr>
                        <td class="col-md-4">
                            <p><%=data.topicData.getTopic_id()%><br><%=data.topicData.getTopic()%>
                        </td>
                        <td class="col-md-8">
                            <p><%=event.getEventDescription()%>
                        </td>    
                    </tr>
                    <tr>
                        <td>
                            <p><%=data.topicData.getCategory()%>
                        </td>
                        <td>
                            <input class="btn btn-primary" type="button" value="Select Topic" onclick="selectTopic()">
                            <input class="btn btn-primary" style="display:none" type="submit" name="action" value="Next Event">
                            <input class="btn btn-primary" style="display:none" type="button" value="First Domain" onClick="topBookmark('<%=event.getDomains().get(0) %>')">
                            <input class="btn btn-primary" style="display:none" type="button" value="abcnews.go.com" onClick="nextBookmark()">
                            <input class="btn btn-primary" type="submit" name="action" value="Save & Next Event">
                        </td>
                    </tr>
                </table>
            </div>
            
            <div class="container-fluid my-style">
                <table class="table">
                    <thead>
                        <tr>
                            <th class="col-md-1">Domain</th>
                            <th class="col-md-3"> Title</th>
                            <th class="col-md-8">Snippet</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            int numOfDocs = 0;
                            for (int i = 0; i < event.getDomains().size(); i++) {
                                ArrayList<DataModelPackage.DocumentContent> domainDocs = event.getResults().get(i);
                                for (int j = 0; j < domainDocs.size(); j++) {
                                    if (j == 0) {
                                        out.println("<tr><td><a class=\"bookmarked\" name=\"" + event.getDomains().get(i) + "\">" + event.getDomains().get(i) + "</a></td>");
                                    } else {
                                        out.println("<tr><td>" + event.getDomains().get(i) + "</td>");
                                    }
                                    DataModelPackage.DocumentContent doc = domainDocs.get(j);
                                    String labelVal = doc.getLabel();
                                    out.println("<td><a target=\"_blank\" href=\"" + doc.getUrl() + "\">" + doc.getTitle() + "</a><br>");

                                    out.println("<input type=\"radio\" name=\"label" + (numOfDocs ) + "\" style=\"display:none\" value=\"null\" >");
                                    if (labelVal != null) {
                                        out.println("<input type=\"radio\" name=\"label" + (numOfDocs ) + "\" value=\"0\" " + (labelVal.equals("0")? "checked" : "") + ">None");
                                        out.println("<input type=\"radio\" name=\"label" + (numOfDocs ) + "\" value=\"1\" " + (labelVal.equals("1")? "checked" : "") + ">Slight");
                                        out.println("<input type=\"radio\" name=\"label" + (numOfDocs++ ) + "\" value=\"2\" " + (labelVal.equals("2")? "checked" : "") + ">Rel </td>");
                                    }
                                    else
                                    {
                                        out.println("<input type=\"radio\" name=\"label" + (numOfDocs ) + "\" value=\"0\" >None");
                                        out.println("<input type=\"radio\" name=\"label" + (numOfDocs ) + "\" value=\"1\" >Slight");
                                        out.println("<input type=\"radio\" name=\"label" + (numOfDocs++ ) + "\" value=\"2\" >Rel </td>");
                                    }

                                    out.println("<td>" + doc.getSnippet() + "</td></tr>");
                                }

                            }
                            session.setAttribute("DOCS", new Integer(--numOfDocs));
                        %>
                    </tbody>
                </table>
            </div>

        </form>
    </body>
</html>