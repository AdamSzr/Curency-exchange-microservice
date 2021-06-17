package microservice;

import microservice.Models.CCalculator;
import microservice.Models.ECBDataStructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.HashMap;

public class CCalculatorTests {

    @Test
    void BasicCalc() throws Exception {
        var map = new HashMap<String,Double>();
        map.put("USD",1.2212);
        map.put("PLN",4.4850);
        map.put("CZK",25.424);

        ECBDataStructure structure = new ECBDataStructure();
        structure.setUpCache(map);
        structure.setDate("2021-05-27");

        CCalculator calc = new CCalculator();
        calc.setDataStrucure(structure);

            Assertions.assertEquals(structure.getValue("PLN"),calc.count("EUR","PLN"),"From EURO to PLN");
           //  Assert.assertEquals(,);

    }

    @Test
    void ReverseCalc() throws Exception {
        var map = new HashMap<String,Double>();
        map.put("USD",1.2212);
        map.put("PLN",4.4850);
        map.put("CZK",25.424);

        ECBDataStructure structure = new ECBDataStructure();
        structure.setUpCache(map);
        structure.setDate("2021-05-27");

        CCalculator calc = new CCalculator();
        calc.setDataStrucure(structure);
        try {
            var expected = 1.0d / structure.getValue("PLN");
            var res = calc.count("PLN","EUR");
            //Assert.assertEquals("reverse calc",(double) expected,(double) res,0.0001d);
        }catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
    @Test
    void FromPLNtoUSD() throws Exception {
        var map = new HashMap<String,Double>();
        map.put("USD",1.2212);
        map.put("PLN",4.4850);
        map.put("CZK",25.424);

        ECBDataStructure structure = new ECBDataStructure();
        structure.setUpCache(map);
        structure.setDate("2021-05-27");

        CCalculator calc = new CCalculator();
        calc.setDataStrucure(structure);
        try {
            var expected =  structure.getValue("USD") / structure.getValue("PLN");
            var res = calc.count("PLN","USD");
            System.out.println(calc.count("PLN","CZK"));
        //    Assert.assertEquals("reverse calc",(double) expected,(double) res,0.0001d);
        }catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}
