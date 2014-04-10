package com.skycloud.tools;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * 
 * @creation 2014年1月2日 上午11:46:22
 * @modification 2014年1月2日 上午11:46:22
 * @company Skycloud
 * @author xiweicheng
 * @version 1.0
 * 
 */
public class CodeTplHandler implements IHandler {

	private static final String EMPTY_STRING = "";

	@Override
	public String parse(String tplPath, IModuleInit moduleInit) {
		try {
			Configuration configuration = new Configuration();
			configuration.setClassicCompatible(true);
			configuration.setClassForTemplateLoading(this.getClass(), EMPTY_STRING);
			configuration.setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			configuration.setNumberFormat("");
			configuration.setDefaultEncoding("utf-8");
			Template template = configuration.getTemplate(tplPath);

			StringWriter sw = new StringWriter();

			Module module = new Module();
			moduleInit.init(module);

			Map<String, Object> dataModelMap = new HashMap<String, Object>();
			dataModelMap.put("module", module);

			template.process(dataModelMap, sw);

			return sw.toString();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return EMPTY_STRING;
	}

}
