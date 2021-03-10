package com.qunar.vacation.example.proxy;

public class RealImage implements Image {
	private byte[] image;
	
	public RealImage(String filename) {
		loadImage(filename);
	}

	
	private void loadImage(String filename) {
		//load image from filename to image byte array
		//a time consuming function
		System.out.println("Load image...");
	}


	@Override
	public void display() {
		System.out.println("Display image");
	}

}
