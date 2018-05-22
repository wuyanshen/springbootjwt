package com.jwt.study.security_jwt.reponsitory;

import com.jwt.study.security_jwt.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MyUser,Long> {
    public abstract MyUser findByUsername(String username);

}
