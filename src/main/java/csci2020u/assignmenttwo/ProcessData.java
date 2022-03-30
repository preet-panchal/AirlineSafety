package csci2020u.assignmenttwo;

import javafx.collections.ObservableList;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class ProcessData {

    public void processData(String filename) {

        FileLoader loadFile = new FileLoader(filename);
        loadFile.readCSV();
        ObservableList<IncidentRecord> incidents = loadFile.getIncidents();
        convertXML(incidents);
        modifyCSV(incidents);
        summaryXML(incidents);
    }

    public void convertXML(ObservableList<IncidentRecord> incidents) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("Summary");
            doc.appendChild(rootElement);

            for (IncidentRecord data : incidents) {
                // Airline element
                Element Airline = doc.createElement("Airline");
                rootElement.appendChild(Airline);

                Element name = doc.createElement("Name");
                name.appendChild(doc.createTextNode(data.getAirline()));
                Airline.appendChild(name);

                // availSeat element
                Element availSeat = doc.createElement("seatAvail");
                availSeat.appendChild(doc.createTextNode(String.valueOf(data.getSeatAvail())));
                Airline.appendChild(availSeat);

                // incidents_85_99 element
                Element incidents_85_99 = doc.createElement("incidents_85_99");
                incidents_85_99.appendChild(doc.createTextNode(String.valueOf(data.getIncidents_85_99())));
                Airline.appendChild(incidents_85_99);

                // fatal_accidents_85_99 element
                Element fatal_accidents_85_99 = doc.createElement("fatal_accidents_85_99");
                fatal_accidents_85_99.appendChild(doc.createTextNode(String.valueOf(data.getFatal_accidents_85_99())));
                Airline.appendChild(fatal_accidents_85_99);

                // fatal_accidents_85_99 element
                Element fatalities_85_99 = doc.createElement("fatalities_85_99");
                fatalities_85_99.appendChild(doc.createTextNode(String.valueOf(data.getFatalities_85_99())));
                Airline.appendChild(fatalities_85_99);

                // incidents_00_14 element
                Element incidents_00_14 = doc.createElement("incidents_00_14");
                incidents_00_14.appendChild(doc.createTextNode(String.valueOf(data.getIncidents_00_14())));
                Airline.appendChild(incidents_00_14);

                // fatal_accidents_00_14 element
                Element fatal_accidents_00_14 = doc.createElement("fatal_accidents_00_14");
                fatal_accidents_00_14.appendChild(doc.createTextNode(String.valueOf(data.getFatal_accidents_85_99())));
                Airline.appendChild(fatal_accidents_00_14);

                // fatalities_00_14 element
                Element fatalities_00_14 = doc.createElement("fatalities_00_14");
                fatalities_00_14.appendChild(doc.createTextNode(String.valueOf(data.getFatalities_00_14())));
                Airline.appendChild(fatalities_00_14);

                // totalIncidents element
                Element totalIncidents = doc.createElement("totalIncidents");
                totalIncidents.appendChild(doc.createTextNode(String.valueOf(data.getTotalIncidents())));
                Airline.appendChild(totalIncidents);

                // setting attribute to element
                //Attr attr = doc.createAttribute("company");
                //attr.setValue("Ferrari");
                //supercar.setAttributeNode(attr);

            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src\\main\\resources\\csci2020u\\assignmenttwo\\converted_airline_safety.xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(4));
            transformer.transform(source, result);

            // Output to console for testing
            // StreamResult consoleResult = new StreamResult(System.out);
            // transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifyCSV(ObservableList<IncidentRecord> incidents) {
        IncidentRecord records;
        List <List<String>> arrayList = new ArrayList<>();
        arrayList.add(new ArrayList<>());
        arrayList.get(0).add("airline");
        arrayList.get(0).add("avail_seat_km_per_week");
        arrayList.get(0).add("incidents_85_99");
        arrayList.get(0).add("fatal_accidents_85_99");
        arrayList.get(0).add("fatalities_85_99");
        arrayList.get(0).add("incidents_00_14");
        arrayList.get(0).add("fatal_accidents_00_14");
        arrayList.get(0).add("fatalities_00_14");
        arrayList.get(0).add("total_incidents_85_14");


        for (int i = 0; i < incidents.size(); i++) {
            IncidentRecord record = incidents.get(i);
            arrayList.add(new ArrayList<>());
            arrayList.get(i + 1).add(record.getAirline());
            arrayList.get(i + 1).add(String.valueOf(record.getSeatAvail()));
            arrayList.get(i + 1).add(String.valueOf(record.getIncidents_85_99()));
            arrayList.get(i + 1).add(String.valueOf(record.getFatal_accidents_85_99()));
            arrayList.get(i + 1).add(String.valueOf(record.getFatalities_85_99()));
            arrayList.get(i + 1).add(String.valueOf(record.getIncidents_00_14()));
            arrayList.get(i + 1).add(String.valueOf(record.getFatal_accidents_00_14()));
            arrayList.get(i + 1).add(String.valueOf(record.getFatalities_00_14()));
            arrayList.get(i + 1).add(String.valueOf(record.getTotalIncidents()));
        }

        StringBuilder string = new StringBuilder();

        for (List<String> element : arrayList) {
            if (element.size() > 0) {
                for (int j = 0; j < element.size(); j++) {
                    string.append(element.get(j));
                    if (j != element.size() - 1) {
                        string.append(',');
                    }
                }
                string.append('\n');
            }
        }

        // replace with filename after
        try (PrintWriter writer = new PrintWriter("airline_safety_updated.csv")){
            writer.append(string.toString());
        }
        catch (FileNotFoundException err){
            //System.out.println(err.getMessage());
        }

    }

    public void summaryXML(ObservableList<IncidentRecord> incidents) {

        Map<String, Float> avgs = new HashMap<>();
        Map<String, Float> mins = new HashMap<>();
        Map<String, Float> maxs = new HashMap<>();

        for (IncidentRecord data : incidents) {
            avgs.put("avg_incidents_85_99", data.getIncidents_85_99());
            avgs.put("avg_fatal_incidents_85_99", data.getFatal_accidents_85_99());
            avgs.put("avg_fatalities_85_99", data.getFatalities_85_99());
            avgs.put("avg_incidents_00_14", data.getIncidents_00_14());
            avgs.put("avg_fatal_incidents_00_14", data.getFatal_accidents_00_14());
            avgs.put("avg_fatalities_00_14", data.getFatalities_00_14());
            avgs.put("avg_total_incidents_85_14", data.getTotalIncidents());
        }

        for (String key : avgs.keySet()) {
            mins.put(key, 0.0f);
            maxs.put(key, 0.0f);
        }

        for (IncidentRecord data : incidents) {
            mins.put("avg_incidents_85_99", Math.min(data.getIncidents_85_99(), mins.get("avg_incidents_85_99")));
            mins.put("avg_fatal_incidents_85_99", Math.min(data.getFatal_accidents_85_99(), mins.get("avg_fatal_incidents_85_99")));
            mins.put("avg_fatalities_85_99", Math.min(data.getFatalities_85_99(), mins.get("avg_fatalities_85_99")));
            mins.put("avg_incidents_00_14", Math.min(data.getIncidents_00_14(), mins.get("avg_incidents_00_14")));
            mins.put("avg_fatal_incidents_00_14", Math.min(data.getFatal_accidents_00_14(), mins.get("avg_fatal_incidents_00_14")));
            mins.put("avg_fatalities_00_14", Math.min(data.getFatalities_00_14(), mins.get("avg_fatalities_00_14")));
            mins.put("avg_total_incidents_85_14", Math.min(data.getTotalIncidents(), mins.get("avg_total_incidents_85_14")));

            maxs.put("avg_incidents_85_99", Math.max(data.getIncidents_85_99(), mins.get("avg_incidents_85_99")));
            maxs.put("avg_fatal_incidents_85_99", Math.max(data.getFatal_accidents_85_99(), mins.get("avg_fatal_incidents_85_99")));
            maxs.put("avg_fatalities_85_99", Math.max(data.getFatalities_85_99(), mins.get("avg_fatalities_85_99")));
            maxs.put("avg_incidents_00_14", Math.max(data.getIncidents_00_14(), mins.get("avg_incidents_00_14")));
            maxs.put("avg_fatal_incidents_00_14", Math.max(data.getFatal_accidents_00_14(), mins.get("avg_fatal_incidents_00_14")));
            maxs.put("avg_fatalities_00_14", Math.max(data.getFatalities_00_14(), mins.get("avg_fatalities_00_14")));
            maxs.put("avg_total_incidents_85_14", Math.max(data.getTotalIncidents(), mins.get("avg_total_incidents_85_14")));
        }

        avgs.replaceAll((k, v) -> avgs.get(k) / incidents.size());

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("Summary");
            doc.appendChild(rootElement);

            for (String key : avgs.keySet()) {
                // Stat element
                Element statElement = doc.createElement("Stat");
                rootElement.appendChild(statElement);

                Element Name = doc.createElement("Name");
                Name.appendChild(doc.createTextNode(key));
                statElement.appendChild(Name);

                Element Min = doc.createElement("Min");
                Min.appendChild(doc.createTextNode(String.valueOf(mins.get(key))));
                statElement.appendChild(Min);

                Element Max = doc.createElement("Max");
                Max.appendChild(doc.createTextNode(String.valueOf(maxs.get(key))));
                statElement.appendChild(Max);

                Element Avg = doc.createElement("Avg");
                Avg.appendChild(doc.createTextNode(String.valueOf(avgs.get(key))));
                statElement.appendChild(Avg);

            }
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src\\main\\resources\\csci2020u\\assignmenttwo\\airline_summary_statistic.xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(4));
            transformer.transform(source, result);

            // Output to console for testing
            //StreamResult consoleResult = new StreamResult(System.out);
            //transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
