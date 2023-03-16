package project.model;
import javax.persistence.*;

@Entity
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

    public User() {
    }

    public User(int id, String login, String password, UserRole role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    @Override
    public ModelType getModelType() {
        return ModelType.User;
    }

}
