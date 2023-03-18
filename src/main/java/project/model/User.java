package project.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements Model{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //генерация первичного ключа автоматически при добавлении
    @Column(name = "id") // новой записи в таблицу
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING) //значения перечисления будут храниться в базе данных в виде строковых значений,
    private UserRole role; // которые соответствуют именам элементов перечисления


    @Override
    public ModelType getModelType() {
        return ModelType.User;
    }

}
