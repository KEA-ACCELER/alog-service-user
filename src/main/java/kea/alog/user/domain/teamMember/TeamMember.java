package kea.alog.user.domain.teamMember;
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import kea.alog.user.domain.BaseTimeEntity;
import kea.alog.user.domain.team.Team;
import kea.alog.user.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Component
@Entity
@Table(name = "team_member")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class TeamMember extends BaseTimeEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_member_pk")
    private Long teamMemberPk;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team")
    private Team team;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;

    
    @Builder
    public TeamMember(Team team, User user){
        this.team = team;
        this.user = user;
    }
}
