package project.GUI;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import project.model.Student;

public class ChangeInfoDialog extends ChangeDialog<Student> {
    @FXML private TextField phoneField = new TextField();
    @FXML private TextField emailField = new TextField();

    public ChangeInfoDialog(Student student) {
        super();

        phoneField.setPromptText("Номер телефона");
        emailField.setPromptText("Email");
        phoneField.setText(student.getPhone());
        emailField.setText(student.getEmail());

        contentVbox.getChildren().addAll(phoneField, emailField);

        setResultConverter(button -> {
            if (button.getButtonData() == ButtonData.CANCEL_CLOSE) {
                return null;
            }

            var alert = new Alert(AlertType.ERROR);

            if (
                    phoneField.getText().isBlank() ||
                            emailField.getText().isBlank()
            ) {
                alert.setHeaderText("Заполните все поля");
                alert.show();

                return null;
            }

            student.setPhone(phoneField.getText().strip());
            student.setEmail(emailField.getText().strip());

            return student;
        });
    }
}

