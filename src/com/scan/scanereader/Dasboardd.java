package com.scan.scanereader;


//import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.StrictMode;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.scan.scanereader.SelloutFunction;

public class Dasboardd extends Activity{

    /**
	 * Connection detector class
	 * */
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    
    private ProgressDialog pDialog;
    Spinner spinner_channel,spinner_area,spinner_outlet;
    private static  String  PARAM_IDCHANNEL = "";
    private static String PARAM_IDAREA ="";
    EditText txtimei,txtinputphone;
    TextView lblmodel,lblharga;
    
    
    
	@SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.selloutform);
        
        /**
    	 * permission StrickMode
    	 * */
        if(android.os.Build.VERSION.SDK_INT > 9){
        	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        }
      
        spinner_channel = (Spinner) findViewById(R.id.spinner_channel);
        spinner_area = (Spinner) findViewById(R.id.spinner_Area);
        spinner_outlet = (Spinner) findViewById(R.id.spinner_outlet);
        txtimei = (EditText)findViewById(R.id.inputimei);
        txtinputphone = (EditText)findViewById(R.id.inputphone);
        lblmodel = (TextView)findViewById(R.id.modelvalue);
        lblharga = (TextView)findViewById(R.id.hargavalue);
        txtimei.setEnabled(false);
        
        tombolscan();
        hasilScan();
        
        /**
		 * creating connection detector class instance
		 * */
        cd = new ConnectionDetector(getApplicationContext());
        
        /**
		 * get Internet status
		 * */
        isInternetPresent = cd.isConnectingToInternet();
        
        /**
		 * check for Internet status
		 * */
        if (isInternetPresent){ 
        	/**
    		 * Data Channel
    		 * */
        	new GetChannel().execute();
        	
        	/**
    		 * Data piner_channel di pilih
    		 * */
			addListenerOnSpinnerItemSelection_channel();
			
			/**
    		 * Data piner_Area di pilih
    		 * */
			addListenerOnSpinnerItemSelection_area();
        }else{ 
        	Toast.makeText(Dasboardd.this, "Koneksi Internet bermasalah", Toast.LENGTH_SHORT).show();
        }
         
    }
	
	
	/**
	 * ketika spiner_channel di pilih
	 * */
	public void addListenerOnSpinnerItemSelection_channel() {
		spinner_channel.setOnItemSelectedListener(new OnItemSelectedListener() {
        	@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
        		/**
        		 *  On selecting a spinner item
        		 * */
		        String label = parent.getItemAtPosition(position).toString();
		        
		        if(label != "--pilih--"){
		        	PARAM_IDCHANNEL = label;
		        	new GetArea().execute();
		        }
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
	}
	
	
	/**
	 * ketika spiner_area di pilih
	 * */
	public void addListenerOnSpinnerItemSelection_area() {
		spinner_area.setOnItemSelectedListener(new OnItemSelectedListener() {
        	@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
				// On selecting a spinner item
		        String label = parent.getItemAtPosition(position).toString();
		        
		        if(label != "--pilih--"){
		        	//Showing selected spinner item
		        	//Toast.makeText(parent.getContext(), "You selected: " + label, Toast.LENGTH_LONG).show();
		        	PARAM_IDAREA = label;
		        	new GetOutlet().execute();
		        }
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
	}
	
	
	/**
	 * Tombol Scan barcode
	 * */
	public void tombolscan(){
		final Button startButton =   (Button) findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
         	public void onClick(View v) {
         		Intent i = new Intent(Dasboardd.this, QRScanner.class);
                startActivity(i);
         	}
        }); 
	}
	
	/**
	 * Ambil Hasil Scan barcode
	 * */
	public void hasilScan(){
		//final EditText tScanResult = (EditText)findViewById(R.id.inputimei);
	  	//tScanResult.setText(Hasilscanan);
        Intent intent= getIntent();
    	final String Hasilscanan_imei = intent.getStringExtra("HasilScan");
    	final String Hasilscanan_model = intent.getStringExtra("model");
    	final String Hasilscanan_harga = intent.getStringExtra("harga");
    	txtimei.setText(Hasilscanan_imei);
    	lblmodel.setText(Hasilscanan_model);
    	lblharga.setText(Hasilscanan_harga);
	}
	
	
	/**
	 * Class Get data channel
	 * */
	private class GetChannel extends AsyncTask<String, String, String> {
		ArrayAdapter<String> adapter_channel = new ArrayAdapter<String>(Dasboardd.this, android.R.layout.simple_spinner_item);
		JSONArray channels = null;
		
		private static final String TAG_CHANNEL = "channel";
	    private static final String TAG_NMCHANNEL = "nm_chanel";
	    private static final String TAG_IDCHANNEL = "id_chanel";
	    
		
		/** 
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Dasboardd.this);
			pDialog.setMessage("retrive Channel Data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		* Retrive channel
		* */
		protected String doInBackground(String... args) {
        	SelloutFunction Jchanel = new SelloutFunction();
      		JSONObject json_chanel = Jchanel.Channel();
      		try {
				channels = json_chanel.getJSONArray(TAG_CHANNEL);
				adapter_channel.add("--pilih--");
				for (int i = 0; i < channels.length(); ++i)
				{
					Log.d("RSP",channels.getJSONObject(i).getString(TAG_NMCHANNEL));
					adapter_channel.add(channels.getJSONObject(i).getString(TAG_NMCHANNEL)+ "-" + channels.getJSONObject(i).getString(TAG_IDCHANNEL));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	   /**
		 * After completing background task Dismiss the progress dialog
		* **/
		protected void onPostExecute(String file_url) {
			
			/**
			 * dismiss the dialog once done
			* **/
			pDialog.dismiss(); 
			
			runOnUiThread(new Runnable() {
				public void run() {
					 adapter_channel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				     spinner_channel.setAdapter(adapter_channel);
				}
			});
			
		}
		
	}
	
	
	
	/**
	 * Class Get data Area
	 * */
	private class GetArea extends AsyncTask<String, String, String> {
		ArrayAdapter<String> adapter_area = new ArrayAdapter<String>(Dasboardd.this, android.R.layout.simple_spinner_item);
		JSONArray areas = null;
		
		private static final String TAG_AREA = "area";
	    private static final String TAG_NMAREA = "nm_area";
	    private static final String TAG_IDAREA = "id_area";
		
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Dasboardd.this);
			pDialog.setMessage("retrive Area Data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		* Retrive Data Area
		* */
		protected String doInBackground(String... args) {
        	SelloutFunction JArea = new SelloutFunction();
      		JSONObject json_Area = JArea.Area(PARAM_IDCHANNEL);
      		try {
      			areas = json_Area.getJSONArray(TAG_AREA);
				adapter_area.add("--pilih--");
      			for (int i = 0; i < areas.length(); ++i)
				{
					Log.d("Create Response", areas.getJSONObject(i).getString(TAG_NMAREA));
					adapter_area.add(areas.getJSONObject(i).getString(TAG_NMAREA) + "-" + areas.getJSONObject(i).getString(TAG_IDAREA));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	   /**
		 * After completing background task Dismiss the progress dialog
		* **/
		protected void onPostExecute(String file_url) {
			
			/**
			 * dismiss the dialog once done
			* **/
			pDialog.dismiss();
			
			runOnUiThread(new Runnable() {
				public void run() {
					adapter_area.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinner_area.setAdapter(adapter_area);
				}
			});
			
		}
		
	}
    
	
	/**
	 * Class Get data Outlet
	 * */
	private class GetOutlet extends AsyncTask<String, String, String> {
		ArrayAdapter<String> adapter_outlet = new ArrayAdapter<String>(Dasboardd.this, android.R.layout.simple_spinner_item);
		JSONArray outlets = null;
		
		private static final String TAG_OUTLET = "outlet";
	    private static final String TAG_NMOUTLET = "nm_outlet";
		
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Dasboardd.this);
			pDialog.setMessage("retrive outlet Data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		* Retrive Data Outlet
		* */
		protected String doInBackground(String... args) {
        	SelloutFunction JOutlet = new SelloutFunction();
      		JSONObject json_Outlet = JOutlet.Outlet(PARAM_IDAREA);
      		Log.d("Create Response", json_Outlet.toString());
      		try {
      			outlets = json_Outlet.getJSONArray(TAG_OUTLET);
      			adapter_outlet.add("--pilih--");
      			for (int i = 0; i < outlets.length(); ++i)
				{
					Log.d("Create Response", outlets.getJSONObject(i).getString(TAG_NMOUTLET));
					adapter_outlet.add(outlets.getJSONObject(i).getString(TAG_NMOUTLET));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	   /**
		 * After completing background task Dismiss the progress dialog
		* **/
		protected void onPostExecute(String file_url) {
			
			/**
			 * dismiss the dialog once done
			* **/
			pDialog.dismiss();
			
			runOnUiThread(new Runnable() {
				public void run() {
					adapter_outlet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinner_outlet.setAdapter(adapter_outlet);
				}
			});
			
		}
		
	}
    
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
       
 
}
