package dto;

import lombok.*;

//{
//        "email": "string",
//        "password": "string"
//        }
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AuthRequestDto {
    String email;
    String password;
}
