package microservice.Models;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.NodeList;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ECBConnector {
    private static final String historicUrl = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist.xml";
    private static final String dailyUrl = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    DocumentBuilder db;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public ECBConnector(){
        var dbf = DocumentBuilderFactory.newInstance();

        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            // parse XML file
            this.db = dbf.newDocumentBuilder();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public ArrayList<ECBDataStructure> getHistoricalCurrenciesStructure() throws Exception {
        var doc = this.db.parse(this.historicUrl);
        doc.getDocumentElement().normalize();

        NodeList list = doc.getElementsByTagName("Cube");
        return parseHistoric(list);
    }

    private ArrayList<ECBDataStructure> parseHistoric(NodeList entries) throws Exception {
        ECBDataStructure structure = new ECBDataStructure();
        ArrayList<ECBDataStructure> array = new ArrayList<>();

        for (int i = 0; i < entries.getLength(); i++) { // i == 0 cube is empty.

            Currency currency = new Currency();

            if (entries.item(i).getAttributes().getLength() > 0)
                if (entries.item(i).getAttributes().item(0).getNodeName() == "time") {
                    if (i > 1) {
                        array.add(structure);
                        structure = new ECBDataStructure();
                    }
                    structure.setDate(entries.item(i).getAttributes().getNamedItem("time").getTextContent());
//                print( ANSI_GREEN+
//                        entries.item(i).getAttributes().getNamedItem("time").getTextContent() +
//                        ANSI_RESET
//                );
                } else {
                    currency.setName(entries.item(i).getAttributes().getNamedItem("currency").getTextContent());
                    currency.setValue(Double.parseDouble(entries.item(i).getAttributes().getNamedItem("rate").getTextContent()));
                    structure.setUpCache(currency);
//                print(entries.item(i).getAttributes().getNamedItem("currency").getTextContent());
//                print(entries.item(i).getAttributes().getNamedItem("rate").getTextContent());

                }
        }
        array.add(structure);
        return array;
    }

    private void print(Object message) {
        System.out.println(message);
    }

    public String getHirtoricCurrency() throws Exception {
        URL url = new URL(this.historicUrl);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoInput(true);
        var dataStream = con.getInputStream();
        var bytes = dataStream.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public String getDailyCurrency() throws Exception {
        URL url = new URL(this.dailyUrl);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoInput(true);
        var dataStream = con.getInputStream();
        var bytes = dataStream.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public ECBDataStructure getDailyCurrencyStructure() {
        var dbf = DocumentBuilderFactory.newInstance();

        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            var doc = db.parse(this.dailyUrl);
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("Cube");
            var returnObj =  buildECBStructure(list);
            return  returnObj;

        }catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
      return  null;
    }

    private ECBDataStructure buildECBStructure(NodeList list) throws Exception {

        //StringBuilder builder = new StringBuilder();
//
//        System.out.println(entries.item(0).getAttributes().item(0).getTextContent());
//        System.out.println(entries.item(1).getAttributes().item(0).getTextContent());
//        System.out.println(entries.item(1).getAttributes().item(0).getNodeName());

        var date = "";
        Map<String, Double> mapa = new HashMap<>();
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getAttributes().getLength() > 0) {
                if (list.item(i).getAttributes().item(0).getNodeName() == "time") {
                    date = list.item(i).getAttributes().getNamedItem("time").getTextContent();
//                    System.out.println(date);
                    /*
                    builder.append(list.item(i).getAttributes().item(0).getNodeName());
                    builder.append(":");
                    builder.append(list.item(i).getAttributes().item(0).getTextContent());
                    */


                } else {
                    /*
                    builder.append(list.item(i).getAttributes().item(0).getNodeName());
                    builder.append(":");
                    builder.append(list.item(i).getAttributes().item(0).getTextContent());
                    builder.append("\t");
                    builder.append(list.item(i).getAttributes().item(1).getNodeName());
                    builder.append(":");
                    builder.append(list.item(i).getAttributes().item(1).getTextContent());
                    */
                    mapa.put(
                            list.item(i).getAttributes().item(0).getTextContent(),
                            Double.parseDouble(list.item(i).getAttributes().item(1).getTextContent())
                    );
                }
                // System.out.println(builder);
                // builder.setLength(0);
            }
        }
        ECBDataStructure dataStructure = new ECBDataStructure();
        dataStructure.setDate(date);
        dataStructure.setUpCache(mapa);

        return dataStructure;

    }

}
