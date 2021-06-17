package microservice;


import microservice.Models.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


@Controller
public class CurencyExchangeController {
    private MockedData mocked;
    private CCalculator calc;
    private ECBConnector connector;
    private SimpleDateFormat formatter;
    private DBConnector db;

    public CurencyExchangeController() throws Exception {
        mocked = new MockedData();
        connector = new ECBConnector();
        calc = new CCalculator();
        db = new DBConnector();


        calc.setDataStrucure(connector.getDailyCurrencyStructure());
        calc.setDb(db);

        formatter = new SimpleDateFormat("yyyy-MM-dd");

    }



    private void RefreshMechanism() {

        Timer t = new Timer("Task timer");
        LoopTask task = new LoopTask();
        Date exec = new Date();
        t.schedule(task,new Date(new Date().getTime()+5000));

    }

    private class LoopTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("RUN");
            Timer t = new Timer("Task timer");
            LoopTask task = new LoopTask();
            Date exec = new Date();
            t.schedule(task, new Date(new Date().getTime() + 5000));
        }
    }

    @GetMapping("/curency/exchange")
    @ResponseBody
    public ExchangeAnswerObject Calc(
            @RequestParam(required = true) String from,
            @RequestParam(required = true) String to,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String count
    ) throws Exception {
        var amount = count != null ? Double.valueOf(count) : 1d;

        ExchangeAnswerObject ret = new ExchangeAnswerObject();

        ret.setFrom(from);
        ret.setTo(to);
        ret.setAmount(amount);


        if (date != null && !date.equals(calc.getDate())) {
            formatter.parse(date);
            ret.setDate(date);

            date = this.validateDay(date);

            var sql =ECBDataStructure.getSqlSelectCommand(date);
            var data = db.select(sql);

            ret.setValue(calc.countOnce( data, from, to, Double.valueOf(amount)));
        } else {
            ret.setDate(calc.getDate());
            ret.setValue(calc.count(from, to, amount));
        }

        System.out.println(ret);
        return ret;
    }

    public String validateDay(String day) throws Exception {
        var locDate = LocalDate.parse(day);
        var d = locDate.getDayOfWeek();

        //System.out.println(d.toString());

        if(d.equals( DayOfWeek.SATURDAY))
            locDate =  locDate.minusDays(1);

        if(d.equals(DayOfWeek.SUNDAY))
            locDate = locDate.minusDays(2);

       // System.out.println(locDate);
        return locDate.toString();

    }

}
