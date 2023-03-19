package project.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "special_scholarship")
public class SpecialScholarship implements Model{

    @OneToOne
    @JoinColumn(name = "id")
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
