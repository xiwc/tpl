/**
 * ${module.clsName}ServiceImpl.java
 */
package ${module.pkg}.service.impl;

import java.util.List; 
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${module.pkg}.base.impl.BaseServiceImpl;
import ${module.pkg}.component.ApiInvoker;
import ${module.pkg}.dao.I${module.clsName}Dao;
import ${module.pkg}.pojo.entity.${module.clsName};
import ${module.pkg}.pojo.vo.PageResult;
import ${module.pkg}.service.I${module.clsName}Service;

/**
 * 【${module.description}】业务逻辑实现.
 * 
 * @creation ${module.creation}
 * @modification ${module.modification}
 * @company ${module.company}
 * @author ${module.author}
 * @version ${module.version}
 * 
 */
@Service
@Transactional
public class ${module.clsName}ServiceImpl extends BaseServiceImpl implements I${module.clsName}Service {

	private static Logger logger = Logger.getLogger(${module.clsName}ServiceImpl.class);

	@Autowired
	I${module.clsName}Dao ${module.name}Dao;

	@Autowired
	ApiInvoker apiInvoker;

	@Override
	public boolean save(Locale locale, ${module.clsName} ${module.name}) {
		
		logger.debug("[业务逻辑层]添加【${module.description}】");
		
		// TODO
		return true;
	}

	@Override
	public boolean delete(Locale locale, ${module.clsName} ${module.name}) {
		
		logger.debug("[业务逻辑层]删除【${module.description}】");
		
		// TODO
		return true;
	}

	@Override
	public ${module.clsName} get(Locale locale, ${module.clsName} ${module.name}) {
		
		logger.debug("[业务逻辑层]获取【${module.description}】");
		
		// TODO
		return null;
	}

	@Override
	public boolean update(Locale locale, ${module.clsName} ${module.name}) {
		
		logger.debug("[业务逻辑层]更新【${module.description}】");
		
		// TODO
		return true;
	}
	
	@Override
	public List<${module.clsName}> list(Locale locale) {

		logger.debug("[业务逻辑层]列举【${module.description}】");

		// TODO
		return null;
	}
	
	@Override
	public List<${module.clsName}> query(Locale locale, ${module.clsName} ${module.name}) {

		logger.debug("[业务逻辑层]查询【${module.description}】(不分页)");

		// TODO
		return null;
	}
	
	@Override
	public List<Map<String, Object>> queryMapList(Locale locale, ${module.clsName} ${module.name}) {

		logger.debug("[业务逻辑层]查询MapList【${module.description}】(不分页)");

		// TODO
		return null;
	}

	@Override
	public PageResult paging(Locale locale, ${module.clsName} ${module.name}, Long start, Long limit) {

		logger.debug("[业务逻辑层]查询【${module.description}】(分页)");

		PageResult pageResult = new PageResult();

		// TODO
		return pageResult;
	}
	
	@Override
	public boolean exists(Locale locale, ${module.clsName} ${module.name}) {
		
		logger.debug("[业务逻辑层]判断【${module.description}】是否存在");
		
		// TODO
		return true;
	}
	
}