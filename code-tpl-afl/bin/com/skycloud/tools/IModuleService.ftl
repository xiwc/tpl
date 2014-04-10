/**
 * I${module.clsName}Service.java
 */
package ${module.pkg}.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import ${module.pkg}.base.IBaseService;
import ${module.pkg}.pojo.entity.${module.clsName};
import ${module.pkg}.pojo.vo.PageResult;

/**
 * 【${module.description}】业务逻辑接口.
 * 
 * @creation ${module.creation}
 * @modification ${module.modification}
 * @company ${module.company}
 * @author ${module.author}
 * @version ${module.version}
 * 
 */
public interface I${module.clsName}Service extends IBaseService {

	/**
	 * 创建【${module.description}】.
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @param ${module.name}
	 * @return
	 */
	boolean save(Locale locale, ${module.clsName} ${module.name});

	/**
	 * 删除【${module.description}】.
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @param ${module.name}
	 * @return
	 */
	boolean delete(Locale locale, ${module.clsName} ${module.name});

	/**
	 * 获取【${module.description}】.
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @param ${module.name}
	 * @return
	 */
	${module.clsName} get(Locale locale, ${module.clsName} ${module.name});
	
	/**
	 * 更新【${module.description}】.
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @param ${module.name}
	 * @return
	 */
	boolean update(Locale locale, ${module.clsName} ${module.name});
	
	/**
	 * 列举【${module.description}】.
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @param ${module.name}
	 * @return
	 */
	List<${module.clsName}> list(Locale locale);

	/**
	 * 查询【${module.description}】(不分页).
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @param ${module.name}
	 * @return
	 */
	List<${module.clsName}> query(Locale locale, ${module.clsName} ${module.name});

	/**
	 * 查询【${module.description}】(不分页).
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @param ${module.name}
	 * @return
	 */
	List<Map<String, Object>> queryMapList(Locale locale, ${module.clsName} ${module.name});

	/**
	 * 查询【${module.description}】(分页).
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @param ${module.name}
	 * @return
	 */
	PageResult paging(Locale locale, ${module.clsName} ${module.name}, Long start, Long limit);

	/**
	 * 判断【${module.description}】是否存在.
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @param ${module.name}
	 * @return
	 */
	boolean exists(Locale locale, ${module.clsName} ${module.name});
}
