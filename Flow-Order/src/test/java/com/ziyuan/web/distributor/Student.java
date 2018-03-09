package com.ziyuan.web.distributor;

import java.sql.Timestamp;

public class Student implements Comparable<Student>{

	private int id;
	private int age;
	private String name;
	private Timestamp create_time;
	
	
	public Student(int id, int age, String name, Timestamp create_time) {
		super();
		this.id = id;
		this.age = age;
		this.name = name;
		this.create_time = create_time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", age=" + age + ", name=" + name + ", create_time=" + create_time + "]";
	}
	@Override
	public int compareTo(Student o) {
		//大于0表示前一个数据比后一个数据大， 0表示相等，小于0表示第一个数据小于第二个数据
		//只能进行对象的比较
		return this.create_time.compareTo(o.getCreate_time());
	}
	
	
}
