package kea.alog.user.domain.user;
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import kea.alog.user.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Entity
@Table(name = "user")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class User extends BaseTimeEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_pk")
    private Long userPk;

    @Column(name = "user_id", length = 30, nullable = false, unique = true)
    private String userId;

    @Column(name = "user_pw", length = 100, nullable = false)
    private String userPw;

    @Column(name = "user_nn", length = 10, nullable = false)
    private String userNn;

    @Column(name = "user_email", length = 50, nullable = false)
    private String userEmail;

    @Column(name = "user_deleted")
    private boolean userDeleted;

    @Builder
    public User(String userId, String userPw, String userNn, String userEmail, boolean userDeleted){
        this.userId = userId;
        this.userPw = userPw;
        this.userNn = userNn;
        this.userEmail = userEmail;
        this.userDeleted = userDeleted;
    }
}