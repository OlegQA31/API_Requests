package schedulerdto;
//{
//        "breaks": 0,
//        "currency": "string",
//        "date": {
//        "dayOfMonth": 0,
//        "dayOfWeek": "string",
//        "month": 0,
//        "year": 0
//        },
//        "hours": 0,
//        "id": 0,
//        "timeFrom": "string",
//        "timeTo": "string",
//        "title": "string",
//        "totalSalary": 0,
//        "type": "string",
//        "wage": 0
//        }

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RecordsDto {
    int breaks;
    String currency;
    DateDto date;
    int hours;
    int id;
    String timeFrom;
    String timeTo;
    String title;
    int totalSalary;
    String type;
    int wage;
}
