package kr.pwner.fakegram.repository;

import kr.pwner.fakegram.model.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadRepository extends JpaRepository<Upload, Long> {
}
