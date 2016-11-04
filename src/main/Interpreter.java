package main;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;

public class Interpreter {

	public static byte[] interpretQRCode(String imgPath) {

		// QRCode 二维码图片的文件
		File imageFile = new File(imgPath);

		BufferedImage bufImg = null;
		byte[] decodedData = null;
		try {
			bufImg = ImageIO.read(imageFile);
			OutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufImg, "png", outputStream);
			QRCodeDecoder decoder = new QRCodeDecoder();
			decodedData = decoder.decode(new J2SEImage(bufImg));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return decodedData;
	}
	
	
}
