package com.ddangnmarket.ddangmarkgetbackend.domain.file;

import com.ddangnmarket.ddangmarkgetbackend.domain.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author SeunghyunYoo
 */
public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {

    Optional<UploadFile> findByStoreFileName(String storeFileName);

    @Query("select u from UploadFile u" +
            " where u.storeFileName in :storeFileNames")
    List<UploadFile> findAllByStoreFileNames(@Param("storeFileNames")List<String> storeFileNames);

    @Query("select u from UploadFile u" +
            " where u.id in :ids")
    List<UploadFile> findAllByIds(@Param("ids") List<Long> ids);

    Optional<UploadFile> findById(Long id);
}
