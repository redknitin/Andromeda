package com.example.reportprob1;

import retrofit.http.Field;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedFile;
import android.graphics.Bitmap;

public interface WoService {
	@POST("/gleeful.php")
	@Multipart
	String postWO(
			@Part("probDesc")
			String probDesc, 
			@Part("loc")
			String loc, 
			@Part("bmp")
//			Bitmap bmp
			TypedByteArray bmp
			);
}
