package main;

import java.util.Map;

import javax.crypto.SecretKey;

import security.DH3DESInitial;

public class DoProcess {

	private static Map<String, SecretKey> keyMap = DH3DESInitial.doInit();
	private static final SecretKey senderDesKey = keyMap.get("senderDesKey");
	private static final SecretKey receiverDesKey = keyMap.get("receiverDesKey");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
