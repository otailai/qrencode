package security;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

public class RSAInitial {
	private static final String RSA_PUBLIC_KEY_NAME = "RSAPublicKey";
	private static final String RSA_PRIVATE_KEY_NAME = "RSAPrivateKey";
	private static final String KEY_GENERATOR_ALGORITHM_NAME = "RSA";
	private static final int KEY_GENERATOR_SIZE = 4096;
	
	public static Map<String, Key> generateKeys() {
		Map<String, Key> keyMap = new HashMap<>();
		try {
			
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_GENERATOR_ALGORITHM_NAME);
			keyPairGenerator.initialize(KEY_GENERATOR_SIZE);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
			keyMap.put(RSA_PUBLIC_KEY_NAME, rsaPublicKey);
			keyMap.put(RSA_PRIVATE_KEY_NAME, rsaPrivateKey);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return keyMap;
	}
}
