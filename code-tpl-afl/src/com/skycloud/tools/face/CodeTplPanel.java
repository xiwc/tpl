/*
 * Created by JFormDesigner on Fri Jan 03 12:30:40 CST 2014
 */

package com.skycloud.tools.face;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.skycloud.tools.CodeTplHandler;
import com.skycloud.tools.IHandler;
import com.skycloud.tools.IModuleInit;
import com.skycloud.tools.Module;

/**
 * @author xiweicheng
 */
public class CodeTplPanel extends JPanel {

	/** serialVersionUID [long] */
	private static final long serialVersionUID = -7785930309100279860L;

	String getBasePath() {
		String text = textField1.getText();

		text = text.replace("\\", "/");

		if (!text.endsWith("/")) {
			text += "/";
		}

		textField1.setText(text);

		return text;
	}

	Map<String, String> map = new HashMap<String, String>();
	{
		map.put("Module.ftl", "pojo/entity/");
		map.put("ModuleController.ftl", "controller/");
		map.put("IModuleService.ftl", "service/");
		map.put("ModuleServiceImpl.ftl", "service/impl/");
		map.put("IModuleDao.ftl", "dao/");
		map.put("ModuleDaoImpl.ftl", "dao/impl/");
	}

	IHandler handler = new CodeTplHandler();

	Gson gson = new GsonBuilder().create();

	public CodeTplPanel() {
		initComponents();

		initModel();
	}

	private void initModel() {

		loadConfig();

		for (String tpl : map.keySet()) {
			comboBox3.addItem(tpl);
		}

		refreshFiles();
	}

	/**
	 * 
	 * 
	 * @author xiweicheng
	 * @creation 2014年1月3日 下午3:55:01
	 * @modification 2014年1月3日 下午3:55:01
	 */
	public void loadConfig() {
		loadModule();
		loadBasic();
	}

