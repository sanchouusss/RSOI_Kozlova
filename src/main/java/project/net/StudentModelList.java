package project.net;

import lombok.Builder;
import lombok.Data;
import project.model.Performance;
import project.model.Student;
import project.model.User;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class StudentModelList implements Serializable {
        private Student student;
        private User user;
        private List<Performance> performance;
    }

