package com.scan.scanereader;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
 
import android.content.Context;

public class UserFunctions {
	
	private JSONParser jsonParser;
	private static String loginURL = "http://10.0.2.2/droidapi/login";
	//private static String loginURL = "http://santrinulis.com/droidserver/login";
	
	//private static String login_tag = "login";
    //private static String register_tag = "register";
	
	//cunstruktor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	
	/**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject loginUser(String email, String password){
    	// Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("strUser", email));
        params.add(new BasicNameValuePair("strPass", password));
        JSONObject json = jsonParser.makeHttpRequest(loginURL,"POST", params);

        return json;
    }
	
    
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
     
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
    
    

}
