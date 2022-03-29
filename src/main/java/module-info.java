module csci2020u.assignmenttwo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens csci2020u.assignmenttwo to javafx.fxml;
    exports csci2020u.assignmenttwo;
}