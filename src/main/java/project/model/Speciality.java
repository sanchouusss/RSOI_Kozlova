package project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.io.Serializable;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "specialities")
public class Speciality implements Serializable, Model {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "ratio_5")
    private float ratio5;

    @Column(name = "ratio_6")
    private float ratio6;

    @Column(name = "ratio_7")
    private float ratio7;

    @Column(name = "ratio_8")
    private float ratio8;

    @Column(name = "ratio_9")
    private float ratio9;


    @Override
    public ModelType getModelType() {
        return ModelType.Speciality;
    }
}
