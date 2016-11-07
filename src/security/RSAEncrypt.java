package security;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

public class RSAEncrypt {
	private static final String KEY_GENERATOR_ALGORITHM_NAME = "RSA";
	private static final String CIPHER_INSTANCE_ALGORITHM_NAME = "RSA/ECB/NoPadding";
	
	public static byte[] getRSAEncode(String src, RSAPrivateKey rsaPrivateKey) {
		byte[] result = null;
		try {
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
			KeyFactory keyFactoryEncode = KeyFactory.getInstance(KEY_GENERATOR_ALGORITHM_NAME);
			PrivateKey privateKey = keyFactoryEncode.generatePrivate(pkcs8EncodedKeySpec);
			Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE_ALGORITHM_NAME);
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			result = cipher.doFinal(src.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}


