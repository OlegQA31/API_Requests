package dto;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class GetAllContactDto {
    List<ContactDto> contacts;
}
