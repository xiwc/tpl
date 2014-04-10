/**
 * ${module.clsName}DaoImpl.java
 */
package ${module.pkg}.dao.impl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ${module.pkg}.base.impl.BaseDaoImpl;
import ${module.pkg}.dao.I${module.clsName}Dao;
import ${module.pkg}.pojo.entity.${module.clsName};

/**
 * 【${module.description}】持久化实现层.
 * 
 * @creation ${module.creation}
 * @modification ${module.modification}
 * @company ${module.company}
 * @author ${module.author}
 * @version ${module.version}
 * 
 */
@Repository
@Transactional
public class ${module.clsName}DaoImpl extends BaseDaoImpl implements I${module.clsName}Dao {

	@Override
	public List<Map<String, Object>> query(Locale locale, ${module.clsName} ${module.name}, Long start, Long limit) {
		
		// TODO
		
		return null;
	}

	@Override
	public long queryCount(Locale locale, ${module.clsName} ${module.name}) {
	
		// TODO
	
		return 0L;
	}
	
}
