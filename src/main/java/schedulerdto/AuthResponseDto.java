package schedulerdto;

import lombok.*;

//{
//        "registration": true,
//        "status": "string",
//        "token": "string"
//        }
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AuthResponseDto {
    boolean registration;
    String status;
    String token;
}
