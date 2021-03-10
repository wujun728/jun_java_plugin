package com.qunar.vacation.example.proxy;

public class ImageProxy implements Image {
	private RealImage realImage;
	private String filename;

	public ImageProxy(String filename) {
		this.filename = filename;
	}

	@Override
	public void display() {
		//Lazy load image
		if (realImage == null) {
			realImage = new RealImage(this.filename);
		}
		realImage.display();
	}

}
