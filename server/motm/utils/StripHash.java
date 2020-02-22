/* SOURCED FROM:
 *   https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
 */

package server.motm.utils;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;



public class StripHash
{
	public static void main(String[] args) throws NoSuchAlgorithmException
	{
		System.out.println(stripHash(args[0]));
	}
	public static String stripHash(String storedPassword) throws NoSuchAlgorithmException
	{
		String[] parts = storedPassword.split(":");
		return parts[0] + ":" + parts[1] + ":" + (fromHex(parts[2]).length * 8);
	}
	
	
	private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
	{
		byte[] bytes = new byte[hex.length() / 2];
		for(int i = 0; i<bytes.length ;i++){
			bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}
}




