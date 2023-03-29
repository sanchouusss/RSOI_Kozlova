package project.GUI.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import project.*;
import project.GUI.dialogs.*;
import project.model.*;
import project.net.*;
import project.util.CastHelpers;
import project.util.FXMLHelpers;
import project.util.InitializeTables;

public class AdminMenuController {

    @FXML private TabPane tabPane;
    @FXML private TitledPane addStudentPane;
    @FXML private TitledPane addSpecialityPane;
    @FXML private TitledPane addSubjectPane;
    @FXML private AnchorPane addAdminPane;

    @FXML private TableView<List<Object>> studentsTable;
    @FXML private TableView<List<Object>> performanceTable;
    @FXML private TableView<List<Object>> specialitiesTable;
    @FXML private TableView<List<Object>> subjectsTable;
    @FXML private TableView<List<Object>> userStudentTable;
    @FXML private TableView<List<Object>> userAdminTable;
    @FXML private TableView<List<Object>> subjectsOfSpecialitiesTable;
    @FXML private Label socialLabel;
    @FXML private Label presidentLabel;
    @FXML private Label specialLabel;
    @FXML private Label baseLabel;
    private ModelList modelBundle;
    private List<Speciality_Subject> subjectsOfSpeciality = new ArrayList<>();
    private SpecialScholarship currentSpecialScholarship = null;

    private ContextMenu contextMenu;
    private MenuItem removeItem;
    private MenuItem changeItem;
    private MenuItem addSubjectItem;
    private ChoiceDialog<SubjectForSpeciality> addSubjectDialog;

    private StudentChangeDialog studentChangeDialog = new StudentChangeDialog();
    private PerformanceChangeDialog performanceChangeDialog = new PerformanceChangeDialog();
    private SpecialScholarshipChangeDialog scholarshipChangeDialog = new SpecialScholarshipChangeDialog();
    private SpecialityChangeDialog specialityChangeDialog = new SpecialityChangeDialog();
    private SubjectChangeDialog subjectChangeDialog = new SubjectChangeDialog();

    private Runnable updateTablesTask;

    @FXML
    private void initialize() throws ClassNotFoundException, IOException {

        initializeSubcontroller("addstudentmenu", addStudentPane);
        initializeSubcontroller("addspecialitymenu", addSpecialityPane);
        initializeSubcontroller("addsubjectmenu", addSubjectPane);

        var addAdminLoader = FXMLHelpers.makeLoader("addadminmenu");
        addAdminPane.getChildren().add((Pane)addAdminLoader.load());
        var addAdminController = (AdminSubController)addAdminLoader.getController();
        addAdminController.setAdminMenuController(this);

        InitializeTables.addCellFactories(studentsTable);
        InitializeTables.addCellFactories(performanceTable);
        InitializeTables.addCellFactories(specialitiesTable);
        InitializeTables.addCellFactories(subjectsTable);
        InitializeTables.addCellFactories(userStudentTable);
        InitializeTables.addCellFactories(userAdminTable);
        InitializeTables.addCellFactories(subjectsOfSpecialitiesTable);

        contextMenu = new ContextMenu();
        removeItem = new MenuItem("Удалить");
        changeItem = new MenuItem("Изменить");
        addSubjectItem = new MenuItem("Добавить предмет");
        addSubjectItem.setVisible(false);
        contextMenu.getItems().addAll(removeItem, changeItem, addSubjectItem);

        addSubjectDialog = new ChoiceDialog<>();
        addSubjectDialog.setHeaderText("Выберите предмет");

        updateTablesTask = () -> {
            Platform.runLater(() -> tabPane.setDisable(true));

            try {
                modelBundle = (ModelList) Client.inputstream.readObject();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
                Platform.runLater(() -> FXMLHelpers.onConnectionLost());
                return;
            }

            Platform.runLater(() -> {
                InitializeTables.students(modelBundle.getStudents(), studentsTable);
                InitializeTables.specialities(modelBundle.getSpecialities(), specialitiesTable);
                InitializeTables.subjects(modelBundle.getSubjects(), subjectsTable);
                InitializeTables.users(modelBundle.getUsers(), userStudentTable, userAdminTable);
                showSubjectsOfSpeciality(null);
                showPerformance(null);
                baseLabel.setText(String.valueOf(modelBundle.getBaseScholarship().getValue()));

                tabPane.setDisable(false);
            });

        };

        new Thread(updateTablesTask).start();
    }

