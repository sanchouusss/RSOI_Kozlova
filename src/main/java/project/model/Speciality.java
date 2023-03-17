package project.model;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@Table(name = "specialities")
public class Speciality implements Model {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "ratio_5")
    private float ratio_5;

    @Column(name = "ratio_6")
    private float ratio_6;

    @Column(name = "ratio_7")
    private float ratio_7;

    @Column(name = "ratio_8")
    private float ratio_8;

    @Column(name = "ratio_9")
    private float ratio_9;

    public Speciality() {

    }

    @Override
    public ModelType getModelType() {
        return ModelType.Speciality;
    }
}
