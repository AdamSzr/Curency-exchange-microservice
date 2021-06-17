package microservice;

import microservice.Models.CCalculator;
import microservice.Models.ECBConnector;
import microservice.Models.MockedData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ECBConnectorTests {

    @Test
    public void DailyCurrencyTest() throws Exception {
        ECBConnector connector = new ECBConnector();
        var item = connector.getDailyCurrencyStructure();
        Assertions.assertTrue(item.getLength()>0,"dayly currencies length");
    }

    @Test
    public void MockedFindByDate() throws Exception {
        var mocked = new MockedData();

        var date = "2016-04-15";
        var struct = mocked.getOnDate(date);

        Assertions.assertTrue(struct.getLength()>0,"get on spec day - struct len");
    }

    @Test
    public void CalcOnMockedData() throws Exception {
        var mocked = new MockedData();
        var histData = new ECBConnector().getHistoricalCurrenciesStructure();

        mocked.applyHistoricalData(histData);
        var date = "2016-04-15";
        var struct = mocked.getOnDate(date);
        var calc = new CCalculator();
        calc.setDataStrucure(struct);

        Assertions.assertEquals(calc.count("eur","pln"),4.2967d,0.0001d, "Mocked find date");
    }

}
