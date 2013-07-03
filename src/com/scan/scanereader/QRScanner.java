package com.scan.scanereader;
 
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.os.AsyncTask;
import android.app.ProgressDialog;
 
import com.example.BarcodeTest.IntentIntegrator;
import com.example.BarcodeTest.IntentResult;
import com.scan.scanereader.SelloutFunction;
 
public class QRScanner extends Activity {
	
    /** Called when the activity is first created. */
    private String upc;
    
    private static String PARAM_IMEI ="";
    private static final String TAG_MODEL = "model";
    private static final String TAG_HARGA = "harga";
    private ProgressDialog pDialog;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /**
    	 * Jendela Tanta title
    	 * */
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        /**
    	 * memulai pemindaian QRCode
    	 * */
        IntentIntegrator.initiateScan(this);
    }
    
    
    // cek hasil dari QRCode
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case IntentIntegrator.REQUEST_CODE: {
                if (resultCode != RESULT_CANCELED) {
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                    
                    /**
                	 * apabila ada hasil dari pemindaian
                	 * */
                    if (scanResult != null) {
                        
                    	/**
                    	 * ambil isi dari QRCode
                    	 * */
                        upc = scanResult.getContents();
                         
                        /**
                    	 * tampilkan pada Alert Dialog
                    	 * */
                        final AlertDialog.Builder builder=new AlertDialog.Builder(QRScanner.this);
                        builder.setTitle("Hasil Scan");
                        builder.setMessage(upc+"\n");
                        builder.setIcon(android.R.drawable.ic_dialog_alert);
                        
                        /**
                    	 * Tombol untuk simpan data
                    	 * */
                        builder.setPositiveButton("OK", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    
                            		/**
                            		 * Kirim Hasil Scane
                            		 * */
                            	    PARAM_IMEI = upc;
                            	    new GetProductInfo().execute();
                            }
                        });
                        
                        /**
                		 * Tombol Coba Lagi
                		 * */
                        builder.setNegativeButton("Coba Lagi", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                QRScanner.this.finish();
                                IntentIntegrator.initiateScan(QRScanner.this);
                            }
                        });
                        
                        /**
                		 * Tampilkan AlertBox
                		 * */
                        builder.show();
                    }
            break;
                }
            }
        }
 
    }
    
    /**
	 * Class Get data Outlet
	 * */
	private class GetProductInfo extends AsyncTask<String, String, String> {
		
		public String model_product="";
		public String harga_product="";
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(QRScanner.this);
			pDialog.setMessage("retrive outlet Data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		* Retrive Data Outlet
		* */
		protected String doInBackground(String... args) {
        	SelloutFunction Jproduct = new SelloutFunction();
      		JSONObject json_product = Jproduct.Product(PARAM_IMEI);
      		Log.d("Create Response", json_product.toString());
      		
      		try {
      			model_product = json_product.getString(TAG_MODEL);
      			harga_product = json_product.getString(TAG_HARGA);
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
					//Toast.makeText(QRScanner.this, "selesai", Toast.LENGTH_SHORT).show();
        			Intent newActivity = new Intent(QRScanner.this,Dasboardd.class);
        			newActivity.putExtra("HasilScan", upc);
        			newActivity.putExtra("model", model_product);
        			newActivity.putExtra("harga", harga_product);
        			startActivity(newActivity);
				}
			});
			
		}
		
	}
	
    
}