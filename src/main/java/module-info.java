module csci2020u.assignmenttwo {
    requires javafx.controls;
    requires javafx.fxml;


    opens csci2020u.assignmenttwo to javafx.fxml;
    exports csci2020u.assignmenttwo;
}