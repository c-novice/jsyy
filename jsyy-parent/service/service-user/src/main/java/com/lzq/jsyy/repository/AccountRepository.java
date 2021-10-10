package com.lzq.jsyy.repository;

import com.lzq.jsyy.model.user.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author lzq
 */
public interface AccountRepository extends MongoRepository<Account, String> {
    /**
     * 根据学号、密码查询校园信息
     *
     * @param studentNumber
     * @param password
     * @return
     */
    Account getAccountByStudentNumberAndPassword(String studentNumber, String password);
}
