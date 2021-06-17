package microservice.Models;

import java.util.ArrayList;

public class MockedData {

    ArrayList<ECBDataStructure> mock;

    public MockedData() {
    }

    public int length() {
        return mock.size();
    }

    public void applyHistoricalData(ArrayList<ECBDataStructure> histroical)
    {
        this.mock = histroical;
    }

    public ECBDataStructure getOnDate(String date) {
        return mock.stream().filter(s -> s.getDate().equals(date)).findAny().orElse(null);
    }
}
