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
	//private static String cannelURL = "http://santrinulis.com/droidserver/login";
	private static String channel_tag = "getchannel";
	
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
        
        JSONObject json1 = jsonParser.makeHttpRequest(cannelURL,"POST", params);
        return json1;
     }
	

}
