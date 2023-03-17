package project.model;


import lombok.Builder;

import javax.persistence.Table;

@Builder
@Table(name = "special_scholarship")
public class SpecialScholarship implements Model{


    @Override
    public ModelType getModelType() {
        return ModelType.SpecialScholarship;
    }
}
