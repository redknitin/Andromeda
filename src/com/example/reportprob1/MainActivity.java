package com.example.reportprob1;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import retrofit.RestAdapter;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void btnPic_click(View v) {
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
        startActivityForResult(cameraIntent, 1818);
	}
	
	Bitmap photo = null;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	TextView tvu3 = (TextView) findViewById(R.id.textView3);
		if (requestCode == 1818 && resultCode == RESULT_OK) {  
            //Bitmap photo = (Bitmap) data.getExtras().get("data");
			photo = (Bitmap) data.getExtras().get("data");
            if (photo != null) {
            	tvu3.setText("Got pic");
            	ImageView imgvu = (ImageView) findViewById(R.id.imageView1);
            	imgvu.setImageBitmap(photo);
            	return;
            }
        }  		
    	tvu3.setText("NO pic");
	}
	
	public void btnSubmit_click(View v) {
		String strProblemDesc;
		String strLocation;
		Bitmap binPicture; //This is a member variable
		
		TextView txtProblemDesc = (TextView) findViewById(R.id.editText1);
		TextView txtLocation = (TextView) findViewById(R.id.editText2);
		
		strProblemDesc = txtProblemDesc.getText().toString();
		strLocation = txtLocation.getText().toString();
		binPicture = photo;
		
    	Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).registerTypeAdapter(Date.class, new DateTypeAdapter()).create();
		RestAdapter ra = new RestAdapter.Builder().setEndpoint("http://10.0.2.2:800").setConverter(new LenientGsonConverter(gson)).build();
    	WoService wos = ra.create(WoService.class);
    	
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	binPicture.compress(Bitmap.CompressFormat.PNG, 0, bos);
    	byte[] bitmapData = bos.toByteArray();    	
    	
		TextView tvexc = (TextView) findViewById(R.id.textView3);
		
    	try {
    	String resp = wos.postWO(strProblemDesc, strLocation, 
    			new TypedByteArray("application/octet-stream", bitmapData) {
    		@Override
    		public String fileName() {
    		return "fileimage.png";
    		}    		
    	}
    	);
    	tvexc.setText(resp);
    	}
    	catch (Exception ex) {
    		tvexc.setText(ex.getMessage());
    	}
//    			binPicture);
		
	}
}
