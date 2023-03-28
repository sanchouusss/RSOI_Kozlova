package project.GUI.dialogs;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import project.model.SpecialScholarship;

public class SpecialScholarshipChangeDialog extends ChangeDialog<SpecialScholarship> {

    private TextField socialField = new TextField();
    private TextField personalField = new TextField();
    private TextField namedField = new TextField();

    public SpecialScholarshipChangeDialog() {
        super();

        socialField.setPromptText("Social");
        personalField.setPromptText("Personal");
        namedField.setPromptText("Named");

        contentVbox.getChildren().addAll(socialField, personalField, namedField);

        setResultConverter(button -> {
            if (button.getButtonData() == ButtonData.CANCEL_CLOSE) {
                return null;
            }

            var alert = new Alert(AlertType.ERROR);

            if (
                    socialField.getText().isBlank() ||
                            personalField.getText().isBlank() ||
                            namedField.getText().isBlank()
            ) {
                alert.setHeaderText("Заполните все поля");
                alert.show();

                return null;
            }

            float social, president, special;

            try {
                social = Float.valueOf(socialField.getText());
                president = Float.valueOf(personalField.getText());
                special = Float.valueOf(namedField.getText());

                if (social < 0 || president < 0 || special < 0) {
                    alert.setHeaderText("Стипендия должна быть положительной");
                    alert.show();

                    return null;
                }
            } catch (Exception e) {
                alert.setHeaderText("Стипендия должна быть числом");
                alert.show();

                return null;
            }

            changeableValue.setSocial(social);
            changeableValue.setPresident(president);
            changeableValue.setSpecial(special);

            return changeableValue;
        });
    }

    @Override
    public void setChangeableValue(SpecialScholarship changeableValue) {
        super.setChangeableValue(changeableValue);

        socialField.setText(String.valueOf(changeableValue.getSocial()));
        personalField.setText(String.valueOf(changeableValue.getPresident()));
        namedField.setText(String.valueOf(changeableValue.getSpecial()));
    }
}

