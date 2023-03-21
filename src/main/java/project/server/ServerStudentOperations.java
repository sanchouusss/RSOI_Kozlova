package project.server;

import project.AnswerType;
import project.DAO.ScholarshipDAO;
import project.ServerMessage;
import project.StudentMessage;
import project.StudentModelList;
import project.model.BaseScholarship;
import project.model.EducationForm;
import project.model.Student;
import project.model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static project.StudentOperationsType.CalculateScholarship;


public class ServerStudentOperations implements Runnable {


    private ObjectOutputStream outputstream;
    private ObjectInputStream inputstream;
    private int userId;
    private int studentId;


    public ServerStudentOperations(ObjectOutputStream outputstream, ObjectInputStream inputstream, int userId, int studentId) throws IOException {
        this.outputstream = outputstream;
        this.inputstream = inputstream;
        this.userId = userId;
        this.studentId = studentId;
    }

    private void calculateScholarship(StudentMessage message) throws IOException {
        try (
                var studentDao = new ScholarshipDAO<>(Student.class);
                var baseScholarshipDao = new ScholarshipDAO<>(BaseScholarship.class)
        ) {
            var student = studentDao.findByUniqueColumn("id", studentId);
            float baseScholarship = baseScholarshipDao.selectAll().get(0).getValue();

            float scholarship = student.getSpecialScholarship().getSocial()
                    + student.getSpecialScholarship().getPresident()
                    + student.getSpecialScholarship().getSpecial();

            if (student.getEducationForm() == EducationForm.Paid) {
                outputstream.writeFloat(scholarship);
                outputstream.flush();

                return;
            }

            float sum = 0;
            int zeroes = 0;

            for (var performance : student.getPerformance()) {
                if (performance.getTotalScore() != 0f) {
                    sum += performance.getTotalScore();
                } else {
                    zeroes++;
                }
            }

            if (student.getPerformance().size() != zeroes) {
                float average = sum / (student.getPerformance().size() - zeroes);

                if (average >= 9) {
                    scholarship += baseScholarship * student.getSpeciality().getRatio9();
                } else if (average >= 8) {
                    scholarship += baseScholarship * student.getSpeciality().getRatio8();
                } else if (average >= 7) {
                    scholarship += baseScholarship * student.getSpeciality().getRatio7();
                } else if (average >= 6) {
                    scholarship += baseScholarship * student.getSpeciality().getRatio6();
                } else if (average >= 5) {
                    scholarship += baseScholarship * student.getSpeciality().getRatio5();
                }
            }

            outputstream.writeFloat(scholarship);
            outputstream.flush();

        } catch (Exception e) {
            e.printStackTrace();
            outputstream.writeFloat(-33);
            outputstream.flush();
        }


    }

    private void showInfo() {
        try (
                var studentDao = new ScholarshipDAO<>(Student.class);
                var userDao = new ScholarshipDAO<>(User.class);
        ) {
            var user = userDao.findByUniqueColumn("id", userId);
            var student = studentDao.findByUniqueColumn("id", studentId);

            var modelList = StudentModelList.builder()
                    .user(user)
                    .student(student)
                    .performance(student.getPerformance().stream().toList())
                    .build();
            outputstream.writeObject(modelList);
            outputstream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <T> void change(StudentMessage message, Class<T> type) throws IOException {
        var value = (T)message.getValue();

        try (var dao = new ScholarshipDAO<>(type)) {
            dao.update(value);
        } catch (Exception e) {
            outputstream.writeObject(ServerMessage.builder()
                    .answerType(AnswerType.Failure)
                    .message("Ошибка при изменении данных")
                    .build()
            );
            outputstream.flush();

            return;
        }

        outputstream.writeObject(ServerMessage.builder()
                .answerType(AnswerType.Success)
                .build()
        );
        outputstream.flush();
    }


    @Override
    public void run() {
        showInfo();

        while (true) {
            try {
                var message = (StudentMessage) inputstream.readObject();

                switch (message.getOperationType()) {
                    case changeInfo -> change(message, Student.class);
                    case changePassword -> change(message, User.class);
                    case CalculateScholarship -> calculateScholarship(message);
                }
                showInfo();
            } catch (Exception e) {

                return;

            }
        }
    }
}
