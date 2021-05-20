package com.jun.plugin.json.jackson.d04;

class Album {
	private String title;
	private Dataset[] dataset;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Dataset[] getDataset() {
		return dataset;
	}

	public void setDataset(Dataset[] dataset) {
		this.dataset = dataset;
	}
}

class Dataset {
	private String album_title;

	public String getAlbum_title() {
		return album_title;
	}

	public void setAlbum_title(String album_title) {
		this.album_title = album_title;
	}
}
