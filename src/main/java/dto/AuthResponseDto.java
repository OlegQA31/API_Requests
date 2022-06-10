package dto;

import lombok.*;

//{
//        "token": "string"
//        }
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AuthResponseDto {
    String token;
}
