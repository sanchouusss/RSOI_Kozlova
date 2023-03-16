package project.model;

import javax.persistence.Column;
import javax.persistence.Id;

public class Subject implements Model{

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "hours")
    private int hours;



    @Override
    public ModelType getModelType() {
        return ModelType.Subject;
    }
}
