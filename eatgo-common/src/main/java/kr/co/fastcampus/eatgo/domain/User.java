package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long Id;

    @Setter
    @NotEmpty
    private String email;

    @Setter
    @NotEmpty
    private String name;

    @Setter
    @NotNull
    private Long level;

    private String password;

    public boolean isAdmin(){
        return level >= 3;
    }

    public boolean isActive() {
        return level > 0L;
    }

    public void deactivate() {
        level = 0L;
    }

    // 실제에선 이렇게 안하는 것 같다... 알아보자
    @JsonIgnore
    public String getAccessToken() {
        if(password == null){
            return "";
        }
        return password.substring(0, 11);
    }
}
