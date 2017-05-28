package com.bizprout.web.app.resource;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESencrp {

	private static final String ALGO = "AES";

	private static final byte[] keyValue = 
			new byte[] { 'U', 's', 
		'e', 'r', 'r', 'e', 
		's', 'o',
		'u', 'r', 'c',
		'e', 'c', 'l', 'a', 's'};
	
	static Logger logger=LoggerFactory.getLogger("Aes Encrypt and Decrypt class");

	public static String encrypt(String Data) throws Exception {
		String encryptedValue = null;
		try {
			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);
			byte[] encVal = c.doFinal(Data.getBytes());
			encryptedValue = new BASE64Encoder().encode(encVal);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+"Encrypt class");
		}
		return encryptedValue;
	}
	
	  public static String decrypt(String encryptedData) throws Exception {
	        String decryptedValue = null;
			try {
				Key key = generateKey();
				Cipher c = Cipher.getInstance(ALGO);
				c.init(Cipher.DECRYPT_MODE, key);
				byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
				byte[] decValue = c.doFinal(decordedValue);
				decryptedValue = new String(decValue);
			} catch (Exception e) {
				logger.error(e.getMessage()+"..."+"Decrypt class");
			}
	        return decryptedValue;
	    }

	private static Key generateKey() throws Exception {
		Key key = null;
		try {
			key = new SecretKeySpec(keyValue, ALGO);
		} catch (Exception e) {
			logger.error(e.getMessage()+"..."+"Generate Key class");
		}
		return key;
	}

}
