package com.zf.service.impl;

import com.zf.annotation.Autowired;
import com.zf.annotation.Service;
import com.zf.annotation.Transaction;
import com.zf.dao.AccountDao;
import com.zf.dao.impl.JdbcAccountDaoImpl;
import com.zf.factory.BeanFactory;
import com.zf.pojo.Account;
import com.zf.service.TransferService;

/**
 * zf
 */
@Service
@Transaction
public class TransferServiceImpl implements TransferService {

    @Autowired
    private JdbcAccountDaoImpl jdbcAccountDaoImpl;

    @Override
//    @Transaction
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
        Account from = jdbcAccountDaoImpl.queryAccountByCardNo(fromCardNo);
        Account to = jdbcAccountDaoImpl.queryAccountByCardNo(toCardNo);
        from.setMoney(from.getMoney() - money);
        to.setMoney(to.getMoney() + money);
        jdbcAccountDaoImpl.updateAccountByCardNo(to);
            int c = 1/0;
        jdbcAccountDaoImpl.updateAccountByCardNo(from);
    }
}
