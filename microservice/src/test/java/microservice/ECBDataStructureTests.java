package microservice;

import microservice.Models.ECBDataStructure;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


class ECBDataStructureTests {

	@Test
	void LengthTestZero(){
		ECBDataStructure structure = new ECBDataStructure();
	 	Assertions.assertEquals (0,structure.getLength(),"Length of structure test");
	}

	@Test
	void LengthTest(){
		ECBDataStructure structure = new ECBDataStructure();
		var map = new HashMap<String,Double>();

		map.put("USD",1.2212);
		map.put("PLN",4.4850);
		map.put("CZK",25.424);
		structure.setUpCache(map);
	//	Assert.assertEquals("structure length",map.size(),structure.getLength());
	}

	@Test
	void RemoveItemTest(){
		ECBDataStructure structure = new ECBDataStructure();
		var map = new HashMap<String,Double>();

		map.put("USD",1.2212);
		map.put("PLN",4.4850);
		map.put("CZK",25.424);
		structure.setUpCache(map);
		structure.removeCurrnecy("USD");
		//Assert.assertEquals("structure length",map.size()-1,structure.getLength());
	}



}
