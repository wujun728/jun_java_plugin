package com.jun.plugin.jdbc.bean;

public class User {
		private int id;
		private String name;
		private String pass;
		private String gender;
		private int age;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPass() {
			return pass;
		}
		public void setPass(String pass) {
			this.pass = pass;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public User() {
			super();
			// TODO Auto-generated constructor stub
		}
		public User(int id, String name, String pass, String gender, int age) {
			super();
			this.id = id;
			this.name = name;
			this.pass = pass;
			this.gender = gender;
			this.age = age;
		}
		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + ", pass=" + pass
					+ ", gender=" + gender + ", age=" + age + "]";
		}
	
		
	
}
