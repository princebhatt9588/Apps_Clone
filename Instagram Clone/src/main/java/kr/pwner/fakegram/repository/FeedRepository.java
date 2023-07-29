package kr.pwner.fakegram.repository;

import kr.pwner.fakegram.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Transactional
    void deleteByAccountIdx(Long AccountIdx);
}