	private void loadBasic() {
		// 加载模块配置
		try {
			String config = FileUtils.readFileToString(fileBasic, "UTF-8");
			Map map = gson.fromJson(config, Map.class);

			mapConfigBasic.putAll(map);

			String basePath = mapConfigBasic.get("basePath");

			if (basePath != null && basePath.length() > 0) {
				textField1.setText(basePath);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * @author xiweicheng
	 * @creation 2014年1月3日 下午5:59:18
	 * @modification 2014年1月3日 下午5:59:18
	 */
	public void loadModule() {
		// 加载模块配置
		try {
			String config = FileUtils.readFileToString(fileModule, "UTF-8");
			Map map = gson.fromJson(config, Map.class);

			mapConfigModule.putAll(map);

			DefaultComboBoxModel model = (DefaultComboBoxModel) comboBox1.getModel();
			model.removeAllElements();

			for (Object object : mapConfigModule.keySet()) {
				comboBox1.addItem(object);
			}

			DefaultComboBoxModel model2 = (DefaultComboBoxModel) comboBox2.getModel();
			model2.removeAllElements();
			for (Object object : mapConfigModule.values()) {
				comboBox2.addItem(object);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void log(String... infos) {

		// TODO 滚动条没有自动滚动
		for (String info : infos) {
			textArea2.append(info);
			textArea2.append("\r\n");
		}
	}

	void log(List<String> infos) {

		// TODO 滚动条没有自动滚动
		for (String info : infos) {
			textArea2.append(info);
			textArea2.append("\r\n");
		}
	}

	String getModuleId() {
		return comboBox1.getSelectedItem() == null ? "" : comboBox1.getSelectedItem().toString();
	}

	String getModuleName() {
		return comboBox2.getSelectedItem() == null ? "" : comboBox2.getSelectedItem().toString();
	}

	void preview(String text) {
		textArea1.setText(text);
	}

	private void comboBox3ActionPerformed(ActionEvent e) {

		String selTpl = comboBox3.getSelectedItem().toString();

		String moduleId = getModuleId();

		String dirPath = map.get(selTpl);
		String fileName = selTpl
				.replace("Module", StringUtils.capitalize(moduleId.length() == 0 ? "module" : moduleId)).replace(
						".ftl", ".java");
		String filePath = getBasePath() + dirPath + fileName;

		textField2.setText(filePath);
		// log("选择模板:" + selTpl);

		File file = FileUtils.getFile(filePath);

		if (file != null && file.exists()) {
			checkBox1.setSelected(false);
			log("文件已经存在!", filePath);
		}

		if (getModuleId().trim().length() == 0 || getModuleName().trim().length() == 0) {
			// 预览模板方法
			previewTpl(selTpl);
		} else {

			String text = handler.parse(getTpl(), new IModuleInit() {

				@Override
				public void init(Module module) {
					String datetime = format.format(new Date());

					module.setName(getModuleId());
					module.setClsName(StringUtils.capitalize(getModuleId()));
					module.setDescription(getModuleName());
					module.setCreation(datetime);
					module.setModification(datetime);
					module.setPkg(getPkg());
				}
			});

			preview(text);

		}
	}

	// 解析方法
	private void previewTpl(String selTpl) {
		try {
			InputStream inputStream = CodeTplHandler.class.getResourceAsStream(selTpl);

			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\r\n");
			}

			// 预览代码
			preview(sb.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	String getTpl() {
		return comboBox3.getSelectedItem().toString();
	}

	SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");

	String getPkg() {
		String flg = "src/main/java/";
		String basePath = getBasePath();
		int index = basePath.indexOf(flg);
		if (index != -1) {
			String subPath = basePath.substring(index + flg.length());
			subPath = subPath.endsWith("/") ? subPath.substring(0, subPath.length() - 1) : subPath;
			return subPath.replace('/', '.');
		} else {
			log("基础路径不包含[" + flg + "]特殊路径.");
		}
		return StringUtils.EMPTY;
	}

	private void button1ActionPerformed(ActionEvent e) {

		if (getModuleId().trim().length() == 0 || getModuleName().trim().length() == 0) {
			log("模块标识和模块名称不能为空!");
			return;
		}

		String text = handler.parse(getTpl(), new IModuleInit() {

			@Override
			public void init(Module module) {
				String datetime = format.format(new Date());

				module.setName(getModuleId());
				module.setClsName(StringUtils.capitalize(getModuleId()));
				module.setDescription(getModuleName());
				module.setCreation(datetime);
				module.setModification(datetime);
				module.setPkg(getPkg());
			}
		});

		saveAsJavaFile(text, getTpl());

		preview(text);
	}

	// 保存
	private void saveAsJavaFile(String text, String tpl) {
		// 保存到文件
		String dirPath = map.get(tpl);
		String fileName = tpl.replace("Module", StringUtils.capitalize(getModuleId())).replace(".ftl", ".java");
		String filePath = getBasePath() + dirPath + fileName;

		File file2 = FileUtils.getFile(filePath);

		boolean _isCovered = checkBox1.isSelected();

		if (_isCovered || (file2 == null || !file2.exists())) {
			File file = new File(filePath);
			try {

				if (file2 != null && file2.exists()) {
					backup(file2);
				}

				FileUtils.write(file, text, "UTF-8");

				refreshFiles();

				comboBox5.setSelectedItem(filePath);

				log("文件生成成功!", filePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			log("文件已经存在!", filePath);
		}
	}

	SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd-hhmmss_");

	// back up.
	private void backup(File file2) {
		Date now = new Date();
		try {
			File fileBack = new File(dirBackup, format2.format(now) + now.getTime() + "_" + file2.getName());
			FileUtils.copyFile(file2, fileBack);
			log("备份文件成功:", fileBack.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
			log("备份文件异常:", e.getMessage());
		}
	}

	boolean exists(JComboBox comboBox, Object item) {
		for (int i = 0; i < comboBox.getItemCount(); i++) {
			if (comboBox.getItemAt(i).equals(item)) {
				return true;
			}
		}
		return false;
	}

	void appendToComb() {

		String id = comboBox1.getEditor().getItem().toString();
		String name = comboBox2.getEditor().getItem().toString();

		if (id.length() == 0 || name.length() == 1) {
			log("模块标识和模块名称不能为空!");
			return;
		}

		if (!exists(comboBox1, id)) {
			comboBox1.addItem(id);
		}
		if (!exists(comboBox2, name)) {
			comboBox2.addItem(name);
		}

		if (!map.containsKey(id)) {
			mapConfigModule.put(id, name);
		}
	}

	Map<String, String> mapConfigModule = new HashMap<>();
	Map<String, String> mapConfigBasic = new HashMap<>();

	private void button4ActionPerformed(ActionEvent e) {

		appendToComb();

		try {
			FileUtils.write(fileModule, gson.toJson(mapConfigModule), "UTF-8");
			mapConfigBasic.clear();
			mapConfigBasic.put("basePath", textField1.getText());

			FileUtils.write(fileBasic, gson.toJson(mapConfigBasic), "UTF-8");

			log("模块配置保存成功!");
		} catch (IOException e1) {
			e1.printStackTrace();
			log("模块配置保存失败!", e1.getMessage());
		}
	}

	private void comboBox1ActionPerformed(ActionEvent e) {
		comboBox2.setSelectedItem(mapConfigModule.get(getModuleId()));
	}

	private void comboBox2ActionPerformed(ActionEvent e) {

		for (String id : mapConfigModule.keySet()) {
			if (mapConfigModule.get(id).equals(getModuleName())) {
				comboBox1.setSelectedItem(id);
				return;
			}
		}
	}

	private void button5ActionPerformed(ActionEvent e) {
		String id = getModuleId();
		mapConfigModule.remove(id);

		try {
			FileUtils.write(fileModule, gson.toJson(mapConfigModule), "UTF-8");

			loadConfig();

			log("删除成功!");
		} catch (IOException e1) {
			e1.printStackTrace();
			log(e1.getMessage());
		}

	}

	String getFilePath() {

		String tplPath = getTpl();
		String dirPath = map.get(tplPath);
		String fileName = tplPath.replace("Module", StringUtils.capitalize(getModuleId())).replace(".ftl", ".java");
		String filePath = getBasePath() + dirPath + fileName;

		return filePath;
	}

	private void button3ActionPerformed(ActionEvent e) {

		File file2 = FileUtils.getFile(getFilePath());

		if (file2 != null && file2.exists()) {
			try {
				Desktop.getDesktop().open(FileUtils.getFile(getFilePath()));
			} catch (Exception e1) {
				e1.printStackTrace();
				log("打开文件异常:", e1.getMessage());
			}
		} else {
			log("文件不存在!", getFilePath());
		}
	}

	private void button6ActionPerformed(ActionEvent e) {
		File file2 = FileUtils.getFile(getFilePath());

		if (file2 != null && file2.exists()) {
			if (JOptionPane.showConfirmDialog(this, "确认删除以下吗?\r\n" + getFilePath(), "提示", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				FileUtils.deleteQuietly(file2);
				refreshFiles();
				log("删除成功!", getFilePath());
			}
		} else {
			log("文件不存在!", getFilePath());
		}
	}

	private void button7ActionPerformed(ActionEvent e) {
		refreshFiles();
	}

	/**
	 * 
	 * 
	 * @author xiweicheng
	 * @creation 2014年1月3日 下午4:51:40
	 * @modification 2014年1月3日 下午4:51:40
	 */
	public void refreshFiles() {

		DefaultComboBoxModel model = (DefaultComboBoxModel) comboBox5.getModel();
		model.removeAllElements();

		int cnt = 0;
		for (String tpl : map.keySet()) {
			String dir = map.get(tpl);
			String dirPath = getBasePath() + dir;

			try {
				File fileDir = FileUtils.getFile(dirPath);

				if (fileDir != null && fileDir.exists()) {
					Collection<File> listFiles = FileUtils.listFiles(fileDir, new String[] { "java" }, false);

					for (File file : listFiles) {

						if (file.getName().equals("package-info.java")) {
							continue;
						}
						comboBox5.addItem(file.getAbsolutePath().replace("\\", "/"));
						cnt++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log("刷新文件异常:", e.getMessage());
			}
		}
		((TitledBorder) panel7.getBorder()).setTitle("文件-总数:" + cnt);
		panel7.validate();
		panel7.doLayout();
		panel7.invalidate();
		panel7.repaint();
	}

	private void comboBox5ActionPerformed(ActionEvent e) {
		try {
			if (comboBox5.getSelectedItem() != null) {
				String text = FileUtils.readFileToString(FileUtils.getFile(comboBox5.getSelectedItem().toString()),
						"UTF-8");
				preview(text);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			log("预览文件异常:", e1.getMessage());
		}
	}

	private void button8ActionPerformed(ActionEvent e) {

		for (String tpl : map.keySet()) {

			if (getModuleId().trim().length() == 0 || getModuleName().trim().length() == 0) {
				log("模块标识和模块名称不能为空!");
				return;
			}

			String text = handler.parse(tpl, new IModuleInit() {

				@Override
				public void init(Module module) {
					String datetime = format.format(new Date());

					module.setName(getModuleId());
					module.setClsName(StringUtils.capitalize(getModuleId()));
					module.setDescription(getModuleName());
					module.setCreation(datetime);
					module.setModification(datetime);
					module.setPkg(getPkg());
				}
			});

			saveAsJavaFile(text, tpl);
		}
	}

	/** baseDir [String] 基本路径 */
	static String baseDir = "C:/codeTpl-config-afl";

	File fileModule = new File(baseDir + "/config-module.data");
	File fileBasic = new File(baseDir + "/config-basic.data");
	File dirBackup = new File(baseDir + "/backup/");

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		panel2 = new JPanel();
		label1 = new JLabel();
		comboBox1 = new JComboBox();
		button4 = new JButton();
		label2 = new JLabel();
		comboBox2 = new JComboBox();
		button5 = new JButton();
		panel3 = new JPanel();
		label3 = new JLabel();
		comboBox3 = new JComboBox();
		button1 = new JButton();
		label4 = new JLabel();
		comboBox4 = new JComboBox();
		button2 = new JButton();
		panel4 = new JPanel();
		button3 = new JButton();
		button6 = new JButton();
		checkBox1 = new JCheckBox();
		button8 = new JButton();
		panel6 = new JPanel();
		scrollPane2 = new JScrollPane();
		textArea2 = new JTextArea();
		panel5 = new JPanel();
		scrollPane1 = new JScrollPane();
		textArea1 = new JTextArea();
		panel7 = new JPanel();
		comboBox5 = new JComboBox();
		button7 = new JButton();
		panel8 = new JPanel();
		label5 = new JLabel();
		textField1 = new JTextField();
		label6 = new JLabel();
		textField2 = new JTextField();

		// ======== this ========
		setBorder(new EmptyBorder(15, 15, 0, 0));
		setPreferredSize(new Dimension(1000, 600));
		setLayout(new BorderLayout());

		// ======== panel1 ========
		{
			panel1.setLayout(new GridBagLayout());
			((GridBagLayout) panel1.getLayout()).columnWidths = new int[] { 0, 0, 0 };
			((GridBagLayout) panel1.getLayout()).rowHeights = new int[] { 0, 0, 0, 200, 0 };
			((GridBagLayout) panel1.getLayout()).columnWeights = new double[] { 0.0, 0.0, 1.0E-4 };
			((GridBagLayout) panel1.getLayout()).rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0E-4 };

			// ======== panel2 ========
			{
				panel2.setBorder(new TitledBorder("\u6a21\u5757\u8bbe\u7f6e"));
				panel2.setLayout(new GridBagLayout());
				((GridBagLayout) panel2.getLayout()).columnWidths = new int[] { 0, 205, 0, 0 };
				((GridBagLayout) panel2.getLayout()).rowHeights = new int[] { 0, 0, 0, 0, 0 };
				((GridBagLayout) panel2.getLayout()).columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0E-4 };
				((GridBagLayout) panel2.getLayout()).rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0E-4 };

				// ---- label1 ----
				label1.setText("\u6807\u8bc6:");
				panel2.add(label1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 10, 5), 0, 0));

				// ---- comboBox1 ----
				comboBox1.setEditable(true);
				comboBox1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						comboBox1ActionPerformed(e);
					}
				});
				panel2.add(comboBox1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 10, 5), 0, 0));

				// ---- button4 ----
				button4.setText("\u4fdd\u5b58");
				button4.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						button4ActionPerformed(e);
					}
				});
				panel2.add(button4, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 10, 0), 0, 0));

				// ---- label2 ----
				label2.setText("\u540d\u79f0:");
				panel2.add(label2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 10, 5), 0, 0));

