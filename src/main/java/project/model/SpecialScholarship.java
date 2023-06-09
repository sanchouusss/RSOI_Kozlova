package project.model;


import lombok.*;


import jakarta.persistence.*;

import java.io.Serializable;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "special_scholarship")
public class SpecialScholarship implements Serializable, Model{

    @OneToOne
    @JoinColumn(name = "id")
    @ToString.Exclude
    private Student student;

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "social_scholarship")
    private float social;

    @Column(name = "president_scholarship")
    private float president;

    @Column(name = "special_scholarship")
    private float special;

    @Override
    public ModelType getModelType() {
        return ModelType.SpecialScholarship;
    }
}
