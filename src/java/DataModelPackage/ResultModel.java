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
public class ResultModel {
    private ArrayList<Byte> result;
    private String topic;
    private ArrayList<String> filename;
    private ArrayList<String> urls;
    private ArrayList<String> html_content;
    

    public ResultModel(String topic) {
        this.result = new ArrayList<Byte>(10);
        this.topic = topic;
        this.filename = new ArrayList<String>(10);;
        this.urls = new ArrayList<String>(10);;
        this.html_content = new ArrayList<String>(10);
    }
    
    private String encodeURL(String URL) {
        // will encode URL here
        return URL;
    }
    
    public boolean addURL(byte eval, String URL, String content)
    {
        result.add(eval);
        urls.add(URL);
        filename.add(encodeURL(URL));
        html_content.add(content);
        return true;  // No exception with adding
    }
    
    public void saveToFile(String path) {
        //save file into this path
    }
}
