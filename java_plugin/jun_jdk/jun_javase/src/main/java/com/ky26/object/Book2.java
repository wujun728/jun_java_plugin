package com.ky26.object;

public class Book2 {
	public static void main(String[] args) {
		
		String[][] arr={{"aa"},{"bb"},{"cc"},{"dd"}};
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr[0].length;j++){
				System.out.print(arr[i][j]+",");
			}
			System.out.println();
		}
		
		
	}
}
class book3{
	
	
	private class science{
		private int id;
		private String name;
		private double price;
		private String author;
		private String publish;
		private String edition;
		private boolean sell;
		private String intro;
		
		public science(){
			
		}
		public science(int id){
			this.id=id;
		}
		public science(String name){
			this.name=name;
		}
		public science(int id,String name){
			this.id=id;
			this.name=name;
		}
		public science(int id,String name,double price){
			this(id,name);
			this.price=price;
		}
		public science(int id,String name,double price,String author,String publish,String edition,boolean sell,String intro){
			this(id,name,price);
			this.author=author;
			this.publish=publish;
			this.edition=edition;
			this.sell=sell;
			this.intro=intro;
		}
	}
	private class literature extends science{
		public literature(){
			super();
		}
	}
	private class politics extends science{
		public politics(){
			super();
		}
	}
	private class novel extends science{
		public novel(){
			super();
		}
	}
}
