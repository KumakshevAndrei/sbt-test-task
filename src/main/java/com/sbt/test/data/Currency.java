package com.sbt.test.data;

import com.sbt.test.util.Utils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currency")
public class Currency extends EntityWithId{

    @Column(name = "code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
