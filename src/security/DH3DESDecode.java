package security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class Decode {
	public static byte[] Decrypt(byte[] src, SecretKey receiverDesKey, String instanceName) {
		byte[] result = null;
			try {
				Cipher cipher = Cipher.getInstance(instanceName);
				cipher.init(Cipher.DECRYPT_MODE, receiverDesKey);
				result = cipher.doFinal(src);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		return result;
	}
}


