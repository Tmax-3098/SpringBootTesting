package com.sketch.TestingApp;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

//@SpringBootTest
@Slf4j
class TestingAppApplicationTests {

	@BeforeEach
	void setup(){
		log.info("setting up");
	}

	@AfterEach
	void cleanup(){
		log.info("cleaning up");
	}

	@Test
	void contextLoads() {
		int a =5;
		int b = 6;
		int result = addTwoNumbers(a,b);
		//Assertions.assertEquals(11,result);
		Assertions.assertThat(result).isEqualTo(11)
				.isCloseTo(10, Offset.offset(1));

	}


	@Test
	void testDivideTwoNumbers_whenDenominatorIsZero(){
		int a = 10;
		int b = 0;
		assertThatThrownBy(() -> divideTwoIntegers(a,b))
				.isInstanceOf(ArithmeticException.class);

	}

	@Test
	void test3(){
		log.info("running test 3");
	}

	int addTwoNumbers(int a,int b){
		return a+b;
	}

	double divideTwoIntegers(int a, int b){
		try{
			return a/b;
		}catch (ArithmeticException e){
			log.error(e.getLocalizedMessage());
			throw new ArithmeticException(e.getLocalizedMessage());
		}
	}

}
