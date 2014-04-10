/**
 * I${module.clsName}Dao.java
 */
package ${module.pkg}.dao;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import ${module.pkg}.pojo.entity.${module.clsName};

/**
 * 【${module.description}】持久化接口层.
 * 
 * @creation ${module.creation}
 * @modification ${module.modification}
 * @company ${module.company}
 * @author ${module.author}
 * @version ${module.version}
 * 
 */
public interface I${module.clsName}Dao {

	/**
	 * 查询【${module.description}】.
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @param locale
	 * @param ${module.name}
	 * @param start
	 * @param limit
	 * @return
	 */
	List<Map<String, Object>> query(Locale locale, ${module.clsName} ${module.name}, Long start, Long limit);

	/**
	 * 查询总数查询【${module.description}】.
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @param locale
	 * @param ${module.name}
	 * @return
	 */
	long queryCount(Locale locale, ${module.clsName} ${module.name});
	
}
