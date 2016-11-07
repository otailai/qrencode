package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import javax.crypto.SecretKey;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import informations.Customer;
import informations.Produtcion;
import informations.Vendor;
import security.DH3DESEncode;

public class Generator {
	private static final int outputSizes = 600;
	private static final String format = "png";
	private static final String companyName = "XX旅游有限公司";
	
//	private static Map<String, SecretKey> keyMap = CryptInitial.doInit();
//	private static final SecretKey senderDesKey = keyMap.get("senderDesKey");
//	private static final SecretKey receiverDesKey = keyMap.get("receiverDesKey");

	public static void generateQRCode(Customer customer, 
			Produtcion produtction, 
			Vendor vendor,
			SecretKey senderDesKey,
			String instanceName) throws Exception {
		
		Date time = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dfy = new SimpleDateFormat("yyyy-MM-dd");
		String dateTime = df.format(time);
		String dateDay = dfy.format(time);
		String text = companyName + "\n" + dateTime + "\n" + produtction.getProductionName() + "x"
				+ produtction.getProductionNumber() + "\n" + vendor.getVendorName() + "\n" + customer.getCustomerName();
		String pathname = customer.getCustomerName() + dateDay + ".png";
		
		String src = Base64.encodeBase64String(DH3DESEncode.Encrypt(text, senderDesKey, instanceName));
		
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(src, BarcodeFormat.QR_CODE, outputSizes, outputSizes, hints);
		BufferedImage bImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
		try {
			ImageIO.write(bImage, format, new File(pathname));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
