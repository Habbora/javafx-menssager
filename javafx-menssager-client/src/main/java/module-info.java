module br.com.habbora.javafxmenssagerclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens br.com.habbora.javafxmenssagerclient to javafx.fxml;
    exports br.com.habbora.javafxmenssagerclient;
}