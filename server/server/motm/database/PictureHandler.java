package server.motm.database;

import server.motm.utils.*;

public class PictureHandler {
	//HAVE NOT TESTED, UNDER DEVELOPMENT
	private byte[] getPicture(String url){
	     try {
	             URL imageUrl = new URL(url);
	             URLConnection ucon = imageUrl.openConnection();

	             InputStream is = ucon.getInputStream();
	             BufferedInputStream bis = new BufferedInputStream(is);

	             ByteArrayBuffer baf = new ByteArrayBuffer(500);
	             int current = 0;
	             while ((current = bis.read()) != -1) {
	                  baf.append((byte) current);
	             }

	             return baf.toByteArray();
	     } catch (Exception e) {
	          Log.d("ImageManager", "Error: " + e.toString());
	     }
	     return null;
	}
}