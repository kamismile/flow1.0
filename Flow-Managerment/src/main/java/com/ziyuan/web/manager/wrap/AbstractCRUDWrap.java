package com.ziyuan.web.manager.wrap;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ziyuan.web.manager.dao.ICRUDMapper;

public abstract class AbstractCRUDWrap<DOMAIN> implements ICRUDWrap<DOMAIN>,InitializingBean{

	private ICRUDMapper<DOMAIN> mapper;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		setMapper(getMapper());
	}
	
	@Override
	@Transactional(readOnly=true)
	public JEasyuiData select(JEasyuiRequestBean bean) {
		PageHelper.startPage(bean.getPage(), bean.getRows());
		List<DOMAIN> list = mapper.select(bean.getParam());
		PageInfo<DOMAIN> pageInfo = new PageInfo<DOMAIN>(list);
		
		JEasyuiData result = new JEasyuiData(list);
		result.setPage(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPages());
		result.setTotal((int) pageInfo.getTotal());
		
		return result;
	}
	
	@Override
	@Transactional(readOnly = true)
	public DOMAIN selectOne(int id) {
		return mapper.selectOne(id);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<DOMAIN> selectAll(JEasyuiRequestBean bean) {
		return mapper.select(bean.getParam());
	}

	@Override
	@Transactional(readOnly=false)
	public DOMAIN insert(DOMAIN domain) {
		mapper.insert(domain);
		return domain;
	}

	@Override
	@Transactional(readOnly=false)
	public DOMAIN update(DOMAIN domain) {
		mapper.update(domain);
		return domain;
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(int id) {
		mapper.delete(id);
	}

	public abstract ICRUDMapper<DOMAIN> getMapper();

	public void setMapper(ICRUDMapper<DOMAIN> mapper) {
		this.mapper = mapper;
	}
	
	
	
	/**
	 * 批量操作
	 */
	@Resource
	private SqlSessionFactory sqlSessionFactory;
	
	@Transactional(readOnly = false)
	protected void batchRun(Consumer<SqlSession> runnable) {
		SqlSession session = sqlSessionFactory.openSession();

		try {
			runnable.accept(session);
			session.commit();
		} catch (RuntimeException e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public void batchInsert(String mapperName,List<DOMAIN> datas) {
		SqlSession session = sqlSessionFactory.openSession();

		try {
			datas.forEach(data -> session.insert(mapperName + ".insert",data));
			session.commit();
		} catch (RuntimeException e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public void batchUpdate(String mapperName,List<DOMAIN> datas) {
		SqlSession session = sqlSessionFactory.openSession();

		try {
			datas.forEach(data -> session.update(mapperName + ".update",data));
			session.commit();
		} catch (RuntimeException e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public void batchDelete(String mapperName,List<DOMAIN> datas,Function<DOMAIN,Integer> idSupplier) {
		SqlSession session = sqlSessionFactory.openSession();

		try {
			datas.forEach(data -> session.delete(mapperName + ".delete",
					idSupplier.apply(data)));
			session.commit();
		} catch (RuntimeException e) {
			session.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	protected SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
}
