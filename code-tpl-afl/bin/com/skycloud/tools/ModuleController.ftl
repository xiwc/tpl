/**
 * ${module.clsName}Controller.java
 */
package ${module.pkg}.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ${module.pkg}.base.BaseController;
import ${module.pkg}.pojo.entity.${module.clsName};
import ${module.pkg}.pojo.vo.PageResult;
import ${module.pkg}.pojo.vo.ReqBody;
import ${module.pkg}.pojo.vo.ResultMsg;
import ${module.pkg}.service.I${module.clsName}Service;

/**
 * 【${module.description}】请求控制层.
 * 
 * @creation ${module.creation}
 * @modification ${module.modification}
 * @company ${module.company}
 * @author ${module.author}
 * @version ${module.version}
 * 
 */
@Controller
@RequestMapping(value = "${module.name}")
public class ${module.clsName}Controller extends BaseController {

	private static Logger logger = Logger.getLogger(${module.clsName}Controller.class);

	@Autowired
	I${module.clsName}Service ${module.name}Service;

	/**
	 * 添加【${module.description}】.
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @return
	 */
	// @RequestMapping("add")
	@ResponseBody
	public ResultMsg add(HttpServletRequest request, @RequestBody(required = false) ReqBody reqBody, @ModelAttribute ${module.clsName} ${module.name}, Locale locale, Model model) {
	
		logger.debug("添加【${module.description}】");

		// TODO

		// ${module.clsName} ${module.name} = getParam(reqBody, ${module.clsName}.class);

		// 参数验证
		// Assert.notNull(${module.name}.get);

		boolean saved = ${module.name}Service.save(locale, ${module.name});

		// TODO null->ID
		return new ResultMsg(saved, reqBody.getId(), null);
	}

	/**
	 * 删除【${module.description}】.
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @return
	 */
	// @RequestMapping("delete")
	@ResponseBody
	public ResultMsg delete(HttpServletRequest request, @RequestBody(required = false) ReqBody reqBody, @ModelAttribute ${module.clsName} ${module.name}, Locale locale, Model model) {
	
		logger.debug("删除【${module.description}】");

		// TODO

		// ${module.clsName} ${module.name} = getParam(reqBody, ${module.clsName}.class);

		// 参数验证
		// Assert.notNull(${module.name}.get);

		boolean deleted = ${module.name}Service.delete(locale, ${module.name});

		return new ResultMsg(deleted, reqBody.getId());
	}

	/**
	 * 获取【${module.description}】.
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @return
	 */
	// @RequestMapping("get")
	@ResponseBody
	public ResultMsg get(HttpServletRequest request, @RequestBody(required = false) ReqBody reqBody, @ModelAttribute ${module.clsName} ${module.name}, Locale locale, Model model) {
	
		logger.debug("获取【${module.description}】");

		// TODO

		// ${module.clsName} ${module.name} = getParam(reqBody, ${module.clsName}.class);
		
		// 参数验证
		// Assert.notNull(${module.name}.get);

		${module.clsName} ${module.clsName}Result = ${module.name}Service.get(locale, ${module.name});

		return new ResultMsg(true, reqBody.getId(), ${module.clsName}Result);
	}
	
	/**
	 * 更新【${module.description}】.
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @return
	 */
	// @RequestMapping("update")
	@ResponseBody
	public ResultMsg update(HttpServletRequest request, @RequestBody(required = false) ReqBody reqBody, @ModelAttribute ${module.clsName} ${module.name}, Locale locale, Model model) {
	
		logger.debug("更新【${module.description}】");

		// TODO

		// ${module.clsName} ${module.name} = getParam(reqBody, ${module.clsName}.class);
		
		// 参数验证
		// Assert.notNull(${module.name}.get);

		boolean updated = ${module.name}Service.update(locale, ${module.name});

		return new ResultMsg(updated, reqBody.getId());
	}
	
	/**
	 * 列举【${module.description}】.
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @return
	 */
	// @RequestMapping("list")
	@ResponseBody
	public ResultMsg list(HttpServletRequest request, @RequestBody(required = false) ReqBody reqBody, @ModelAttribute ${module.clsName} ${module.name}, Locale locale, Model model) {
	
		logger.debug("列举【${module.description}】");

		// TODO

		// ${module.clsName} ${module.name} = getParam(reqBody, ${module.clsName}.class);
		
		// 参数验证
		// Assert.notNull(${module.name}.get);

		List<${module.clsName}> ${module.name}List = ${module.name}Service.list(locale);

		return new ResultMsg(reqBody.getId(), ${module.name}List);
	}
	
	/**
	 * 查询【${module.description}】(不分页).
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @return
	 */
	// @RequestMapping("query")
	@ResponseBody
	public ResultMsg query(HttpServletRequest request, @RequestBody(required = false) ReqBody reqBody, @ModelAttribute ${module.clsName} ${module.name}, Locale locale, Model model) {

		logger.debug("查询【${module.description}】");

		// TODO

		// ${module.clsName} ${module.name} = getParam(reqBody, ${module.clsName}.class);

		// 参数验证
		// Assert.notNull(${module.name}.get);

		List<${module.clsName}> ${module.name}List = ${module.name}Service.query(locale, ${module.name});

		return new ResultMsg(reqBody.getId(), ${module.name}List);
	}

	/**
	 * 查询【${module.description}】(分页).
	 * 
	 * @author ${module.author}
	 * @creation ${module.creation}
	 * @modification ${module.modification}
	 * @return
	 */
	// @RequestMapping("paging")
	@ResponseBody
	public ResultMsg paging(HttpServletRequest request, @RequestBody(required = false) ReqBody reqBody, @ModelAttribute ${module.clsName} ${module.name}, Locale locale, Model model) {

		logger.debug("查询【${module.description}】");

		// TODO

		// ${module.clsName} ${module.name} = getParam(reqBody, ${module.clsName}.class);

		// 参数验证
		Assert.notNull(reqBody.getStart());
		Assert.notNull(reqBody.getLimit());
		
		// Assert.notNull(${module.name}.get);

		PageResult pageResult = ${module.name}Service.paging(locale, ${module.name}, reqBody.getStart(), reqBody.getLimit());

		return new ResultMsg(reqBody.getId(), pageResult.getList(), pageResult.getTotal());
	}
	
}
