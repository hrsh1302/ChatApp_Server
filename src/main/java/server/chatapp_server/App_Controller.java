package server.chatapp_server;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


//Controller class that creates the GUI elements for the Chat App
public class App_Controller {
    @FXML
    protected AnchorPane anchor_conv;

    @FXML
    protected AnchorPane anchor_user;

    @FXML
    protected Button btSend;

    @FXML
    protected Label conversation;

    @FXML
    protected GridPane grid;

    @FXML
    protected Label lb_enter;

    @FXML
    protected TextArea taServer = new TextArea();

    @FXML
    protected TextField tfClient = new TextField();

}
