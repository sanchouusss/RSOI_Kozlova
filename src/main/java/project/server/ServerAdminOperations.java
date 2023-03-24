package project.server;

import lombok.AllArgsConstructor;
import project.AnswerType;
import project.ClientMessage;
import project.DAO.ScholarshipDAO;
import project.ModelList;
import project.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import project.model.*;


@AllArgsConstructor
public class ServerAdminOperations implements Runnable {


    private ObjectOutputStream outputstream;
    private ObjectInputStream inputstream;

    @Override
    public void run() {
        showInfo();

        while (true) {
            try {
                var message = (ClientMessage) inputstream.readObject();

                switch (message.getOperationType()) {
                    case Add -> {
                        switch (message.getValue().getModelType()) {
                          //  case Performance ->
                          //  case Student ->
                            case Speciality -> add(message, Speciality.class);
                            case Subject -> add(message, Subject.class);
                          //  case SpecialScholarship -> unimplementedOperation();
                            case User -> add(message, User.class);
                          //  case Speciality_Subject ->
                         //   case BaseScholarship ->
                        }
                    }
                    case Update -> {
                        switch (message.getValue().getModelType()) {
                            case Performance -> update(message, Performance.class);
                            case Student -> update(message, Student.class);
                            case Speciality -> update(message, Speciality.class);
                            case Subject -> update(message, Subject.class);
                            case SpecialScholarship -> update(message, SpecialScholarship.class);
                         //   case User ->
                         //   case Speciality_Subject ->
                            case BaseScholarship -> update(message, BaseScholarship.class);
                        }
                    }
                    case Remove -> {
                        switch (message.getValue().getModelType()) {
                          //  case Performance ->
                          //  case Student ->
                            case Speciality -> remove(message, Speciality.class, "Нельзя удалить специальность, пока на ней числятся студенты");
                            case Subject -> remove(message, Subject.class);
                          //  case SpecialScholarship ->
                            case User -> remove(message, User.class);
                          //  case Speciality_Subject ->
                          //  case BaseScholarship ->
                        }
                    }
                }

                showInfo();

            } catch (Exception e) {

                return;
            }
        }

    }


    private <T> void add(ClientMessage message, Class<T> type) throws IOException {
        var newValue = (T)message.getValue();

        try (var dao = new ScholarshipDAO<>(type)) {
            dao.add(newValue);
        } catch (Exception e) {
            outputstream.writeObject(ServerMessage.builder()
                    .answerType(AnswerType.Failure)
                    .message("Duplicate %s id".formatted(message.getValue().getModelType()))
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



    private <T> void update(ClientMessage message, Class<T> type) throws IOException {
        updateInfo(message, type, "Ошибка обновления " + message.getValue().getModelType());
    }


    private <T> void updateInfo(ClientMessage message, Class<T> type, String errorMessage) throws IOException {

        try (var dao = new ScholarshipDAO<>(type)) {
            dao.update((T)message.getValue());
        } catch (Exception e) {
            e.printStackTrace();

            outputstream.writeObject(ServerMessage.builder()
                    .answerType(AnswerType.Failure)
                    .message(errorMessage)
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

    private <T> void remove(ClientMessage message, Class<T> type) throws IOException {
        remove(message, type, "Ошибка удаления " + message.getValue().getModelType());
    }

    private <T> void remove(ClientMessage message, Class<T> type, String errorMessage) throws IOException {
        try (var dao = new ScholarshipDAO<>(type)) {
            var persistedValue = dao.findByUniqueColumn("id", ((Identifiable)message.getValue()).getId());

            dao.remove(persistedValue);
        } catch (Exception e) {
            e.printStackTrace();

            outputstream.writeObject(ServerMessage.builder()
                    .answerType(AnswerType.Failure)
                    .message(errorMessage)
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


    private void showInfo() {
        try (
                var studentDao = new ScholarshipDAO<>(Student.class);
                var performanceDao = new ScholarshipDAO<>(Performance.class);
                var specialScholarshipDao = new ScholarshipDAO<>(SpecialScholarship.class);
                var subjectDao = new ScholarshipDAO<>(Subject.class);
                var specialityDao = new ScholarshipDAO<>(Speciality.class);
                var userDao = new ScholarshipDAO<>(User.class);
                var baseScholarshipDao = new ScholarshipDAO<>(BaseScholarship.class)
        ) {
            var modelList = ModelList.builder()
                    .students(studentDao.selectAll())
                    .performances(performanceDao.selectAll())
                    .specialScholarships(specialScholarshipDao.selectAll())
                    .subjects(subjectDao.selectAll())
                    .specialities(specialityDao.selectAll())
                    .users(userDao.selectAll())
                    .baseScholarship(baseScholarshipDao.selectAll().get(0))
                    .build();

            for (var student : modelList.getStudents()) {
                student.getPerformance().forEach(p -> {});
            }
            outputstream.writeObject(modelList);
            outputstream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
