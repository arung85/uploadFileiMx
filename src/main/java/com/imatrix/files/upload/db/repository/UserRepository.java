package com.imatrix.files.upload.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imatrix.files.upload.db.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
