package study.springdatajpa.entity;

import lombok.*;
import net.bytebuddy.asm.Advice;

import javax.annotation.processing.Generated;
import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","username","age"}) // 연관관계 필드인 team은 추가 하지않는 것이 좋다.
@NamedQuery(
        name ="Member.findByUsername",
        query ="select m from Member m where m.username= :username"
)
public class Member extends BaseEntity{

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY) // 모든 연관관계는 LAZY로 셋팅하자!
    @JoinColumn(name="team_id")
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }
    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if(team != null) {
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
