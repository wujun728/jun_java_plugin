package com.yc.model;

public class Menu {
	private String menuPic;

	public String getMenuPic() {
		return menuPic;
	}

	public void setMenuPic(String menuPic) {
		this.menuPic = menuPic;
	}

	public Menu(String menuPic) {
		super();
		this.menuPic = menuPic;
	}

	@Override
	public String toString() {
		return "Menu [menuPic=" + menuPic + "]";
	}
	
	
	
	
	
}
