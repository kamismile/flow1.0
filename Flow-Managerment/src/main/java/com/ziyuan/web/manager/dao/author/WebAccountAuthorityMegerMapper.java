package com.ziyuan.web.manager.dao.author;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ziyuan.web.manager.dao.ICRUDMapper;
import com.ziyuan.web.manager.domain.author.WebAccountAuthorityMegerBean;

public interface WebAccountAuthorityMegerMapper extends ICRUDMapper<WebAccountAuthorityMegerBean> {

	List<WebAccountAuthorityMegerBean> selectOneAuthority(@Param("description") String description, @Param("enabled") String enabled);

	List<WebAccountAuthorityMegerBean> selectBindAuthoritySys(@Param("role") String role);

	int getID(@Param("string") String string);
}
