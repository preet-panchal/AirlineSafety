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
import java.util.List;

public class ProcessData {

    public void processData(String filename) {
        //<?xml version = "1.0" encoding = "UTF-8" standalone = "no"?>

        //<cars>
        //<supercars company = "Ferrari">
        //<carname type = "formula one">Ferrari 101</carname>
        //<carname type = "sports">Ferrari 202</carname>
        //</supercars>
        //</cars>

        FileLoader loadFile = new FileLoader(filename);
        loadFile.readCSV();
        ObservableList<IncidentRecord> incidents = loadFile.getIncidents();
        System.out.println(incidents.get(0));

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
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
