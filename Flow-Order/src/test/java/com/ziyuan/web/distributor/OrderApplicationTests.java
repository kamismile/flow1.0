package com.ziyuan.web.distributor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderApplicationTests {

	@Test
	public void contextLoads() {
		
	}
	
	public static void main(String[] args) {
//		List<Student> list = new ArrayList<>();
//		list.add(new Student(1,12,"小明",Timestamp.valueOf("2017-11-01 00:00:00")));
//		list.add(new Student(7,19,"小明",Timestamp.valueOf("2017-10-22 00:00:00")));
//		list.add(new Student(6,24,"小明",Timestamp.valueOf("2017-11-11 00:00:00")));
//		list.add(new Student(8,13,"小明",Timestamp.valueOf("2017-11-05 00:00:00")));
//		
//		list.stream().forEach(obj -> System.out.println(obj));
		Map<String, String> map = new HashMap<String,String>();
		System.out.println(map.put("1", "2"));
		System.out.println(map.put("1", "3"));
		System.out.println(map.put("1", "4"));
		System.out.println(map.get("1"));
		System.out.println(map.containsValue("4"));
		
	}

}
