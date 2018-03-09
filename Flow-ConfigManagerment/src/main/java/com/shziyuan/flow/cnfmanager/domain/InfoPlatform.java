package com.shziyuan.flow.cnfmanager.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.shziyuan.flow.cnfmanager.daoutil.Criteria;
import com.shziyuan.flow.cnfmanager.daoutil.ExcludeCriteria;

@Table(name = "info_platform")
public class InfoPlatform implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

    @ExcludeCriteria(true)
    private Date create_time;

    @Criteria("platform like '%${platform}%'")
    private String platform;

    private Boolean enabled;

    private Date update_time;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}