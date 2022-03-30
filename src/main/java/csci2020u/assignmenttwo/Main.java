package csci2020u.assignmenttwo;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FileLoader loadFile = new FileLoader("airline_safety.csv");
        loadFile.readCSV();
        ObservableList<IncidentRecord> incidents = loadFile.getIncidents();

        // using the XYChart.Series library to generate bar plot
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        XYChart.Series series1 = new XYChart.Series(); // for fatal_incidents_85_99
        XYChart.Series series2 = new XYChart.Series(); // for fatal_incidents_00_14

        series1.setName("fatal_incidents_85_99");
        series2.setName("fatal_incidents_00_14");
        bc.setTitle("Airline Incident Summary");
        xAxis.setLabel("Airline");
        yAxis.setLabel("Number of Fatal Incidents");

        // getting incident values from columns to plot for each designated airline
        for (IncidentRecord data : incidents) {
            series1.getData().add(new XYChart.Data(data.getAirline(), data.getFatal_accidents_85_99()));
            series2.getData().add(new XYChart.Data(data.getAirline(), data.getFatal_accidents_00_14()));
        }

        Scene scene = new Scene(bc,1800, 1000);
        bc.getData().addAll(series1, series2);
        stage.setTitle("CSCI2020U Assignment 2 - Part 3");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Handles part 01 and 02 of assignment
        ProcessData data = new ProcessData();
        data.processData("airline_safety.csv");
        launch();
    }
}