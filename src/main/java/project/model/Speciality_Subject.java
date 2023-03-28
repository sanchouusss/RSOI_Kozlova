package project.model;


import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Speciality_Subject implements Serializable, Model, Identifiable {
    private int specialityId;
    private int subjectId;

    @Override
    public ModelType getModelType() {

        return ModelType.Speciality_Subject;
    }

    @Override
    public int getId() {
        return subjectId;
    }

}