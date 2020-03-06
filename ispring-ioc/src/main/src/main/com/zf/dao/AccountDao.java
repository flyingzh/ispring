package com.zf.dao;


import com.zf.pojo.Account;

/**
 * zf
 */
public interface AccountDao {

    Account queryAccountByCardNo(String cardNo) throws Exception;

    int updateAccountByCardNo(Account account) throws Exception;
}
