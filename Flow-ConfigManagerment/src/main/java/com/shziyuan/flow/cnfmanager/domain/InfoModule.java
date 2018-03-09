package com.shziyuan.flow.cnfmanager.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "info_module")
public class InfoModule {
	@Id
    private Integer id;

    private String module;

    private Date createTime;

    private Date updateTime;

    private Boolean enabled;

    private String description;

    private Integer platformId;

    private String redisKey;

    private String type;

    private Byte system;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Byte getSystem() {
        return system;
    }

    public void setSystem(Byte system) {
        this.system = system;
    }
}