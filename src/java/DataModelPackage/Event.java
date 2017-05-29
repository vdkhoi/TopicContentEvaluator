/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModelPackage;

import gnu.trove.map.hash.TIntObjectHashMap;
import java.util.ArrayList;

/**
 *
 * @author Khoi
 */
public class Event {
    private int event_id;
    private String time;
    private String eventDescription;
    private ArrayList<String> domains;
    private TIntObjectHashMap<ArrayList<DocumentContent>> results;

    
    public Event () {
        domains = new ArrayList<String>();
        results = new TIntObjectHashMap<ArrayList<DocumentContent>>();
    }
    
    /**
     * @return the event_id
     */
    public int getEvent_id() {
        return event_id;
    }
    
    public void setLabel(String url, String title, String labelVal) {
        ArrayList<DocumentContent>[] docs = new ArrayList[results.size()]; 
        for (int i = 0; i < docs.length; i ++) {
            docs[i] = new ArrayList<DocumentContent>();
        }
        results.values(docs);
        for (int i = 0; i < docs.length; i ++) {
            for (int j = 0; j < docs[i].size(); j ++) {
                DocumentContent doc = docs[i].get(j);
                if (doc.getUrl().compareTo(url) == 0 && doc.getTitle().compareTo(title) == 0){
                    doc.setLabel(labelVal);
                    return;
                }
            }
        }
    }
    
    public ArrayList<DocumentContent> getDomainDoc(int domainIdx) {
        return results.get(domainIdx);
    }

    /**
     * @param event_id the event_id to set
     */
    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    /**
     * @return the domains
     */
    public ArrayList<String> getDomains() {
        return domains;
    }

    /**
     * @param domains the domains to set
     */
    public void setDomains(ArrayList<String> domains) {
        this.domains = domains;
    }

    /**
     * @return the results
     */
    public TIntObjectHashMap<ArrayList<DocumentContent>> getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(TIntObjectHashMap<ArrayList<DocumentContent>> results) {
        this.results = results;
    }
    
    public void AddResult(int domain, DocumentContent doc) {
        ArrayList<DocumentContent> temp = results.get(domain);
        if (temp == null) 
            temp = new ArrayList<DocumentContent>();
        temp.add(doc);
        results.put(domain, temp);
    }
    
    public void AddDomain(String domain) {
        domains.add(domain);
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the eventDescription
     */
    public String getEventDescription() {
        return eventDescription;
    }

    /**
     * @param eventDescription the eventDescription to set
     */
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
