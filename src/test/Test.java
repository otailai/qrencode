package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import main.Interpreter;
import security.CryptInitial;
import security.Decode;
import security.Encode;

public class Test {

	private static String src = "几种常见的非对称加密算法的在"
			+ "\n" +"Java中的应用几种常见的非对称加密算法的在Java中的应"
			+ "\n" +"用几种常见的非对称加密算法的在Java中的应用几种常见的非"
			+ "\n" +"对称加密算法的在Java中的应用";

	@SuppressWarnings({ "deprecation", "unused", "unused" })
	public static void main(String[] args) throws Exception {
//		 String text = "你好吗";
//		 int width = 100;
//		 int height = 100;
//		 String format = "png";
//		 Hashtable<EncodeHintType, String> hints = new
//		 Hashtable<EncodeHintType, String>();
//		 hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//		 BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
//		 BarcodeFormat.QR_CODE, width, height, hints);
//		 File outputFile = new File("ugly.png");
//		 MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
//		 Date time = new Date();
//		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		 String tString = df.format(time) + "\n" + "dsfsdf";
//		 System.out.println(time);

//		jdkDH();
//		jdkAes();
//		jdkPBE();
//		jdkRSA();
		String format = "png";
		String pathname = "777.png";
		Map<String, SecretKey> map = CryptInitial.doInit();
		SecretKey sender = map.get("SenderDesKey");
		byte[] result = Encode.Encrypt(src, sender, "DESede");
		
		String text = Base64.encodeBase64URLSafeString(result);
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 600, 600, hints);
		BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
		try {
			ImageIO.write(bImage, format, new File(pathname));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		byte[] finalresult = Interpreter.interpretQRCode(pathname);
		SecretKey receiver = map.get("ReceiverDesKey");
		finalresult = Decode.Decrypt(finalresult, receiver, "DESede");
		System.out.println(new String(finalresult));
		
		
//		
		
	}

	private static void jdkAes() {
		try {
			// 生成key
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(new SecureRandom());
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] keyBytes = secretKey.getEncoded();

			// key转换
			Key key = new SecretKeySpec(keyBytes, "AES");

			// 加密
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(src.getBytes());
			System.out.println("encode:" + Base64.encodeBase64String(result));
			;

			// 解密
			cipher.init(Cipher.DECRYPT_MODE, key);
			result = cipher.doFinal(result);
			System.out.println("decode:" + new String(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void jdkPBE() {
		try {
			// 初始化盐
			SecureRandom random = new SecureRandom();
			byte[] salt = random.generateSeed(8);

			// 口令与加密
			String psd = "shxing";
			PBEKeySpec pbeKeySpec = new PBEKeySpec(psd.toCharArray());
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
			Key key = factory.generateSecret(pbeKeySpec);
			
			//加密
			PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, 100);
			Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
			cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
			byte[] result = cipher.doFinal(src.getBytes());
			System.out.println("PBE encode: " + Base64.encodeBase64String(result));
			
			//解密
			cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
			result = cipher.doFinal(result);
			System.out.println("PBE decode: " + new String(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static void jdkDH() {
		try {
			//1.初始化发送方密钥
			KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			senderKeyPairGenerator.initialize(1024);
			KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();
			byte[] senderPublicKeyEncode = senderKeyPair.getPublic().getEncoded();//发送方公钥，发送给接收方
			
			//2.初始化接收方密钥
			KeyFactory receiverKeyFactory = KeyFactory.getInstance("DH");
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEncode);
			PublicKey receiverPublicKey = receiverKeyFactory.generatePublic(x509EncodedKeySpec);
			DHParameterSpec dhParameterSpec = ((DHPublicKey)receiverPublicKey).getParams();
			KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			receiverKeyPairGenerator.initialize(dhParameterSpec);
			KeyPair receiverKeyPair = receiverKeyPairGenerator.generateKeyPair();
			PrivateKey receiverPrivateKey = receiverKeyPair.getPrivate();
			byte[] receiverPublicKeyEncode = receiverKeyPair.getPublic().getEncoded();
			
			//3.密钥构建
			//接收方的公钥
			KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
			receiverKeyAgreement.init(receiverPrivateKey);
			receiverKeyAgreement.doPhase(receiverPublicKey, true);
			//接收方的私钥
			SecretKey receiverDesKey = receiverKeyAgreement.generateSecret("DESede");
			//发送方的公钥
			KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
			x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyEncode);
			PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
			KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
			senderKeyAgreement.init(senderKeyPair.getPrivate());
			senderKeyAgreement.doPhase(senderPublicKey, true);
			//发送方的私钥
			SecretKey senderDesKey = senderKeyAgreement.generateSecret("DESede");
			
			if (Objects.equals(receiverDesKey, senderDesKey)) {
				System.out.println("双方密钥相同");
			}
			
			//4.加密
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, senderDesKey);
			byte[] result = cipher.doFinal(src.getBytes());
			System.out.println("DH encode: " + Base64.encodeBase64String(result));
			
			//5.解密
			cipher.init(Cipher.DECRYPT_MODE, receiverDesKey);
			result = cipher.doFinal(result);
			System.out.println("DH decode: " + new String(result));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static void jdkRSA() {
		try {
			//1.初始化密钥
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
			System.out.println("Public Key: " + Base64.encodeBase64String(rsaPublicKey.getEncoded()));
			System.out.println("Private Key: " + Base64.encodeBase64String(rsaPrivateKey.getEncoded()));
			
			//2.私钥加密，公钥解密--加密
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
			KeyFactory keyFactoryEncode = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactoryEncode.generatePrivate(pkcs8EncodedKeySpec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] result = cipher.doFinal(src.getBytes());
			System.out.println("私钥加密，公钥解密--加密: " + Base64.encodeBase64String(result));
			
			//3.私钥加密，公钥解密--解密
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
			KeyFactory keyFactoryDecode = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactoryDecode.generatePublic(x509EncodedKeySpec);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			result = cipher.doFinal(result);
			System.out.println("私钥加密，公钥解密--解密: " + new String(result));
			
//			//4.公钥加密，私钥解密--加密
//			x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
//			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//			publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
//			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//			result = cipher.doFinal(src.getBytes());
//			System.out.println("公钥加密，私钥解密--加密: " + Base64.encodeBase64String(result));
//			
//			//5.公钥加密，私钥解密--解密
//			pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
//			keyFactory = KeyFactory.getInstance("RSA");
//			privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
//			cipher.init(Cipher.DECRYPT_MODE, privateKey);
//			result = cipher.doFinal(result);
//			System.out.println("私钥加密，公钥解密--解密: " + new String(result));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
