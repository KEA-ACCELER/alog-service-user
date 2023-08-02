package kea.alog.user.domain.email;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Component
@Entity
@Table(name = "email")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_pk")
    private Long emailPk;

    @Column(name = "email", length = 30, nullable = false)
    private String email;

    @Column(name = "verify_code", length = 10)
    private String verifyCode;

    @Builder
    public Email(String email, String verifyCode){
        this.email = email;
        this.verifyCode = verifyCode;
    }

}
