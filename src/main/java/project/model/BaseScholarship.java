package project.model;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "base_scholarship")
public class BaseScholarship implements Model{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "value")
    private float value;

    public BaseScholarship() {
    }

    @Override
    public ModelType getModelType() {
        return ModelType.BaseScholarship;
    }

}
