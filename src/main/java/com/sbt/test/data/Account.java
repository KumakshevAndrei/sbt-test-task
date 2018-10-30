package com.sbt.test.data;

import com.sbt.test.util.Utils;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "account")
public class Account extends EntityWithId {

    @Column(name = "account_number")
    private BigInteger accountNumber;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "currency")
    @OneToOne(targetEntity = Currency.class)
    private Currency currency;

    public BigInteger getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(BigInteger accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
