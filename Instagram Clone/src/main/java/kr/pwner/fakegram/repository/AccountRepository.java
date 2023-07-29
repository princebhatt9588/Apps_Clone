package kr.pwner.fakegram.repository;

import kr.pwner.fakegram.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findById(String id);
    Account findByIdAndIsActivateTrue(String id);
    Account findByIdxAndIsActivateTrue(Long idx);

    @Transactional
    void deleteById(String id);
}
