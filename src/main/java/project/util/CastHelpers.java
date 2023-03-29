package project.util;


import project.model.Identifiable;
import project.model.Subject;
import project.model.SubjectForSpeciality;

import java.util.List;


public class CastHelpers {

    public static <T> List<Identifiable> toIdentifiables(List<T> objects) {
        return objects.stream().map(Identifiable.class::cast).toList();
    }

    public static List<SubjectForSpeciality> toSubjectForSpeciality(List<Subject> subjects) {
        return subjects.stream().map(s -> new SubjectForSpeciality(s)).toList();
    }
}

