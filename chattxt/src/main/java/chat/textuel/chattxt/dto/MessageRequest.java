package chat.textuel.chattxt.dto;

import lombok.*;

@Getter
@Setter
public class MessageRequest {
    private Integer authorId;
    private Integer conversationId;
    private String message;
}
