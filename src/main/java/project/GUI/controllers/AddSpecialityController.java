package project.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import project.model.Speciality;

public class AddSpecialityController extends AdminSubController {
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField ratio5Field;
    @FXML private TextField ratio6Field;
    @FXML private TextField ratio7Field;
    @FXML private TextField ratio8Field;
    @FXML private TextField ratio9Field;

    @FXML
    private void addSpeciality() {
        var alert = new Alert(AlertType.ERROR);

        if (
                idField.getText().isBlank() ||
                        nameField.getText().isBlank() ||
                        ratio5Field.getText().isBlank() ||
                        ratio6Field.getText().isBlank() ||
                        ratio7Field.getText().isBlank() ||
                        ratio8Field.getText().isBlank() ||
                        ratio9Field.getText().isBlank()
        ) {
            alert.setHeaderText("Заполните все поля");
            alert.show();
            return;
        }

        int id;

        try {
            id = Integer.valueOf(idField.getText().strip());

            if (id < 0) {
                alert.setHeaderText("ID должно быть положительным");
                alert.show();
                return;
            }
        } catch (Exception e) {
            alert.setHeaderText("ID должно быть числом");
            alert.show();
            return;
        }

        float ratio5, ratio6, ratio7, ratio8, ratio9;

        try {
            ratio5 = Float.parseFloat(ratio5Field.getText().strip());
            ratio6 = Float.parseFloat(ratio6Field.getText().strip());
            ratio7 = Float.parseFloat(ratio7Field.getText().strip());
            ratio8 = Float.parseFloat(ratio8Field.getText().strip());
            ratio9 = Float.parseFloat(ratio9Field.getText().strip());

            if (ratio5 < 0 || ratio6 < 0 || ratio7 < 0 || ratio8 < 0 || ratio9 < 0) {
                alert.setHeaderText("Множители должны быть положительными");
                alert.show();
                return;
            }
        } catch (Exception e) {
            alert.setHeaderText("Множитедт должны быть числами");
            alert.show();
            return;
        }

        var speciality = Speciality.builder()
                .id(id)
                .name(nameField.getText().strip())
                .ratio5(ratio5)
                .ratio6(ratio6)
                .ratio7(ratio7)
                .ratio8(ratio8)
                .ratio9(ratio9)
                .build();
        adminMenuController.addModel(speciality);
    }
}
