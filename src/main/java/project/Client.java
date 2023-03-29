package project;


import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.GUI.controllers.LoadingController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client extends Application {

    public static Socket socket = null;
    public static ObjectOutputStream outputstream = null;
    public static ObjectInputStream inputstream = null;
    public static Scene scene = null;
    public static Stage stage = null;

    @Override

    public void start(Stage stage) throws IOException, InterruptedException {
        Client.stage = stage;

        var loader = FXMLHelpers.makeLoader("loading");
        var parent = (Parent)loader.load();

        scene = new Scene(parent, 1280, 960);
        stage.setScene(scene);
        stage.show();

        var controller = (LoadingController)loader.getController();

        var loadingThread = new Thread(() -> {
            try {
                controller.setup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        loadingThread.start();
    }

    public static void main(String[] args) {
        launch();
    }

}

