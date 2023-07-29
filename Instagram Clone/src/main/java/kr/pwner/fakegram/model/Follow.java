package kr.pwner.fakegram.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idx;

    @Column(nullable = false)
    private Long fromIdx;

    @Column(nullable = false)
    private Long toIdx;

    @Builder
    public Follow(Long fromIdx, Long toIdx) {
        this.fromIdx = fromIdx;
        this.toIdx = toIdx;
    }
}
