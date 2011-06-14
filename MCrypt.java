package br.com.monty.android.crypt;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Hashtable;

import javax.crypto.Cipher;

import org.kobjects.base64.Base64;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

public final class MCrypt {
	
	public static final String encryptRSABlock(RSAPublicKey publicKey, String clearText) {
		int maxSize = 80;

        StringBuilder cypherText = new StringBuilder();
        String aux = "";
        while (clearText != null && !clearText.equalsIgnoreCase(""))
        {
        	aux = clearText.length() > maxSize ? clearText.substring(0, maxSize) : clearText;
        	cypherText.append(String.format("%s\n", Base64.encode(MCrypt.encryptRSA(publicKey, aux)).replace("\n", "").replace("\r", "").trim()));
            clearText = clearText.length() > maxSize ? clearText.substring(maxSize) : "";
        }

        return cypherText.toString().trim();
	}

	private static final byte[] encryptRSA(RSAPublicKey publicKey, String clearText) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");

			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
						
			return cipher.doFinal(clearText.getBytes("UTF-8"));
		} catch (Exception e) {
			Log.e("MMCrypt-encryptRSA", e.toString());
		}
		
	    return null;
	}
	
	public static final String decryptRSABlock(RSAPrivateKey privateKey, String cypherText) {
		StringBuilder clearText = new StringBuilder();
		
		String[] blocks = cypherText.split("\n");
		String aux = "";
        for (int i = 0; i < blocks.length; i++) {
        	aux = blocks[i];
        	if (aux != null && !aux.equalsIgnoreCase("")) {
        		clearText.append(String.format("%s", MCrypt.decryptRSA(privateKey, Base64.decode(aux.trim())))); 
        	}
        }
        
        return clearText.toString();
	}
	
	private static final String decryptRSA(RSAPrivateKey privateKey, byte[] cypherText) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");

			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			return new String(cipher.doFinal(cypherText));
		} catch (Exception e) {
			Log.e("MMCrypt-decryptRSA", e.toString());
		}
		
	    return null;
	}

	public static final Hashtable<String, String> generateRSAKeys() {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
			generator.initialize(1024);
			
			KeyPair pair = generator.generateKeyPair();
			
			RSAPrivateKey privateKey = (RSAPrivateKey)pair.getPrivate();
			String privateNet = String.format("<RSAKeyValue><Modulus>%s</Modulus><Exponent>%s</Exponent></RSAKeyValue>", Base64.encode(privateKey.getModulus().toByteArray()), Base64.encode(privateKey.getPrivateExponent().toByteArray()));
						
			RSAPublicKey publicKey = (RSAPublicKey)pair.getPublic();
			String publicNet = String.format("<RSAKeyValue><Modulus>%s</Modulus><Exponent>%s</Exponent></RSAKeyValue>", Base64.encode(publicKey.getModulus().toByteArray()), Base64.encode(publicKey.getPublicExponent().toByteArray()));
			
			Hashtable<String, String> keys = new Hashtable<String, String>();
			keys.put("privateKey", privateNet);
			keys.put("publicKey", publicNet);
			
			return keys;
		} catch (Exception e) {
			Log.e("MMCrypt-generateRSAKeys", e.toString());
			e.printStackTrace();
			return null;
		}
	}

	public static final RSAPublicKey getPublicKey(Context context, String publicKey)
	{
		RSAKeyHandler content = new RSAKeyHandler();
		
		try {
			
			Xml.parse(publicKey, content);
			
			RSAPublicKeySpec key = new RSAPublicKeySpec(content.getModulus(), content.getExponent());

			return (RSAPublicKey)KeyFactory.getInstance("RSA/None/PKCS1Padding", "BC").generatePublic(key);
		} catch (Exception e) {
			Log.e("MMCrypt-getPublicKey", e.toString());
		}
		
		return null;
	}

	public static final RSAPrivateKey getPrivateKey(Context context, String privateKey)
	{
		RSAKeyHandler content = new RSAKeyHandler();
		
		try {
			Xml.parse(privateKey, content);
			
			RSAPrivateKeySpec key = new RSAPrivateKeySpec(content.getModulus(), content.getExponent());

			return (RSAPrivateKey)KeyFactory.getInstance("RSA/None/PKCS1Padding", "BC").generatePrivate(key);
		} catch (Exception e) {
			Log.e("MMCrypt-getPrivateKey", e.toString());
		}
		
		return null;
	}
	
	public static byte[] stripLeadingZeros(byte[] a) {
		int lastZero = -1;
		
		for (int i = 0; i < a.length; i++) {
			if (a[i] == 0) {
				lastZero = i;
			} else {
				break;
			}
		}
		
		lastZero++;
		byte[] result = new byte[a.length-lastZero];

		System.arraycopy(a, lastZero, result, 0, result.length);

		return result;
	}
}