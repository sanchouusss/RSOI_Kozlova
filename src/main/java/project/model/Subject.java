package project.model;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@Table(name = "subjects")
public class Subject implements Model{

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "hours")
    private int hours;

    public Subject() {

    }


    @Override
    public ModelType getModelType() {
        return ModelType.Subject;
    }
}