    private void initializeSubcontroller(String fxml, TitledPane addPane) throws IOException {
        var loader = FXMLHelpers.makeLoader(fxml);
        addPane.setContent((Pane)loader.load());
        var controller = (AdminSubController)loader.getController();
        controller.setAdminMenuController(this);
    }

    @FXML
    private void onStudentsTableClick(MouseEvent event) {
        onTableClick(event, studentsTable, modelBundle.getStudents());

        int selectedIndex = studentsTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            changeItem.setOnAction(e -> changeModel(selectedIndex, studentsTable, modelBundle.getStudents(), studentChangeDialog));
        }
    }

    @FXML
    private void onSpecialitiesTableClick(MouseEvent event) {
        onTableClick(event, specialitiesTable, modelBundle.getSpecialities());

        int selectedIndex = specialitiesTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            addSubjectItem.setVisible(true);

            addSubjectItem.setOnAction(e -> addSubjectToSpeciality(selectedIndex));
            changeItem.setOnAction(e -> changeModel(selectedIndex, specialitiesTable, modelBundle.getSpecialities(), specialityChangeDialog));
        }
    }

    private void addSubjectToSpeciality(int selectedIndex) {
        if (modelBundle.getSubjects().isEmpty()) {
            var alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("В системе нет предметов");
            alert.show();

            return;
        }

        var subjects = CastHelpers.toSubjectForSpeciality(modelBundle.getSubjects());
        addSubjectDialog.getItems().clear();
        addSubjectDialog.getItems().addAll(subjects);
        addSubjectDialog.setSelectedItem(subjects.get(0));

        var answer = addSubjectDialog.showAndWait();

        if (answer.isPresent()) {
            int specialityId = (Integer)specialitiesTable.getItems().get(selectedIndex).get(0);

            addModel(Speciality_Subject.builder()
                    .specialityId(specialityId)
                    .subjectId(answer.get().getSubject().getId())
                    .build()
            );
        }
    }

    @FXML
    private void onPerformanceTableClick(MouseEvent event) {
        onTableClick(event, performanceTable, null);

        removeItem.setVisible(false);

        int selectedIndex = performanceTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            changeItem.setOnAction(e -> changeModel(selectedIndex, performanceTable, modelBundle.getPerformances(), performanceChangeDialog));
        }
    }

    @FXML
    private void onSubjectsForSpecialityTableClick(MouseEvent event) {

        if (!subjectsOfSpeciality.isEmpty()) {
            onTableClick(event, subjectsOfSpecialitiesTable, subjectsOfSpeciality);
            changeItem.setVisible(false);
        }
    }

    @FXML
    private void onSubjectsTableClick(MouseEvent event) {
        onTableClick(event, subjectsTable, modelBundle.getSubjects());

        int selectedIndex = subjectsTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            changeItem.setOnAction(e -> changeModel(selectedIndex, subjectsTable, modelBundle.getSubjects(), subjectChangeDialog));
        }
    }

    @FXML
    private void onAdminsTableClick(MouseEvent event) {
        onTableClick(event, userAdminTable, modelBundle.getUsers());
        changeItem.setVisible(false);
    }

    @FXML
    private void showSubjectsOfSpeciality(MouseEvent event) {
        int selectedIndex = specialitiesTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            int id = (Integer)specialitiesTable.getItems().get(selectedIndex).get(0);

            var speciality = modelBundle.getSpecialities()
                    .stream()
                    .filter(s -> s.getId() == id)
                    .collect(Collectors.toList())
                    .get(0);

            InitializeTables.subjects(speciality.getSubjects(), subjectsOfSpecialitiesTable);

            subjectsOfSpeciality.clear();

            for (var subject : speciality.getSubjects()) {
                subjectsOfSpeciality.add(Speciality_Subject.builder().specialityId(id).subjectId(subject.getId()).build());
            }
        } else {
            subjectsOfSpecialitiesTable.getItems().clear();
        }
    }

    @FXML
    private void showPerformance(MouseEvent event) {
        socialLabel.setText("");
        presidentLabel.setText("");
        specialLabel.setText("");

        currentSpecialScholarship = null;

        int selectedIndex = studentsTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            int id = (Integer)studentsTable.getItems().get(selectedIndex).get(0);

            var student = modelBundle.getStudents()
                    .stream()
                    .filter(s -> s.getId() == id)
                    .toList()
                    .get(0);

            InitializeTables.performance(student.getPerformance(), performanceTable);

            currentSpecialScholarship = student.getSpecialScholarship();

            socialLabel.setText(String.valueOf(currentSpecialScholarship.getSocial()));
            presidentLabel.setText(String.valueOf(currentSpecialScholarship.getPresident()));
            specialLabel.setText(String.valueOf(currentSpecialScholarship.getSpecial()));
        } else {
            performanceTable.getItems().clear();
        }
    }

    private <T extends Identifiable & Model> void onTableClick(MouseEvent event, TableView<List<Object>> table, List<T> identifiables) {
        changeItem.setVisible(true);
        addSubjectItem.setVisible(false);

        if (contextMenu.isShowing()) {
            contextMenu.hide();
        }

        if (table.getSelectionModel().getSelectedIndex() < 0)
            return;

        if (event.getButton() == MouseButton.SECONDARY) {

            if (identifiables != null) {
                removeItem.setVisible(true);

                int selectedIndex = table.getSelectionModel().getSelectedIndex();
                int id = (Integer)table.getItems().get(selectedIndex).get(0);

                removeItem.setOnAction(e -> removeModel(
                        identifiables.stream()
                                .filter(s -> s.getId() == id)
                                .collect(Collectors.toList())
                                .get(0)
                ));
            }
            contextMenu.show(table, event.getScreenX(), event.getScreenY());
        }
    }

    private <T extends Identifiable & Model> void changeModel(int selectedIndex, TableView<List<Object>> table, List<T> models, ChangeDialog<T> dialog) {
        int id = (Integer)table.getItems().get(selectedIndex).get(0);

        var model = models.stream()
                .filter(p -> p.getId() == id)
                .toList()
                .get(0);

        dialog.setChangeableValue(model);

        var answer = dialog.showAndWait();

        if (answer.isPresent()) {
            updateModel(answer.get());
        }
    }

    @FXML
    private void changeSpecialScholarship() {
        if (currentSpecialScholarship == null) {
            return;
        }

        scholarshipChangeDialog.setChangeableValue(currentSpecialScholarship);
        var answer = scholarshipChangeDialog.showAndWait();

        if (answer.isPresent()) {
            updateModel(answer.get());
        }
    }

    @FXML
    private void changeBaseScholarship() {
        var dialog = new TextInputDialog();
        dialog.setHeaderText("Введите новую базовую стипендию");

        var answer = dialog.showAndWait();

        if (answer.isPresent()) {
            try {
                float newBase = Float.valueOf(answer.get());

                if (newBase < 0) {
                    var alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Стипендия должна быть положительным числом");
                    alert.show();
                }

                modelBundle.getBaseScholarship().setValue(newBase);
                updateModel(modelBundle.getBaseScholarship());
            } catch (Exception e) {
                var alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Стипендия должна быть числом");
                alert.show();
            }
        }
    }

    private void actionWithModel(Model model, AdminOperationsType operationType) {
        var alert = new Alert(AlertType.ERROR);

        try {
            Client.outputstream.writeObject(ClientMessage.builder()
                    .operationType(operationType)
                    .value(model)
                    .build()
            );
            Client.outputstream.flush();

            var message = (ServerMessage)Client.inputstream.readObject();

            if (message.getAnswerType() == AnswerType.Failure) {
                alert.setHeaderText(message.getMessage());
                alert.show();
            }

            new Thread(updateTablesTask).start();
        } catch (Exception e) {
            e.printStackTrace();
            FXMLHelpers.onConnectionLost();
        }
    }

    void addModel(Model model) {
        actionWithModel(model, AdminOperationsType.Add);
    }

    void removeModel(Model model) {
        actionWithModel(model, AdminOperationsType.Remove);
    }

    void updateModel(Model model) {
        actionWithModel(model, AdminOperationsType.Update);
    }
}
