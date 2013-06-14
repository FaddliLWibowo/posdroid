package com.scan.scanereader;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;
 
import com.example.BarcodeTest.IntentIntegrator;
import com.example.BarcodeTest.IntentResult;
 
public class QRScanner extends Activity {
	
    /** Called when the activity is first created. */
    private String upc;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //jendela tanpa title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //memulai pemindaian QRCode
        IntentIntegrator.initiateScan(this);
    }
    // cek hasil dari QRCode
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case IntentIntegrator.REQUEST_CODE: {
                if (resultCode != RESULT_CANCELED) {
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                    
                    // apabila ada hasil dari pemindaian
                    if (scanResult != null) {
                        
                    	// ambil isi dari QRCode
                        upc = scanResult.getContents();
                        
                        //  tampilkan pada Alert Dialog
                        final AlertDialog.Builder builder=new AlertDialog.Builder(QRScanner.this);
                        builder.setTitle("Hasil Scan");
                        builder.setMessage(upc+"\n");
                        builder.setIcon(android.R.drawable.ic_dialog_alert);
                        
                        // Tombol untuk simpan data
                        builder.setPositiveButton("OK", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    
                            		//kirim Hasil Scan
                                    Toast.makeText(QRScanner.this, "selesai", Toast.LENGTH_SHORT).show();
                        			Intent newActivity = new Intent(QRScanner.this,Dasboardd.class);
                        			newActivity.putExtra("HasilScan", upc);
                        			startActivity(newActivity);
 
                            }
                        });
                        
                        // Tombol untuk coba lagi/keluar
                        builder.setNegativeButton("Coba Lagi", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                QRScanner.this.finish();
                                IntentIntegrator.initiateScan(QRScanner.this);
                            }
 
                        });
                        
                        // tampilkan alert box
                        builder.show();
                    }
            break;
                }
            }
        }
 
    }
}