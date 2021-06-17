package microservice;

public class ExchangeAnswerObject {

    private  String from;
    private  String to;
    private Double amount;
    private  Double value;
    private  String date;

    public ExchangeAnswerObject(){

    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Double getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }


    public void setFrom(String from) {
        this.from=from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ExchangeAnswerObject{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", amount=" + amount +
                ", value=" + value +
                ", date='" + date + '\'' +
                '}';
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
