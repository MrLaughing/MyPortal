package com.zai360.portal.test.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

public class JsonRoot {
	public static void main(String[] args) throws Exception {
//		Root_List root = JSONObject.parseObject(ReadRoot("D:\\1.json"), Root_List.class);
		Gson gson = new Gson();
		Root_List root = gson.fromJson(ReadRoot("D:"+File.separator+"1.json"), Root_List.class);
		for (Student s : root.getStudent()) {
			System.out.println(s.getId());
			System.out.println(s.getName());
			System.out.println(s.getAge());
			System.out.println(s.getPassword());
		}
		for (Teacher t : root.getTeacher()) {
			System.out.println(t.getId());
			System.out.println(t.getName());
			System.out.println(t.getAge());
			System.out.println(t.getPassword());
			System.out.println(t.getCar().getNum());
		}
	}

	public static String ReadRoot(String rer) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new FileReader(rer));
		char[] it = new char[1024];
		int sum = 0;
		while ((sum = br.read(it)) != -1) {
			String s = String.valueOf(it, 0, sum);
			sb.append(s);
		}
		br.close();
		return sb.toString();
	}

	class Root_List {
		private List<Student> student;

		private List<Teacher> teacher;

		public void setStudent(List<Student> student) {
			this.student = student;
		}

		public List<Student> getStudent() {
			return this.student;
		}

		public void setTeacher(List<Teacher> teacher) {
			this.teacher = teacher;
		}

		public List<Teacher> getTeacher() {
			return this.teacher;
		}

	}

	class Teacher {
		private String id;
		private String name;
		private String password;
		private String age;
		private Car car;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}

		public Car getCar() {
			return car;
		}

		public void setCar(Car car) {
			this.car = car;
		}

	}

	class Student {
		private String id;
		private String name;
		private String password;
		private String age;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}

	}
	class Car{
		private String num;

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}
		
	}
}
