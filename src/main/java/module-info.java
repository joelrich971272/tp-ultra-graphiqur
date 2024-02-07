module com.example.tp_ultrason_graphique {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tp_ultrason_graphique to javafx.fxml;
    exports com.example.tp_ultrason_graphique;
}