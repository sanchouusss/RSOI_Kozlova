package project.model;

import lombok.*;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "specialities")
public class Speciality implements Serializable, Model, Identifiable {

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

    @ManyToMany(targetEntity = Subject.class, cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "speciality_subjects",
            inverseJoinColumns = { @JoinColumn(name = "subjects_id", referencedColumnName = "id") },
            joinColumns = { @JoinColumn(name = "speciality_id", referencedColumnName = "id") }
    )
    @Builder.Default
    @ToString.Exclude
    private Set<Subject> subjects = new HashSet<>();

    @Override
    public ModelType getModelType() {
        return ModelType.Speciality;
    }
}
