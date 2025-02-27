package mn.khosbilegt.service.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import mn.khosbilegt.jooq.generated.tables.records.PfUserRecord;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private String userId;
    private String username;
    private String email;
    private String password;
    private String type;
    private String role;

    public UserDTO() {}

    public UserDTO(PfUserRecord record) {
        this.userId = String.valueOf(record.getUserId());
        this.username = record.getUsername();
        this.email = record.getEmail();
        this.password = null;
        this.type = record.getType();
        this.role = record.getRole();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
