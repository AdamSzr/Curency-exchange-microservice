package microservice.Models;

public class Currency {
    private String name; // private = restricted access
    private Double value; // private = restricted access

    // Getter
    public String getName() {
        return name;
    }

    // Setter
    public void setName(String newName) {
        this.name = newName;
    }

    // Getter
    public Double getValue() {
        return value;
    }

    // Setter
    public void setValue(Double newValue) {
        this.value = newValue;
    }

}
