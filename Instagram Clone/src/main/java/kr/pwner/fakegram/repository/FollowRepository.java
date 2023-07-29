package kr.pwner.fakegram.repository;

import kr.pwner.fakegram.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findByFromIdxAndToIdx(Long fromIdx, Long toIdx);
    void deleteByIdx(Long idx);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM follow f " +
            "WHERE f.from_idx = :idx OR f.to_idx = :idx", nativeQuery = true)
    void deleteAllByIdx(@Param("idx") Long idx);

    @Query(value = "SELECT a.id, a.name, a.email " +
            "FROM account a " +
            "INNER JOIN follow f " +
            "ON f.from_idx = a.idx AND f.to_idx = :idx", nativeQuery = true)
    List<Map<String, String>> getFollowerByIdx(@Param("idx") Long idx);

    @Query(value = "SELECT a.id, a.name, a.email " +
            "FROM account a " +
            "INNER JOIN follow f " +
            "ON f.to_idx = a.idx AND f.from_idx = :idx", nativeQuery = true)
    List<Map<String, String>> getFollowingByIdx(@Param("idx") Long idx);
}