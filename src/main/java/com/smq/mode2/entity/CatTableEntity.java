package com.smq.mode2.entity;

import java.io.Serializable;

/**
 * Created by shimanqian on 2017/7/6.
 */

public class CatTableEntity implements Serializable {
    private String level;
    private String parentId;
    private String id;
    private String name;
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
