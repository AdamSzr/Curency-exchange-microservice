package microservice.Models;


import microservice.DBConnector;

import java.util.Date;

public class CCalculator {

    ECBDataStructure data;
    DBConnector db;

    public void setDataStrucure(ECBDataStructure strucure) {
        this.data = strucure;
    }
    public void setDb(DBConnector db)
    {
        this.db=db;
    }

    public Double countOnce(ECBDataStructure structure,String from,String to,Double count)
    {
        var old = this.data;
        this.data = structure;
        var value = this.count(from,to,Double.valueOf(count));
        this.data = old;
        return  value;
    }


    public String getDate() {
        return this.data.getDate();
    }

//    public Double count(String from, String to, Double count, Date date) {
//        if (data.isDateSame(date))
//            return this.count(from, to, count);
//        else
//            return 0d;
//    }



    public Double count(String from, String to, Double count) {
        return count(from, to) * count;
    }

    public Double count(String from, String to) {
        from = from.toUpperCase();
        to = to.toUpperCase();
        try {
            if (this.data.isDefaultCurency(from) && this.data.doesKeyExists(to)) // from euro to...
            {
                return this.data.getValue(to);
            } else if (this.data.doesKeyExists(from) && this.data.isDefaultCurency(to)) // from xxx to euro
            {
                return 1.0d / this.data.getValue(from);
            }
            if (this.data.doesKeyExists(from) && this.data.doesKeyExists(to)) // from xxx to yyy
            {
                return this.data.getValue(to) / this.data.getValue(from);
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return 1d;
    }

}
