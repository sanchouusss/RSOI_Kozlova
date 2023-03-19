package project.server;

import project.DAO.ScholarshipDAO;
import project.model.BaseScholarship;
import project.model.EducationForm;
import project.model.Student;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class ServerStudentOperations implements Runnable{


    private ObjectOutputStream outputstream;
    private ObjectInputStream inputstream;
    private int studentId;


    public ServerStudentOperations(ObjectOutputStream outputstream, ObjectInputStream inputstream, int studentId) throws IOException
    {
        this.outputstream = outputstream;
        this.inputstream = inputstream;
        this.studentId = studentId;
    }

    private void calculateScholarship() throws IOException {
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

    @Override
    public void run() {

    }
}
