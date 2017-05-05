package com.davidwang.security;
import java.security.*;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

public class SHACoder {
	
	public static byte[] encodeSHA256(byte[] data) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		MessageDigest md=MessageDigest.getInstance("SHA-256");
		return md.digest(data);
	}
	
	public static String encodeSHA256Hex(byte[] data) throws Exception {
		byte[] b=encodeSHA256(data);
		return new String(Hex.encode(b));
	}
	
	public static byte[] encodeSHA224(byte[] data) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		MessageDigest md=MessageDigest.getInstance("SHA-224");
		return md.digest(data);
	}
	
	public static String encodeSHA224Hex(byte[] data) throws Exception {
		byte[] b=encodeSHA224(data);
		return new String(Hex.encode(b));
	}

}
