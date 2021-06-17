package microservice.Models;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ECBDataStructure {

    private String defaultCurency = "EUR";
    private String useByDate;
    private LoadingCache<String, Double> cache;
    private SimpleDateFormat dateFormat;
    private CacheLoader loader;

    public ECBDataStructure() {
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        this.loader = new CacheLoader<String, Double>() {
            @Override
            public Double load(String s) throws Exception {
                return null;
            }
        };
        this.cache = CacheBuilder.newBuilder().build(loader);
    }

    public void setUp(LoadingCache<String, Double> memory, Date d) {
        this.setUpCache(memory);
        this.setDate(d);
    }

    public void setUp(Map<String, Double> memory, Date d) {
        this.setUpCache(memory);
        this.setDate(d);
    }


    public void setDate(String d) throws Exception {
        dateFormat.parse(d);
        this.useByDate = d;
    }

    public void setDate(Date d) {
        this.useByDate = dateFormat.format(d);
    }
    public  String getDate()
    {
        return  this.useByDate;
    }

    public void setUpCache(LoadingCache<String, Double> memory) {
        this.cache = memory;
    }

    public void setUpCache(Map<String, Double> memory) {
        var newCache = CacheBuilder.newBuilder().build(loader);
        newCache = CacheBuilder.newBuilder().build(loader);
        newCache.putAll(memory);
        setUpCache(newCache);
    }
    public void setUpCache(Currency c)
    {
        this.cache.put(c.getName(),c.getValue());
    }

    public ArrayList<DataBaseObjectStructure> getDataBaseObjStruct()
    {
        var x = new ArrayList<DataBaseObjectStructure>();

        this.cache.asMap().forEach((k,v)->{
            var structure = new DataBaseObjectStructure();
            structure.setDay(this.useByDate);
            structure.setTo(k);
            structure.setRate(v);
            x.add(structure);
        });

        return  x;
    }

    public Double getValue(String key) throws Exception {
        if (doesKeyExists(key))
            return cache.get(key);
        else
            throw new Exception("Key does not exists");
    }

    public int getLength() {
        return (int) cache.size();
    }

    public void applyCurrencies(LoadingCache<String, Double> currencies) {
        this.cache = currencies;
    }

    public void addCurrency(String name, Double value) throws Exception {
        if (!doesKeyExists(name))
            this.cache.put(name, value);
        else
            throw new Exception("Key already exists.");
    }

    public void addCurrency(Currency currency) throws Exception {
        this.addCurrency(currency.getName(), currency.getValue());
    }

    public Boolean doesKeyExists(String key) {
        return this.cache.asMap().keySet().stream().anyMatch(k -> k.equals(key));
    }

    public void clearDataSet() {
        this.cache.invalidateAll();
    }

    public void removeCurrnecy(String name) {
        this.cache.invalidate(name);
    }

    public Boolean isDefaultCurency(String curency) {
        return this.defaultCurency.equals(curency);
    }

    public void setDefaultCurency(String curency) {
        this.defaultCurency = curency;
    }

    public ArrayList<DataBaseObjectStructure> createDbStructs()
    {
        var list = new ArrayList<DataBaseObjectStructure>();
        this.cache.asMap().forEach( (k,v) ->
                {
                    var item = new DataBaseObjectStructure();
                    item.setTo(k);
                    item.setDay(this.useByDate);
                    item .setRate(v);
                    list.add( item );
                });

        return list;
    }

  public static String getSqlSelectCommand(String day)
  {
      StringBuilder b = new StringBuilder();
      b.append("Select * from currency ");
      b.append("Where day = \"");
      b.append(day);
      b.append("\"");
      return b.toString();
  }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("time : ");
        b.append(this.useByDate);
        b.append("\r\n");


        this.cache.asMap().keySet().stream().forEach(s -> {
            b.append(s);
            b.append(" : ");
            try {
                b.append(this.getValue(s));
            } catch (Exception e) {
                e.printStackTrace();
            }
            b.append("\r\n");
        });

        return b.toString();
    }



    public Boolean isDateSame(Date d) {
        return dateFormat.format(this.useByDate).equals(dateFormat.format(d));
    }
}
