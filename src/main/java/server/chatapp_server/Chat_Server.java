package server.chatapp_server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Chat_Server extends Application {
    private DataInputStream fromServer = null;
    private DataOutputStream toServer = null;
    private final TextArea taServer = new TextArea();
    private final TextArea taClient = new TextArea();


    @Override
    public void start(Stage stage) {
        //Grid created for window
        GridPane grid = new GridPane();
        taServer.setEditable(false);

        grid.add(new Label("Server"), 0, 0);
        grid.add(new ScrollPane(taServer), 0, 1);

        grid.add(new Label("Client"), 0, 2);
        grid.add(new ScrollPane(taClient), 0, 3);

        Scene scene = new Scene(grid, 300, 400);
        stage.setTitle("Online Chat App!");
        stage.setScene(scene);
        stage.show();
        new Thread(() -> {
            try {
                //Create Socket to
                ServerSocket serverSocket = new ServerSocket(8080);
                Platform.runLater(() -> taServer.appendText("Server started at " + new Date() + "\n"));

                //Opens connection
                Socket socket = serverSocket.accept();

                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    //Receives and outputs the text from server
                    String txt = inputStream.readUTF();
                    outputStream.writeUTF(txt);

                    Platform.runLater(() -> taServer.appendText("- " + txt + "\n"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        //Event triggered when entered the "enter" key
        taClient.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                String txt = null;
                txt = taClient.getText().trim();
                if (toServer == null) {
                    try {
                        Socket socket = new Socket("localhost", 8000);

                        fromServer = new DataInputStream(socket.getInputStream());
                        toServer = new DataOutputStream(socket.getOutputStream());

                        InetAddress inetAddress = socket.getInetAddress();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                try {
                    toServer.writeUTF(txt);
                    toServer.flush();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                taClient.clear();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}