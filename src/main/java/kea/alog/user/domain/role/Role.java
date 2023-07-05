package kea.alog.user.domain.role;

import org.springframework.stereotype.Component;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Component
@Entity
@Table(name = "role")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Role {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "role_pk")
    private Long rolePk;

    @Column(name = "role_name", length = 30, nullable = false, unique = true)
    private String roleName;

    @Builder
    public Role(String roleName){
        this.roleName = roleName;
    }

}
