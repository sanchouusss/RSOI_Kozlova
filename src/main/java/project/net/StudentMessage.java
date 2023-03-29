package project.net;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class StudentMessage implements Serializable {
    private StudentOperationsType operationType;
    private Object value;
}
