package project.GUI.controllers;

import java.io.IOException;
import java.util.List;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import project.Client;
import project.GUI.dialogs.ChangeInfoDialog;
import project.GUI.dialogs.ChangePasswordDialog;
import project.net.*;
import project.util.FXMLHelpers;
import project.util.InitializeTables;

public class StudentMenuController {
    @FXML private TabPane tabPane;

    @FXML private Label fullNameLabel;
    @FXML private Label phoneLabel;
    @FXML private Label addressLabel;
    @FXML private Label emailLabel;

    @FXML private TableView<List<Object>> performanceTable;

    private Runnable updateInfoTask;
    private StudentModelList modelList;

    @FXML
    private void initialize() {
        InitializeTables.addCellFactories(performanceTable);

        updateInfoTask = () -> {
            Platform.runLater(() -> tabPane.setDisable(true));

            try {
                modelList = (StudentModelList) Client.inputstream.readObject();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
                Platform.runLater(() -> FXMLHelpers.onConnectionLost());
                return;
            }

            Platform.runLater(() -> {
                fullNameLabel.setText("%s %s %s".formatted(
                        modelList.getStudent().getLastName(),
                        modelList.getStudent().getFirstName(),
                        modelList.getStudent().getPatronymic()
                ));
                phoneLabel.setText(modelList.getStudent().getPhone());
                addressLabel.setText(modelList.getStudent().getAddress());
                emailLabel.setText(modelList.getStudent().getEmail());
                InitializeTables.performanceForStudent(modelList.getPerformance(), performanceTable);

                tabPane.setDisable(false);
            });
        };

        new Thread(updateInfoTask).start();
    }

    @FXML
    private void changeInfo() throws ClassNotFoundException, IOException {
        var dialog = new ChangeInfoDialog(modelList.getStudent());

        var answer = dialog.showAndWait();

        if (answer.isPresent()) {
            sendMessage(answer.get(), StudentOperationsType.changeInfo);
        }
    }

    @FXML
    private void changePassword() throws IOException, ClassNotFoundException {
        var dialog = new ChangePasswordDialog(modelList.getUser());

        var answer = dialog.showAndWait();

        if (answer.isPresent()) {
            sendMessage(answer.get(), StudentOperationsType.changePassword);
        }
    }

    @FXML
    private void calculateScholarship() {
        float scholarship;

        try {
            Client.outputstream.writeObject(StudentMessage.builder()
                    .operationType(StudentOperationsType.calculateScholarship)
                    .build()
            );

            Client.outputstream.flush();

            scholarship = Client.inputstream.readFloat();
        } catch (IOException e) {
            e.printStackTrace();
            FXMLHelpers.onConnectionLost();
            return;
        }

        if (scholarship < 0) {
            FXMLHelpers.onConnectionLost();
            return;
        }

        var alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setHeaderText("Итоговая стипендия: " + scholarship);
        alert.show();

        new Thread(updateInfoTask).start();
    }

    private void sendMessage(Object value, StudentOperationsType operationType){
        try {
            Client.outputstream.writeObject(StudentMessage.builder()
                    .operationType(operationType)
                    .value(value)
                    .build());
            Client.outputstream.flush();

            var message = (ServerMessage)Client.inputstream.readObject();

            if (message.getAnswerType() == AnswerType.Failure) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(message.getMessage());
                alert.show();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            FXMLHelpers.onConnectionLost();
            return;
        }



        new Thread(updateInfoTask).start();
    }
}

