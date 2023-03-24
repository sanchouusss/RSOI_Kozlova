package project;


import lombok.Builder;
import lombok.Data;
import project.model.Model;

import java.io.Serializable;

@Data
@Builder
public class ClientMessage implements Serializable {
    private AdminOperationsType operationType;
    private Model value;
}

