package project.model;


import lombok.Builder;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "students")
public class Student implements Model{

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


    @Column(name = "specialities_id")
    private int specialityId;

    public Student() {

    }

    @Override
    public ModelType getModelType() {
        return ModelType.Student;
    }
}
