package microservice;


import microservice.Models.Currency;
import microservice.Models.DataBaseObjectStructure;
import microservice.Models.ECBDataStructure;

import javax.xml.crypto.Data;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class DBConnector {
    private Connection con = null;
    private Statement stat = null;

   public DBConnector()
   {
       try {
           con = DriverManager.getConnection("jdbc:sqlite:exchange.microservice");
       }catch (Exception e)
       {
           e.printStackTrace();
       }
   }

   public void insertECBDataStructure(ECBDataStructure struct)
   {
        insert(struct.getDataBaseObjStruct());
   }

   public ECBDataStructure select(LocalDate day) {
       var ecb =  new ECBDataStructure();
       try {
           ecb.setDate(day.toString());

       String sql = new StringBuilder()
               .append("Select * from currency ")
               .append("Where day = \"")
               .append(day.toString())
               .append("\"")
               .toString();
       var data = con.createStatement().executeQuery(sql);


       while (data.next())
       {
           var currency = new Currency();
           currency.setName(data.getString("target"));
           currency.setValue(data.getDouble("rate"));
           ecb.setUpCache(currency);
       }


       }catch (Exception e)
       {
           e.printStackTrace();
       }
       return ecb ;
   }



    public  void  resetDB(){
        var sql = new StringBuilder();
        sql.append("DROP TABLE if exists currency;");

        try{
            stat = con.createStatement();
            System.out.println(sql.toString());
            var x=   stat.execute(sql.toString());
            System.out.println(x);

            sql.delete(0,sql.length());
            sql.append("create table currency (");
            sql.append(" id INTEGER constraint currency_pk primary key autoincrement,");
            sql.append("target varchar(255),");
            sql.append("day date,");
            sql.append("rate double );");
            Thread.sleep(200);

            stat = con.createStatement();
            System.out.println(sql.toString());
            x= stat.execute(sql.toString());
            System.out.println(x);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public ECBDataStructure select(String sql)
    {

        ECBDataStructure struct = new ECBDataStructure();

        try{
            con = DriverManager.getConnection("jdbc:sqlite:exchange.microservice");
            stat = con.createStatement();

            var x =   stat.executeQuery(sql);

            while (x.next()){
                var currency = new Currency();

                currency.setName(x.getString("target"));
                currency.setValue(x.getDouble("rate"));

                if(struct.getDate() == null)
                    struct.setDate(x.getString("day"));

                struct.addCurrency(currency);
            }


            stat.close();
            con.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    return  struct;
    }

    public void insert(ArrayList<DataBaseObjectStructure> array)
    {
        for(DataBaseObjectStructure x : array)
        {
            insert(x);
        }
    }

    public void insert(DataBaseObjectStructure c)
    {
        Connection con = null;
        Statement stat = null;

        try{
            con = DriverManager.getConnection("jdbc:sqlite:exchange.microservice");
            stat = con.createStatement();

            stat.execute(c.getSqlInsertCommand());

            stat.close();
            con.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
