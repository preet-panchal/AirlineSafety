package csci2020u.assignmenttwo;

public class IncidentRecord {
    String airline;
    float seatAvail;
    float incidents_85_99;
    float fatal_accidents_85_99;
    float fatalities_85_99;
    float incidents_00_14;
    float fatal_accidents_00_14;
    float fatalities_00_14;
    float totalIncidents;

    public IncidentRecord(String airline, float seatAvail, float incidents_85_99, float fatal_accidents_85_99, float fatalities_85_99,
                          float incidents_00_14, float fatal_accidents_00_14, float fatalities_00_14, float totalIncidents) {
        this.airline = airline;
        this.seatAvail = seatAvail;
        this.incidents_85_99 = incidents_85_99;
        this.fatal_accidents_85_99 = fatal_accidents_85_99;
        this.fatalities_85_99 = fatalities_85_99;
        this.incidents_00_14 = incidents_00_14;
        this.fatal_accidents_00_14 = fatal_accidents_00_14;
        this.fatalities_00_14 = fatalities_00_14;
        this.totalIncidents = totalIncidents;
    }

    public String getAirline() {
        return this.airline;
    }

    public float getSeatAvail() {
        return this.seatAvail;
    }

    public float getIncidents_85_99() {
        return this.incidents_85_99;
    }

    public float getFatal_accidents_85_99() {
        return this.fatal_accidents_85_99;
    }

    public float getFatalities_85_99() {
        return this.fatalities_85_99;
    }

    public float getIncidents_00_14() {
        return this.incidents_00_14;
    }

    public float getFatal_accidents_00_14() {
        return this.incidents_00_14;
    }

    public float getFatalities_00_14() {
        return this.incidents_00_14;
    }

    public float getTotalIncidents() {
        return this.totalIncidents;
    }
}
