package com.scan.scanereader;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;

import com.scan.scanereader.UserFunctions;


public class MainActivity extends Activity{	
	
	//flag for Internet connection status
    Boolean isInternetPresent = false;
    //Connection detector class
    ConnectionDetector cd;
    //progres dialog
    private ProgressDialog pDialog;
    //Alert dialog
    private AlertDialog.Builder ad;
 	private static final String TAG_SUCCESS = "StatusID";
 	EditText txtUser; 
    EditText txtPass; 
    Button btnLogin; 
    
	@SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.form_login);
        
       //permission StrickMode
        if(android.os.Build.VERSION.SDK_INT > 9){
        	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        }
        
        //alert dialog
        ad = new AlertDialog.Builder(this);
        //creating connection detector class instance
        cd = new ConnectionDetector(getApplicationContext());
        txtUser = (EditText)findViewById(R.id.reg_username);
        txtPass = (EditText)findViewById(R.id.reg_password);
        btnLogin = (Button)findViewById(R.id.btnLogin); //button login 
        
        //perform action onclick
        btnLogin.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		//get Internet status
                isInternetPresent = cd.isConnectingToInternet();
                // check for Internet status
                if (isInternetPresent) 
    				new DoLogin().execute();
                else 
                	Toast.makeText(MainActivity.this, "Koneksi Internet bermasalah", Toast.LENGTH_SHORT).show();    
        	}
        }); 
        
    }
	
	
	private class DoLogin extends AsyncTask<String, String, String> {
		public Boolean flag;
		
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Login..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
    		
        	UserFunctions userFunction = new UserFunctions();
      		JSONObject json = userFunction.loginUser(txtUser.getText().toString(), txtPass.getText().toString());
      	    
      		//check log cat fro response
      		Log.d("Create Response", json.toString());
      		
      		//check for success tag
      		try {
    			int success = json.getInt(TAG_SUCCESS);
   					if (success == 1) {
   						flag = true;
      				} else {
      					flag = false;
      				}
      		} catch (JSONException e) {
      			 e.printStackTrace();
      		}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
			
			if(flag){
				Toast.makeText(MainActivity.this, "login", Toast.LENGTH_SHORT).show();
    			Intent newActivity = new Intent(MainActivity.this,Dasboardd.class);
    			startActivity(newActivity);
			 }else{
		    	ad.setTitle("Error");
				ad.setIcon(android.R.drawable.btn_star_big_on);
				ad.setPositiveButton("Close", null);
				ad.setMessage("Incorrect Username and Password");
				ad.show();
				txtUser.setText("");
				txtPass.setText("");
			 }
		}
	
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
       
 
}