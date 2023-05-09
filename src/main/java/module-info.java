module com.bearzwebworks.beardb {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.bearzwebworks.beardb to javafx.fxml;
    exports com.bearzwebworks.beardb;
    exports com.bearzwebworks.beardb.fx_controllers;
    opens com.bearzwebworks.beardb.fx_controllers to javafx.fxml;
    exports com.bearzwebworks.beardb.db;
    opens com.bearzwebworks.beardb.db to javafx.fxml;

    exports com.bearzwebworks.beardb.db.model;
}