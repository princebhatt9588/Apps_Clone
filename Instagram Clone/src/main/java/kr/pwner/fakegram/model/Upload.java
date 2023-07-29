package kr.pwner.fakegram.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Upload {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idx;

    @Column(nullable = false)
    private String fileName;

    // * Relation Mapping
    // ? feed relation

//    // * Methods
//    @Builder
//    public Upload(Account account, String fileName){
//        this.account = account
//        this.fileName = fileName;
//    }
}