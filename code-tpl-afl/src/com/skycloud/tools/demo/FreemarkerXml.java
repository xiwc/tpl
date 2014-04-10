package com.skycloud.tools.demo;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerXml {
	Template testfile;
	Map<String, User> root;

	FreemarkerXml() {
		init();
		initData();
	}

	private void initData() {
		root = new HashMap<String, User>();
		User user = new User();
		user.setName("qiaobing11");
		user.setPwd("password11");
		List<Address> addresses = new ArrayList<Address>();
		for (int i = 0; i < 10; i++) {
			Address address = new Address();
			address.setName("address_" + i);
			addresses.add(address);

		}
		Map<String, Address> keyadds = new HashMap<String, Address>();
		for (int i = 0; i < 10; i++) {
			Address address = new Address();
			address.setName("mapaddress_" + i);
			keyadds.put("keyadd_" + i, address);
		}
		user.setAdds(addresses);
		user.setKeyadds(keyadds);
		root.put("user", user);
	}

	private void init() {
		try {
			Configuration tempConfiguration = new Configuration();
			tempConfiguration.setClassicCompatible(true);
			tempConfiguration.setClassForTemplateLoading(this.getClass(), "");
			tempConfiguration.setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			tempConfiguration.setNumberFormat("");
			tempConfiguration.setDefaultEncoding("utf-8");
			testfile = tempConfiguration.getTemplate("test.ftl");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String toString() {

		StringWriter sw = new StringWriter();
		try {

			testfile.process(root, sw);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sw.toString();
	}

	/**
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		System.out.println(new FreemarkerXml().toString());
	}

}