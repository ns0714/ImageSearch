package com.codepath.gridimagesearch.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.helper.TouchImageView;
import com.codepath.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends Activity {

	private TouchImageView ivImageResult;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		
		//DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		//int screenWidth = metrics.widthPixels;
		//int screenHeight = metrics.heightPixels;
		
		ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
		//int imageResizeWidth = screenWidth - 100;
		//int imageResizeHeight = (imageResizeWidth * result.getHeight()) / result.getWidth();

		ivImageResult = (TouchImageView)findViewById(R.id.ivImageResult);
		Picasso.with(this).load(result.getFullUrl()).resize(600, 600).into(ivImageResult, new Callback() {
			
			@Override
			public void onSuccess() {
				//onShareItem();				
			}
			
			@Override
			public void onError() {
				//handle image load error
				Toast.makeText(ImageDisplayActivity.this, 
						"Error", Toast.LENGTH_LONG).show();
			}
		});
		getActionBar().setTitle(Html.fromHtml(result.getTitle()));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.share_image, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.share:
			//Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
			onShareItem();
			return true;
		}
		return false;
	}
	
	public void onShareItem() {
	    // Get access to bitmap image from view
	    ImageView ivImage = (ImageView) findViewById(R.id.ivImageResult);
	    // Get access to the URI for the bitmap
	    Uri bmpUri = getLocalBitmapUri(ivImage);
	    if (bmpUri != null) {
	        // Construct a ShareIntent with link to image
	        Intent shareIntent = new Intent();
	        shareIntent.setAction(Intent.ACTION_SEND);
	        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
	        shareIntent.setType("image/*");
	        // Launch sharing dialog for image
	        startActivity(Intent.createChooser(shareIntent, "Share Image"));	
	    } else {
	    	Toast.makeText(getApplicationContext(), "Sharing failed", Toast.LENGTH_SHORT).show();
	    }
	}

	// Returns the URI path to the Bitmap displayed in specified ImageView
	public Uri getLocalBitmapUri(ImageView imageView) {
	    // Extract Bitmap from ImageView drawable
	    Drawable drawable = imageView.getDrawable();
	    Bitmap bmp = null;
	    if (drawable instanceof BitmapDrawable){
	       bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
	    } else {
	       return null;
	    }
	    // Store image to default external storage directory
	    Uri bmpUri = null;
	    try {
	        File file =  new File(Environment.getExternalStoragePublicDirectory(  
		        Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
	        file.getParentFile().mkdirs();
	        FileOutputStream out = new FileOutputStream(file);
	        bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
	        out.close();
	        bmpUri = Uri.fromFile(file);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return bmpUri;
	}
}
