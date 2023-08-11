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
import lombok.Setter;

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

    // @Column(name = "user_id", length = 30, nullable = false, unique = true)
    // private String userId;

    @Column(name = "user_pw", length = 100, nullable = false)
    private String userPw;

    @Column(name = "user_nn", length = 10, nullable = false)
    private String userNn;


    @Column(name = "user_email", length = 50, nullable = false)
    private String userEmail;

    @Setter
    @Getter
    @Column(name = "user_deleted")
    private boolean userDeleted;

    @Setter
    @Column(name = "user_profile", length = 100)
    private String userProfile;

    // @Column(name = "user_role", length = 10)
    // private String userRole;

    @Setter
    @Column(name = "is_email_verified", length = 10)
    private boolean isVerified;


    @Builder
    public User( String userPw, String userNn, String userEmail, boolean userDeleted, String userProfile, boolean isVerified){
        this.userPw = userPw;
        this.userNn = userNn;
        this.userEmail = userEmail;
        this.userDeleted = userDeleted;
        this.userProfile = userProfile;
        this.isVerified = isVerified;
    }
}
