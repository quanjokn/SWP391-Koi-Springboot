package SWP391.Fall24.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaredKoiDTO {
    private String name;
    private String sex;
    private String age;
    private String size;
    private String healthStatus;
    private String ration;
    private String photo;
}
