package com.imatrix.files.upload.db.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imatrix.files.upload.db.model.FileDB;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
	@Query(value = "select *  from tblfile where order_no =:order_no and com_guid=:com_guid and active_status=1", nativeQuery = true)
	FileDB getFileByOrderGuid(@Param("order_no") String order_no, @Param("com_guid") String com_guid);
	
	@Query(value = "select *  from tblfile where id =:id and active_status=1", nativeQuery = true)
	FileDB getFileById(@Param("id") Long id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE tblfile set active_status=0 where id=:id", nativeQuery = true)
	int delFileByOrderGuid(@Param("id") Long id);
}
