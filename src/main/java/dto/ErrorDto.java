package dto;

import lombok.*;

//{
//        "code": 0,
//        "details": "string",
//        "message": "string",
//        "timestamp": "2022-06-10T09:03:25.923Z"
//        }
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ErrorDto {
    int code;
    String details;
    String message;
}