				// ---- comboBox2 ----
				comboBox2.setEditable(true);
				comboBox2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						comboBox2ActionPerformed(e);
					}
				});
				panel2.add(comboBox2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 10, 5), 0, 0));

				// ---- button5 ----
				button5.setText("\u5220\u9664");
				button5.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						button5ActionPerformed(e);
					}
				});
				panel2.add(button5, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 10, 0), 0, 0));
			}
			panel1.add(panel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

			// ======== panel3 ========
			{
				panel3.setBorder(new TitledBorder("\u4ee3\u7801\u6a21\u677f"));
				panel3.setLayout(new GridBagLayout());
				((GridBagLayout) panel3.getLayout()).columnWidths = new int[] { 0, 205, 0, 0 };
				((GridBagLayout) panel3.getLayout()).rowHeights = new int[] { 0, 0, 0, 0, 0 };
				((GridBagLayout) panel3.getLayout()).columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0E-4 };
				((GridBagLayout) panel3.getLayout()).rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0E-4 };

				// ---- label3 ----
				label3.setText("\u6a21\u677f:");
				panel3.add(label3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 10, 5), 0, 0));

				// ---- comboBox3 ----
				comboBox3.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						comboBox3ActionPerformed(e);
					}
				});
				panel3.add(comboBox3, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 10, 5), 0, 0));

				// ---- button1 ----
				button1.setText("\u751f\u6210");
				button1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						button1ActionPerformed(e);
					}
				});
				panel3.add(button1, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 10, 0), 0, 0));

				// ---- label4 ----
				label4.setText("\u65b9\u6cd5:");
				label4.setVisible(false);
				panel3.add(label4, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 10, 5), 0, 0));

				// ---- comboBox4 ----
				comboBox4.setVisible(false);
				panel3.add(comboBox4, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 10, 5), 0, 0));

				// ---- button2 ----
				button2.setText("\u751f\u6210");
				button2.setVisible(false);
				panel3.add(button2, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 10, 0), 0, 0));
			}
			panel1.add(panel3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

			// ======== panel4 ========
			{
				panel4.setBorder(new CompoundBorder(new TitledBorder("\u751f\u6210\u6587\u4ef6\u64cd\u4f5c"),
						new EmptyBorder(5, 5, 5, 5)));
				panel4.setLayout(new FlowLayout());

				// ---- button3 ----
				button3.setText("\u6253\u5f00");
				button3.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						button3ActionPerformed(e);
					}
				});
				panel4.add(button3);

				// ---- button6 ----
				button6.setText("\u5220\u9664");
				button6.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						button6ActionPerformed(e);
					}
				});
				panel4.add(button6);

				// ---- checkBox1 ----
				checkBox1.setText("\u8986\u76d6");
				panel4.add(checkBox1);

				// ---- button8 ----
				button8.setText("\u5168\u90e8\u751f\u6210");
				button8.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						button8ActionPerformed(e);
					}
				});
				panel4.add(button8);
			}
			panel1.add(panel4, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

			// ======== panel6 ========
			{
				panel6.setBorder(new CompoundBorder(new TitledBorder("\u72b6\u6001\u4fe1\u606f"), new EmptyBorder(5, 5,
						5, 5)));
				panel6.setPreferredSize(new Dimension(259, 300));
				panel6.setLayout(new BorderLayout());

				// ======== scrollPane2 ========
				{

					// ---- textArea2 ----
					textArea2.setAutoscrolls(false);
					scrollPane2.setViewportView(textArea2);
				}
				panel6.add(scrollPane2, BorderLayout.CENTER);
			}
			panel1.add(panel6, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
					GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));
		}
		add(panel1, BorderLayout.LINE_START);

		// ======== panel5 ========
		{
			panel5.setLayout(new BorderLayout());

			// ======== scrollPane1 ========
			{
				scrollPane1
						.setBorder(new CompoundBorder(new TitledBorder("\u9884\u89c8"), new EmptyBorder(5, 5, 5, 5)));
				scrollPane1.setPreferredSize(new Dimension(26, 200));

				// ---- textArea1 ----
				textArea1.setEditable(false);
				scrollPane1.setViewportView(textArea1);
			}
			panel5.add(scrollPane1, BorderLayout.CENTER);

			// ======== panel7 ========
			{
				panel7.setBorder(new TitledBorder("\u6587\u4ef6"));
				panel7.setLayout(new BorderLayout());

				// ---- comboBox5 ----
				comboBox5.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						comboBox5ActionPerformed(e);
					}
				});
				panel7.add(comboBox5, BorderLayout.CENTER);

				// ---- button7 ----
				button7.setText("\u5237\u65b0");
				button7.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						button7ActionPerformed(e);
					}
				});
				panel7.add(button7, BorderLayout.EAST);
			}
			panel5.add(panel7, BorderLayout.NORTH);
		}
		add(panel5, BorderLayout.CENTER);

		// ======== panel8 ========
		{
			panel8.setBorder(new TitledBorder("\u8def\u5f84"));
			panel8.setLayout(new FormLayout("default, default:grow", "fill:default:grow, $lgap, default"));

			// ---- label5 ----
			label5.setText("\u57fa\u7840\u8def\u5f84:");
			panel8.add(label5, CC.xy(1, 1));

			// ---- textField1 ----
			textField1.setText("D:/SourceCode/Java/workspace/jkb/src/main/java/com/skycloud/jkb/");
			panel8.add(textField1, CC.xy(2, 1));

			// ---- label6 ----
			label6.setText("\u53e0\u52a0\u8def\u5f84:");
			panel8.add(label6, CC.xy(1, 3));

			// ---- textField2 ----
			textField2.setEditable(false);
			panel8.add(textField2, CC.xy(2, 3));
		}
		add(panel8, BorderLayout.PAGE_START);
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	private JPanel panel1;
	private JPanel panel2;
	private JLabel label1;
	private JComboBox comboBox1;
	private JButton button4;
	private JLabel label2;
	private JComboBox comboBox2;
	private JButton button5;
	private JPanel panel3;
	private JLabel label3;
	private JComboBox comboBox3;
	private JButton button1;
	private JLabel label4;
	private JComboBox comboBox4;
	private JButton button2;
	private JPanel panel4;
	private JButton button3;
	private JButton button6;
	private JCheckBox checkBox1;
	private JButton button8;
	private JPanel panel6;
	private JScrollPane scrollPane2;
	private JTextArea textArea2;
	private JPanel panel5;
	private JScrollPane scrollPane1;
	private JTextArea textArea1;
	private JPanel panel7;
	private JComboBox comboBox5;
	private JButton button7;
	private JPanel panel8;
	private JLabel label5;
	private JTextField textField1;
	private JLabel label6;
	private JTextField textField2;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
