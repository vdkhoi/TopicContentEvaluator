/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessPackage;

import DataModelPackage.DocumentContent;
import DataModelPackage.Event;
import DataModelPackage.TopicData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Khoi
 */
public class DataInitialize {
    public TopicData topicData;
    
    public DataInitialize(String filename) {
        String line ;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
            line = br.readLine();
            String[] topic_details = line.split("=");
            line = br.readLine();
            String[] category = line.split("=");
            line = br.readLine();
            String[] description = line.split("=");
            topicData = new TopicData();
            topicData.setTopic_id(topic_details[1].trim());
            topicData.setCategory(category[1].trim());
            topicData.setTopic(description[1].trim());
            int numOfEvents = Integer.parseInt(br.readLine().split("=")[1].trim());
            Event[] events  = new Event[numOfEvents];
            for (int i = 0; i < numOfEvents; i ++) {
                line = br.readLine();
                String[] eventDetails = line.split("\t");
                events[i] = new Event();
                events[i].setEvent_id(i);
                events[i].setTime(eventDetails[1]);
                events[i].setEventDescription(eventDetails[2]);
            }
            String current_domain = "";
            int[] current_domain_idx = new int[numOfEvents];
            for (int i = 0; i < numOfEvents; i ++) {
                current_domain_idx[i] = -1;
            }
            
            int current_event_id = 0;
            while ((line = br.readLine()) != null) {
                String[] resultContent  = line.split("\t");
                current_event_id = Integer.parseInt(resultContent[0].replaceFirst(topic_details[1].trim() + "/", ""));
                
                if (current_domain.compareTo(resultContent[1]) == 0 && current_domain.length() > 0) {
                    DocumentContent doc = new DocumentContent();
                    doc.setDate(resultContent[3].trim());
                    doc.setUrl(resultContent[4].trim());
                    doc.setSnippet(resultContent[5].trim().replace("<!--", ""));
                    doc.setTitle(resultContent[2].trim().replace("<!--", ""));
                    events[current_event_id].AddResult(current_domain_idx[current_event_id], doc);
                }
                else if (current_domain.compareTo(resultContent[1]) != 0) {
                    DocumentContent doc = new DocumentContent();
                    doc.setDate(resultContent[3].trim());
                    doc.setUrl(resultContent[4].trim());
                    doc.setSnippet(resultContent[5].trim().replace("<!--", ""));
                    doc.setTitle(resultContent[2].trim().replace("<!--", ""));
                    current_domain_idx[current_event_id] ++;
                    current_domain = resultContent[1].trim();
                    events[current_event_id].AddDomain(resultContent[1].trim());
                    events[current_event_id].AddResult(current_domain_idx[current_event_id], doc);
                }
            }
            topicData.addEvents(events);
            br.close();
        } catch (Exception ex) {
            Logger.getLogger(DataInitialize.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ResultInitialize(String filename) {
        String line ;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                String topic_id = topicData.getTopic_id();
                int current_event_id = Integer.parseInt(parts[1].replaceFirst(topic_id + "/", ""));
                Event event = topicData.getEvent(current_event_id);
                event.setLabel(parts[5], parts[3], parts[0]);
            }
            
            br.close();
        } catch (Exception ex) {
            Logger.getLogger(DataInitialize.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args){
        DataInitialize data = new DataInitialize("D:\\Trying_Tools\\TopicContentEvaluator\\TOPIC-1093.txt");
        data.ResultInitialize("D:\\Trying_Tools\\TopicContentEvaluator\\_TOPIC-1093.txt");
    }
}
