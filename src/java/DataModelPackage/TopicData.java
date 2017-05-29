/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModelPackage;

import java.util.ArrayList;

/**
 *
 * @author Khoi
 */
public class TopicData {

    private String topic_id;
    private String description;
    private String category;
    private ArrayList<Event> events;

    public TopicData() {
        this.events = new ArrayList<Event>();
    }

    /**
     * @return the topic
     */
    public String getTopic() {
        return description;
    }

    /**
     * @param topic the topic to set
     */
    public void setTopic(String topic) {
        this.description = topic;
    }

    /**
     * @return the events
     */
    public Event getEvent(int idx) {
        Event temp = events.get(idx);
        return temp;
    }

    /**
     * @param events the events to set
     */
    public void addEvent(Event event) {

        events.add(event);

    }
    
    public int getNumOfEvent() {

        return events.size();

    }

    public void addEvents(Event[] events) {
        for (int i = 0; i < events.length; i++) {
            this.events.add(events[i]);
        }
    }

    /**
     * @return the topic_id
     */
    public String getTopic_id() {
        return topic_id;
    }

    /**
     * @param topic_id the topic_id to set
     */
    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }
}
