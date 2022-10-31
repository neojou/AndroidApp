module com.nj.basicgameapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.nj.basicgameapp to javafx.fxml;
    exports com.nj.basicgameapp;
}