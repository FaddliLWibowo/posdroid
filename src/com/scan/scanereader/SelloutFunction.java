package com.scan.scanereader;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
 
import android.content.Context;

public class SelloutFunction {
	
	private JSONParser jsonParser;
	//private static String loginURL = "http://10.0.2.2/droidapi/login";
	private static String SaveURL = "http://santrinulis.com/droidserver/login";
	
	//cunstruktor
	public SelloutFunction(){
		jsonParser = new JSONParser();
	}
	
	//public JSONObject SaveSellout(String email, String password){
		//return 
	//}

}
