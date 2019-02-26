package com.board.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameApplicationTests {
	
	private Map<String, Integer> mockPits = new HashMap<String, Integer>();
	
	@Before
	public void createMock() {
		mockPits.put("A1", 0);
		mockPits.put("A2", 6);
		mockPits.put("A3", 6);
		mockPits.put("A4", 6);
		mockPits.put("A5", 6);
		mockPits.put("A6", 6);
		mockPits.put("A7", 6);

		mockPits.put("B1", 6);
		mockPits.put("B2", 6);
		mockPits.put("B3", 6);
		mockPits.put("B4", 6);
		mockPits.put("B5", 6);
		mockPits.put("B6", 6);
		mockPits.put("B7", 0);
	}
	
	@Test
	public void shouldBeFilled() {
		assertThat(mockPits).isNotEmpty();
	}
	
	@Test
	public void shouldBeSame() {
		int stones = 6;
		assertEquals(stones, mockPits.get("A2").intValue());
	}
	
	@Test
	public void shouldBeEmpty() {
		int stones = 0;
		assertEquals(stones, mockPits.get("A1").intValue());
	}
	
	
	

}
