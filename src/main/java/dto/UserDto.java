package dto;

import lombok.Data;
import org.sda.finalbackend.entity.UserRole;

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private UserRole userRole;
}
