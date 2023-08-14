package kea.alog.user.domain.team;
import java.beans.JavaBean;
import java.io.Serializable;

import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import kea.alog.user.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "team")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString 
@Component
public class Team extends BaseTimeEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_pk")
    private Long teamPk;

    @Column(name = "team_name", length = 20)
    private String teamName;

    @Setter
    @Column(name = "team_image", length = 500)
    private String teamImage;

    @Column(name = "team_leader_pk")
    private Long teamLeaderPk;

    @Builder
    public Team (String teamName, Long teamLeaderPk, String teamImage){
        this.teamName = teamName;
        this.teamLeaderPk = teamLeaderPk;
        this.teamImage = teamImage;
    }
}
