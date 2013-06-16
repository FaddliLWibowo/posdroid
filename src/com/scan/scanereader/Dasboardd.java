package com.scan.scanereader;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.sax.TextElementListener;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.EditText;

public class Dasboardd extends Activity{

	 
	@SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //ambil data ke Spiner combobox
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_outlet);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, outlet);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        //Tobol Scan
        final Button startButton =   (Button) findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
         	public void onClick(View v) {
         		Intent i = new Intent(Dasboardd.this, QRScanner.class);
                startActivity(i);
         	}
        }); 
        
        //retrive hasil Scan
        final EditText tScanResult = (EditText)findViewById(R.id.inputimei);
        Intent intent= getIntent();
    	final String Hasilscanan = intent.getStringExtra("HasilScan");
    	tScanResult.setText(Hasilscanan);
    	
    	
    		
        
    }
	
	/*
	public void hasilScan(){
		final EditText tScanResult = (EditText)findViewById(R.id.inputimei);
        Intent intent= getIntent();
    	final String Hasilscanan = intent.getStringExtra("HasilScan");
    	tScanResult.setText(Hasilscanan);
	}
	*/
	
	
    
    static final String[] outlet = new String[] {"outlet1","outlet2","outlet3"};
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
       
 
}