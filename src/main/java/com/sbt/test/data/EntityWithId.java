package com.sbt.test.data;

import com.sbt.test.util.Utils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EntityWithId {

    @Id
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return Utils.getInstance().objectAsJsonString(this);
    }
}
