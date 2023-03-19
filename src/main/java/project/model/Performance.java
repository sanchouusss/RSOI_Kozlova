package project.model;


import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "performance")
public class Performance implements Model{

    @ManyToOne
    @JoinColumn(name = "students_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subjects_id")
    private Subject subject;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "total_score")
    private float totalScore;

    @Column(name = "missed_hours")
    private int missedHours;

    @Override
    public ModelType getModelType() {
        return ModelType.Performance;
    }
}
