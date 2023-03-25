package project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subjects")
public class Subject implements Serializable, Model{

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
