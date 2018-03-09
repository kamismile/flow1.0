package com.ziyuan.web.manager.dao.author;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.domain.author.WebAccountRoleMegerBean;

public interface WebAccountRoleMegerMapper extends ICRUDMapper<WebAccountRoleMegerBean>{

	List<WebAccountRoleMegerBean> selectBindAuthority(Map<String, String> param);

	List<WebAccountRoleMegerBean> selectOneRole(@Param("description") String description, @Param("enabled") String enabled);

}
