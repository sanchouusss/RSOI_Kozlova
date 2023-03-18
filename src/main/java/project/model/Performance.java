package project.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Table;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "performance")
public class Performance implements  Model{


    @Override
    public ModelType getModelType() {
        return ModelType.Performance;
    }
}
