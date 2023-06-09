package lt.krtrinkunas.twodayinternship;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(AnimalControllerTest.class)
class TwodayinternshipApplicationTests {

	@Test
	void contextLoads() {
	}

}
