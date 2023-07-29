package kr.pwner.fakegram.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Feed {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idx;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_IDX")
    private Account account;

    @Builder
    public Feed(String content){
        this.content = content;
    }

    public void LinkAccount(Account account){
        account.getFeeds().add(this);
        this.account = account;
    }
}
