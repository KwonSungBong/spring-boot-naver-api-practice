package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void test() throws IOException {
//		String test = "{\"message\":{\"@type\":\"response\",\"@service\":\"korea.naverkoreaservice.community.cafe\",\"@version\":\"1.0.0\",\"status\":\"200\",\"result\":{\"msg\":\"Success\",\"cafeUrl\":\"saneprogramming\",\"articleId\":180,\"articleUrl\":\"http://cafe.naver.com/saneprogramming/180\"}}}";
		String test = "{\"message\":{\"type\":\"response\",\"service\":\"korea.naverkoreaservice.community.cafe\",\"version\":\"1.0.0\",\"status\":\"200\"}}";
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> testMap = mapper.readValue(test.replace("@", ""),
				new TypeReference<HashMap<String, Object>>() {});

		System.out.println();
	}

}
