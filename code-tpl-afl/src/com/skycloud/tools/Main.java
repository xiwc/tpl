package com.skycloud.tools;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.skycloud.tools.face.CodeTplFrame;

public class Main {

	public static void main(String[] args) {

		JFrame.setDefaultLookAndFeelDecorated(true);

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				// LookAndFeelInfo[] installedLookAndFeels =
				// UIManager.getInstalledLookAndFeels();
				//
				// for (LookAndFeelInfo item : installedLookAndFeels) {
				// System.out.println(item.getClassName());
				// }

				try {
					// javax.swing.plaf.metal.MetalLookAndFeel
					// javax.swing.plaf.nimbus.NimbusLookAndFeel
					// com.sun.java.swing.plaf.motif.MotifLookAndFeel
					// com.sun.java.swing.plaf.windows.WindowsLookAndFeel
					// com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}

				CodeTplFrame codeTplFrame = new CodeTplFrame();
				codeTplFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				codeTplFrame.setVisible(true);
			}
		});
	}

	static void generateCode() {

		// final String name = "user";
		// final String description = "用户";
		// final String name = "media";
		// final String description = "告警媒介";
		// final String name = "mediaType";
		// final String description = "告警媒介类型";
		// final String name = "webSite";
		// final String description = "Web站点";
		// final String name = "hostGroup";
		// final String description = "主机组";
		final String name = "action";
		final String description = "告警动作";

		final boolean isCovered = false;

		IHandler handler = new CodeTplHandler();

		Map<String, String> map = new HashMap<String, String>();
		map.put("Module.ftl", "pojo/entity/");
		map.put("ModuleController.ftl", "controller/");
		map.put("IModuleService.ftl", "service/");
		map.put("ModuleServiceImpl.ftl", "service/impl/");
		map.put("IModuleDao.ftl", "dao/");
		map.put("ModuleDaoImpl.ftl", "dao/impl/");

		for (String tplPath : map.keySet()) {
			String parse = handler.parse(tplPath, new IModuleInit() {

				SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");

				@Override
				public void init(Module module) {

					String datetime = format.format(new Date());

					module.setName(name);
					module.setClsName(StringUtils.capitalize(name));
					module.setDescription(description);
					module.setCreation(datetime);
					module.setModification(datetime);
				}
			});

			// 保存到文件
			String basePath = "D:/SourceCode/Java/workspace/jkb/src/main/java/com/skycloud/jkb/";

			String dirPath = map.get(tplPath);
			String fileName = tplPath.replace("Module", StringUtils.capitalize(name)).replace(".ftl", ".java");
			String filePath = basePath + dirPath + fileName;

			File file2 = FileUtils.getFile(filePath);

			boolean _isCovered = isCovered;

			if (_isCovered || (file2 == null || !file2.exists())) {
				File file = new File(filePath);
				try {
					FileUtils.write(file, parse, "UTF-8");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("[提示]:[" + filePath + "]已经存在!");
			}

			System.out.println("处理完毕...");
		}
	}
}
