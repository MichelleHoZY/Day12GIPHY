package vttp2022.paf.PAFDay115;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vttp2022.paf.PAFDay115.service.GiphyService;

@SpringBootTest
class PafDay115ApplicationTests {

	@Autowired
	private GiphyService gSvc;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldLoad10Images() {
		List gifs = gSvc.getGifs("dog");
		assertEquals(10, gifs.size(), "Default number of gifs is 10");
	}

}
