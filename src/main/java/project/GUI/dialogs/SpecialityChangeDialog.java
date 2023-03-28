package project.GUI.dialogs;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import project.model.Speciality;

public class SpecialityChangeDialog extends ChangeDialog<Speciality> {

    private TextField nameField = new TextField();
    private TextField mult5Field = new TextField();
    private TextField mult6Field = new TextField();
    private TextField mult7Field = new TextField();
    private TextField mult8Field = new TextField();
    private TextField mult9Field = new TextField();

    public SpecialityChangeDialog() {
        nameField.setPromptText("Название");
        mult5Field.setPromptText("Множитель 5-5.9");
        mult6Field.setPromptText("Множитель 6-6.9");
        mult7Field.setPromptText("Множитель 7-7.9");
        mult8Field.setPromptText("Множитель 8-8.9");
        mult9Field.setPromptText("Множитель 9-10");

        contentVbox.getChildren().addAll(nameField, mult5Field, mult6Field, mult7Field, mult8Field, mult9Field);

        setResultConverter(button -> {
            if (button.getButtonData() == ButtonData.CANCEL_CLOSE) {
                return null;
            }

            var alert = new Alert(AlertType.ERROR);

            if (
                    nameField.getText().isBlank() ||
                            mult5Field.getText().isBlank() ||
                            mult6Field.getText().isBlank() ||
                            mult7Field.getText().isBlank() ||
                            mult8Field.getText().isBlank() ||
                            mult9Field.getText().isBlank()
            ) {
                alert.setHeaderText("Заполните все поля");
                alert.show();

                return null;
            }

            float ratio5, ratio6, ratio7, ratio8, ratio9;

            try {
                ratio5 = Float.parseFloat(mult5Field.getText().strip());
                ratio6 = Float.parseFloat(mult6Field.getText().strip());
                ratio7 = Float.parseFloat(mult7Field.getText().strip());
                ratio8 = Float.parseFloat(mult8Field.getText().strip());
                ratio9 = Float.parseFloat(mult9Field.getText().strip());

                if (ratio5 < 0 || ratio6 < 0 || ratio7 < 0 || ratio8 < 0 || ratio9 < 0) {
                    alert.setHeaderText("Множители должны быть положительными");
                    alert.show();

                    return null;
                }
            } catch (Exception e) {
                alert.setHeaderText("Множители должны быть числами");
                alert.show();

                return null;
            }

            changeableValue.setName(nameField.getText().strip());
            changeableValue.setRatio5(ratio5);
            changeableValue.setRatio6(ratio6);
            changeableValue.setRatio7(ratio7);
            changeableValue.setRatio8(ratio8);
            changeableValue.setRatio9(ratio9);

            return changeableValue;
        });
    }

    @Override
    public void setChangeableValue(Speciality changeableValue) {
        super.setChangeableValue(changeableValue);

        nameField.setText(changeableValue.getName());
        mult5Field.setText(String.valueOf(changeableValue.getRatio5()));
        mult6Field.setText(String.valueOf(changeableValue.getRatio6()));
        mult7Field.setText(String.valueOf(changeableValue.getRatio7()));
        mult8Field.setText(String.valueOf(changeableValue.getRatio8()));
        mult9Field.setText(String.valueOf(changeableValue.getRatio9()));
    }
}

