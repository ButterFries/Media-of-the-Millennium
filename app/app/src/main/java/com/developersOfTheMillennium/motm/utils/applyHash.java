/* SOURCED FROM:
 *   https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
 */

package server.motm.utils;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;



public class applyHash
{
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException 
	{
		System.out.println(hashPassword(args[0], args[1]));
	}
	public static String hashPassword(String password, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		String[] parts = storedPassword.split(":");
		int iterations = Integer.parseInt(parts[0]);
		byte[] salt = fromHex(parts[1]);
		int hash_length = Integer.parseInt(parts[2]);
		
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, hash_length);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");		
		byte[] result_hash = skf.generateSecret(spec).getEncoded();		
		return iterations + ":" + toHex(salt) + ":" + toHex(result_hash);
	}
	
	
	private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
	{
		byte[] bytes = new byte[hex.length() / 2];
		for(int i = 0; i<bytes.length ;i++){
			bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}
	
	private static String toHex(byte[] array) throws NoSuchAlgorithmException
	{
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if(paddingLength > 0)
			return String.format("%0"  +paddingLength + "d", 0) + hex;
		else
			return hex;
		
	}
}




