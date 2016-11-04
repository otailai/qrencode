package main;

import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;

class J2SEImage implements QRCodeImage {
    BufferedImage bufImg;

    public J2SEImage(BufferedImage bufImg) {
        this.bufImg = bufImg;
    }

    public int getWidth() {
        return bufImg.getWidth();
    }

    public int getHeight() {
        return bufImg.getHeight();
    }

    public int getPixel(int x, int y) {
        return bufImg.getRGB(x, y);
    }

}
