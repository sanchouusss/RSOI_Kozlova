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
@Table(name = "students")
public class Student implements Serializable, Model, Identifiable{

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private Set<Performance> performance = new HashSet<>();


    @OneToOne
    @JoinColumn(name = "id")
    @ToString.Exclude
    private SpecialScholarship specialScholarship;


    @ManyToOne
    @JoinColumn(name = "specialities_id")
    @ToString.Exclude
    private Speciality speciality;

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "education_form")
    @Enumerated(EnumType.STRING)
    private EducationForm educationForm;


    @Transient
    private int specialityId;

    @Override
    public ModelType getModelType() {
        return ModelType.Student;
    }
}
