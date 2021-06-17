package microservice.Models;

import microservice.Utils;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.Date;

public class DataBaseObjectStructure {

    private String to;
    private LocalDate day;
    private double rate;

    @Override
    public String toString() {
        return "ExchangeAnswerObject{" +
                " to='" + to + '\'' +
                ", date='" + day.toString() + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }


    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }


    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }
    public void setDay(String  day){
        this.day = LocalDate.parse(day);

    }


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSqlInsertCommand()
    {
        StringBuilder b = new StringBuilder();
        b.append("INSERT INTO currency (target, day ,rate) ");
        b.append("VALUES (");
        b.append( "\"" +this.to+"\", ");
        b.append("\"" +this.day+"\", ");
        b.append(this.rate+")");

       return b.toString();
    }


    public static String getSqlSelectCommand(LocalDate day, String to)
    {

        StringBuilder b = new StringBuilder();
        b.append("SELECT * from currency ");
        b.append( "WHERE day = \""+ day.toString() +"\" AND target = \"" + to+"\"");

        return b.toString();
    }
    public static String getSqlSelectCommand(LocalDate day, String to1,String to2)
    {
        StringBuilder b = new StringBuilder();
        b.append("SELECT * from currency ");
        b.append( "WHERE day = \""+ day.toString() +"\" OR target = \"" + to1+"\""+" AND target = \"" + to2+"\"");

        return b.toString();
    }


}
