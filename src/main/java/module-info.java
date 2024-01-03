module server.chatapp_server {
    requires javafx.controls;
    requires javafx.fxml;


    opens server.chatapp_server to javafx.fxml;
    exports server.chatapp_server;
}