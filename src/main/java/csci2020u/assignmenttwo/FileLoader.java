package csci2020u.assignmenttwo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;

public class FileLoader {
    private final String filename;
    private final ObservableList<IncidentRecord> incidents;

    public FileLoader(String filename){
        this.filename = filename;
        this.incidents = FXCollections.observableArrayList();
    }

    public void readCSV(){
        String line = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.filename));
            line = br.readLine();
            while ((line = br.readLine())!=null){
                String[] columns = line.split(",");
                newIncident(columns);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void newIncident(String[] column){
        float totalIncidents = Float.parseFloat(column[2]) + Float.parseFloat(column[5]);
        IncidentRecord airlineData = new IncidentRecord(column[0], Float.parseFloat(column[1]), Float.parseFloat(column[2]), Float.parseFloat(column[3]),
                Float.parseFloat(column[4]), Float.parseFloat(column[5]), Float.parseFloat(column[6]), Float.parseFloat(column[7]), totalIncidents);
        incidents.add(airlineData);
    }

    public ObservableList<IncidentRecord> getIncidents(){
        return this.incidents;
    }
}