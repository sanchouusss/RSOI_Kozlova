module RSOI.Kozlova {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires lombok;
    requires org.hibernate.orm.core;

    opens project to javafx.fxml;
    opens project.GUI.controllers to javafx.fxml;
    opens project.model to org.hibernate.orm.core;
    exports project;
    exports project.GUI.controllers;
    exports project.util;
    opens project.util to javafx.fxml;
    exports project.net;
    opens project.net to javafx.fxml;
}
