package com.sbt.test.testing;

import com.sbt.test.data.dao.AccountDao;
import com.sbt.test.util.Utils;
import org.testng.annotations.Test;

@Test
public class Tests {

    Utils utils;


    public void testAccountGetting() {
        utils = Utils.getInstance();
        AccountDao accountDao = utils.getDao(AccountDao.class);
    }

}
