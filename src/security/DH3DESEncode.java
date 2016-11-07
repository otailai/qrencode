package security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class DH3DESEncode {
	public static byte[] Encrypt(String src, SecretKey senderDesKey, String instanceName) {
		byte[] result = null;
		try {
			Cipher cipher = Cipher.getInstance(instanceName);
			cipher.init(Cipher.ENCRYPT_MODE, senderDesKey);
			result = cipher.doFinal(src.getBytes());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
}
