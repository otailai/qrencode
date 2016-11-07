package security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

public class DH3DESInitial {
	private static final String PRIVATE_KEY_ALGORITHM_PARAMETER = "DESede";
	private static final String CRYPT_ALGORITHM_PARAMETER = "DH";
	private static final int KEY_PAIR_INITIAL_PARAMETER = 512;
	private static final String RECEIVER_DES_KEY = "ReceiverDesKey";
	private static final String SENDER_DES_KEY = "SenderDesKey";
	
	
	public static Map<String, SecretKey> doInit() {
		Map<String, SecretKey> rsSecretKey = new HashMap<>();
		try {
			//1.初始化发送方密钥
			KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance(CRYPT_ALGORITHM_PARAMETER);
			senderKeyPairGenerator.initialize(KEY_PAIR_INITIAL_PARAMETER);
			KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();
			byte[] senderPublicKeyEncode = senderKeyPair.getPublic().getEncoded();//发送方公钥，发送给接收方
			
			//2.初始化接收方密钥
			KeyFactory receiverKeyFactory = KeyFactory.getInstance(CRYPT_ALGORITHM_PARAMETER);
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEncode);
			PublicKey receiverPublicKey = receiverKeyFactory.generatePublic(x509EncodedKeySpec);
			DHParameterSpec dhParameterSpec = ((DHPublicKey)receiverPublicKey).getParams();
			KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance(CRYPT_ALGORITHM_PARAMETER);
			receiverKeyPairGenerator.initialize(dhParameterSpec);
			KeyPair receiverKeyPair = receiverKeyPairGenerator.generateKeyPair();
			PrivateKey receiverPrivateKey = receiverKeyPair.getPrivate();
			byte[] receiverPublicKeyEncode = receiverKeyPair.getPublic().getEncoded();
			
			//3.密钥构建
			//接收方的公钥
			KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance(CRYPT_ALGORITHM_PARAMETER);
			receiverKeyAgreement.init(receiverPrivateKey);
			receiverKeyAgreement.doPhase(receiverPublicKey, true);
			//接收方的私钥
			SecretKey receiverDesKey = receiverKeyAgreement.generateSecret(PRIVATE_KEY_ALGORITHM_PARAMETER);
			//发送方的公钥
			KeyFactory senderKeyFactory = KeyFactory.getInstance(CRYPT_ALGORITHM_PARAMETER);
			x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyEncode);
			PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
			KeyAgreement senderKeyAgreement = KeyAgreement.getInstance(CRYPT_ALGORITHM_PARAMETER);
			senderKeyAgreement.init(senderKeyPair.getPrivate());
			senderKeyAgreement.doPhase(senderPublicKey, true);
			//发送方的私钥
			SecretKey senderDesKey = senderKeyAgreement.generateSecret(PRIVATE_KEY_ALGORITHM_PARAMETER);
			rsSecretKey.put(RECEIVER_DES_KEY, receiverDesKey);
			rsSecretKey.put(SENDER_DES_KEY, senderDesKey);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rsSecretKey;
	}
}
