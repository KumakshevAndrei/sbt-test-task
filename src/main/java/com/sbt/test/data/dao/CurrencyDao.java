package com.sbt.test.data.dao;

import com.sbt.test.data.Currency;

public class CurrencyDao extends CommonDao<Currency> {

    private final String entityHql = "from currency";

}
