package com.scan.scanereader;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
 
//import android.content.Context;

public class SelloutFunction {
	
	private JSONParser jsonParser;
	private static String cannelURL = "http://10.0.2.2/droidapi/channel";
	private static String channel_tag = "getchannel";
	private static String areaURL = "http://10.0.2.2/droidapi/area";
	
	
	//cunstruktor
	public SelloutFunction(){
		jsonParser = new JSONParser();
	}
	
	/**
     * function make Chanel Request
     * @param channel_tag
     * */
     public JSONObject Channel(){
    	// Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", channel_tag));
        
        JSONObject json = jsonParser.makeHttpRequest(cannelURL,"POST", params);
        return json;
     }
     
     /**
      * function make Area Request
      * */
      public JSONObject Area(String id_chanel){
     	// Building Parameters
         List<NameValuePair> params = new ArrayList<NameValuePair>();
         params.add(new BasicNameValuePair("id_chanel", id_chanel));
         
         JSONObject json = jsonParser.makeHttpRequest(areaURL,"POST", params);
         return json;
      }
	

}
