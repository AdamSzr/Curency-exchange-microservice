package microservice;

import microservice.Models.DataBaseObjectStructure;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

public class DBTests {

    @Test
    public  void InsertTestData()
    {
        DBConnector connector = new DBConnector();
        DataBaseObjectStructure structure = new DataBaseObjectStructure();
        structure.setRate(1.0d);
        structure.setDay(LocalDate.now());
        structure.setTo("JPY");
        structure.getSqlInsertCommand();
        connector.insert(structure);
    }
    @Test
    public  void SelectDataTest()
    {
        DBConnector connector = new DBConnector();

        String query = DataBaseObjectStructure.getSqlSelectCommand(LocalDate.now(),"PLN");
        var x = connector.select(query);
        System.out.println(x.toString());
    }
    @Test
    public  void Select2DataTest()
    {
        DBConnector connector = new DBConnector();

        String query = DataBaseObjectStructure.getSqlSelectCommand(LocalDate.now(),"PLN","JPY");
        var objs= connector.select(query);


    }
    @Test
    public  void selectECBDataStruct() {
        LocalDate d = LocalDate.now();
        d = LocalDate.parse("2021-06-02");

        DBConnector db = new DBConnector();
        var data =  db.select(d);
        System.out.println(data.toString());
    }


    @Test
    public  void test(){
        //new Date().toLocaleString();

        LocalDate d = LocalDate.now();
        d = LocalDate.parse("2021-06-02");

        System.out.println(d.toString());

        Date day = convertToDateViaInstant(d);
        System.out.println(day.toGMTString());
        d = convertToLocalDateViaInstant(day);

        System.out.println(d.toString());
    }

    @Test
    public void ResetDB(){
        DBConnector d = new DBConnector();
        d.resetDB();
    }


    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return Utils.convertToLocalDateViaInstant(dateToConvert);
    }
    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return Utils.convertToDateViaInstant(dateToConvert);
    }

    @Test
    public void InsertArrayListInDB()
    {

    }


}
