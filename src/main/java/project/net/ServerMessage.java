package project.net;

import lombok.Builder;
import lombok.Data;
import project.net.AnswerType;

import java.io.Serializable;

@Data
@Builder
public class ServerMessage implements Serializable {
    AnswerType answerType;
    String message;
}
