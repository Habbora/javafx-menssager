module br.com.habbora.javafxmenssagerserver {
    requires javafx.controls;
    requires javafx.fxml;


    opens br.com.habbora.javafxmenssagerserver to javafx.fxml;
    exports br.com.habbora.javafxmenssagerserver;
}