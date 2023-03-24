package project;

import lombok.Builder;
import lombok.Getter;
import project.model.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
public class ModelList implements Serializable {

        private List<Student> students;
        private List<User> users;
        private List<SpecialScholarship> specialScholarships;
        private List<Performance> performances;
        private List<Speciality> specialities;
        private List<Subject> subjects;
        private BaseScholarship baseScholarship;
    }
