package project.util;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import project.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InitializeTables {

        public static void addCellFactories(TableView<List<Object>> table) {
            for (int i = 0; i < table.getColumns().size(); i++) {
                getTables(table, i);
            }
        }


        private static void getTables(TableView<List<Object>> table, int columnIndex) {
            ((TableColumn<List<Object>, Object>)table.getColumns().get(columnIndex)).setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().get(columnIndex)));
        }

        public static void students(List<Student> students, TableView<List<Object>> table) {
            ObservableList<List<Object>> items = FXCollections.observableArrayList();

            for (var s : students) {
                var row = new ArrayList<Object>() {
                    {
                        add(s.getId());
                        add(s.getLastName());
                        add(s.getFirstName());
                        add(s.getPatronymic());
                        add(s.getPhone());
                        add(s.getAddress());
                        add(s.getEmail());
                        add(s.getEducationForm().toRussianString());
                        add(s.getSpeciality().getName());
                    }
                };

                items.add(row);
            }

            table.setItems(items);
        }

        public static void specialities(List<Speciality> specialities, TableView<List<Object>> table) {
            ObservableList<List<Object>> items = FXCollections.observableArrayList();

            for (var s : specialities) {
                var row = new ArrayList<Object>() {
                    {
                        add(s.getId());
                        add(s.getName());
                        add(s.getRatio5());
                        add(s.getRatio6());
                        add(s.getRatio7());
                        add(s.getRatio8());
                        add(s.getRatio9());
                    }
                };

                items.add(row);
            }

            table.setItems(items);
        }

        public static void subjects(Collection<Subject> subjects, TableView<List<Object>> table) {
            ObservableList<List<Object>> items = FXCollections.observableArrayList();

            for (var s : subjects) {
                var row = new ArrayList<Object>() {
                    {
                        add(s.getId());
                        add(s.getName());
                        add(s.getHours());
                    }
                };

                items.add(row);
            }

            table.setItems(items);
        }

        public static void users(List<User> users, TableView<List<Object>> userStudentTable, TableView<List<Object>> userAdminTable) {

            ObservableList<List<Object>> studentItems = FXCollections.observableArrayList();
            ObservableList<List<Object>> adminItems = FXCollections.observableArrayList();

            for (var s : users) {
                var row = new ArrayList<Object>() {
                    {
                        add(s.getId());
                        add(s.getLogin());
                    }
                };

                if (s.getRole() == UserRole.Administrator)
                    adminItems.add(row);
                else
                    studentItems.add(row);
            }

            userStudentTable.setItems(studentItems);
            userAdminTable.setItems(adminItems);
        }

        public static void performance(Collection<Performance> performances, TableView<List<Object>> table) {
            ObservableList<List<Object>> items = FXCollections.observableArrayList();

            for (var p : performances) {
                var row = new ArrayList<Object>() {
                    {
                        add(p.getId());
                        add(p.getSubject().getName());
                        add(p.getTotalScore());
                        add(p.getMissedHours());
                    }
                };

                items.add(row);
            }

            table.setItems(items);
        }


        public static void performanceForStudent(Collection<Performance> performances, TableView<List<Object>> table) {
            ObservableList<List<Object>> items = FXCollections.observableArrayList();

            for (var p : performances) {
                var row = new ArrayList<Object>() {
                    {
                        add(p.getSubject().getName());
                        add(p.getTotalScore());
                        add(p.getMissedHours());
                    }
                };

                items.add(row);
            }

            table.setItems(items);
        }
    }
