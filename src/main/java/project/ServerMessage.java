package project;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ServerMessage implements Serializable {
    AnswerType answerType;
    String message;
}
