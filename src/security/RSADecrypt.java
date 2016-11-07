package security;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSADecrypt {
	private static final String KEY_GENERATOR_ALGORITHM_NAME = "RSA";
	private static final String CIPHER_INSTANCE_ALGORITHM_NAME = "RSA/ECB/NoPadding";
	
	public static byte[] getRSADecode(byte[] src, RSAPublicKey rsaPublicKey) {
		byte[] result = null;
		try {
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
			KeyFactory keyFactoryDecode = KeyFactory.getInstance(KEY_GENERATOR_ALGORITHM_NAME);
			PublicKey publicKey = keyFactoryDecode.generatePublic(x509EncodedKeySpec);
			Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE_ALGORITHM_NAME);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			result = cipher.doFinal(src);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
