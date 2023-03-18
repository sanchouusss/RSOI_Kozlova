package project.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "special_scholarship")
public class SpecialScholarship implements Model{


    @Override
    public ModelType getModelType() {
        return ModelType.SpecialScholarship;
    }
}
