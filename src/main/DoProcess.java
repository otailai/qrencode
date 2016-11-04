package main;

import java.util.Map;

import javax.crypto.SecretKey;

import security.CryptInitial;

public class DoProcess {

	private static Map<String, SecretKey> keyMap = CryptInitial.doInit();
	private static final SecretKey senderDesKey = keyMap.get("senderDesKey");
	private static final SecretKey receiverDesKey = keyMap.get("receiverDesKey");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
