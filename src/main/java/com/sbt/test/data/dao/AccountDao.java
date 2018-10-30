package com.sbt.test.data.dao;

import com.sbt.test.data.Account;

public class AccountDao extends CommonDao<Account> {

    private final String entityHql = "from account";

}
