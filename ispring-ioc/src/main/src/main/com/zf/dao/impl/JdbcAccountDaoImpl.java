package com.zf.dao.impl;

import com.zf.annotation.Autowired;
import com.zf.annotation.Service;
import com.zf.pojo.Account;
import com.zf.dao.AccountDao;
import com.zf.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Service
public class JdbcAccountDaoImpl implements AccountDao {

    @Autowired
    private ConnectionManager connectionManager;

//    public void setConnectionUtils(ConnectionManager connectionUtils) {
//        this.connectionManager = connectionUtils;
//    }

    @Override
    public Account queryAccountByCardNo(String cardNo) throws Exception {
        Connection con = connectionManager.getCurrentThreadConn();
        String sql = "select * from account where cardNo=?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1,cardNo);
        ResultSet resultSet = preparedStatement.executeQuery();

        Account account = new Account();
        while(resultSet.next()) {
            account.setCardNo(resultSet.getString("cardNo"));
            account.setName(resultSet.getString("name"));
            account.setMoney(resultSet.getInt("money"));
        }

        resultSet.close();
        preparedStatement.close();
        return account;
    }

    @Override
    public int updateAccountByCardNo(Account account) throws Exception {
        Connection con = connectionManager.getCurrentThreadConn();
        String sql = "update account set money=? where cardNo=?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1,account.getMoney());
        preparedStatement.setString(2,account.getCardNo());
        int i = preparedStatement.executeUpdate();

        preparedStatement.close();
        return i;
    }
}
