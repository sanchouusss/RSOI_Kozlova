package project.model;


import lombok.Builder;

import javax.persistence.Table;


@Builder
@Table(name = "performance")
public class Performance implements  Model{



    @Override
    public ModelType getModelType() {
        return ModelType.Performance;
    }
}
