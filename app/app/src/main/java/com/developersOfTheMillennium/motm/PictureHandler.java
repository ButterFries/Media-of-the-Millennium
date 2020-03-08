package com.developersOfTheMillennium.motm;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class PictureHandler {
	//HAVE NOT TESTED, UNDER DEVELOPMENT
	public byte[] getByteArrayImage(String url){
		try {

			URL imageUrl = new URL(url);
			URLConnection ucon = imageUrl.openConnection();

			InputStream is = ucon.getInputStream();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int read = 0;

			while ((read = is.read(buffer, 0, buffer.length)) != -1) {
				baos.write(buffer, 0, read);
			}

			baos.flush();

			return  baos.toByteArray();

		} catch (Exception e) {
			Log.d("ImageManager", "Error: " + e.toString());
		}

		return null;
	}
}