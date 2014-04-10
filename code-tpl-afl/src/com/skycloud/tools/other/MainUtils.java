package com.skycloud.tools.other;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.skycloud.util.AES;

public class MainUtils {
	public static void main(String[] args) throws Exception {
		// modifyFileName();
		// zabbixSql();

		System.out.println(AES.Encrypt("http://192.168.35.77/allmoniapi/api_jsonrpc.php"));
		System.out.println(AES.Encrypt("http://192.168.0.113/zabbix/api_jsonrpc.php"));
	}

	static void zabbixSql() throws Exception {
		String path = "C:\\Users\\thinkpad\\Desktop\\mysql";

		// File file = FileUtils.getFile(new File(path), "schema.sql");
		File file = FileUtils.getFile(new File(path), "skycloud-zabbix.sql");

		List<String> readLines = FileUtils.readLines(file, "UTF-8");

		List<String> tbList = new ArrayList<>();

		for (String line : readLines) {
			Pattern compile = Pattern.compile("^CREATE TABLE `(.+)` \\($");
			Matcher matcher = compile.matcher(line);
			matcher.matches();

			try {
				// System.out.println(matcher.group(1));
				tbList.add(matcher.group(1));
			} catch (Exception e) {
			}
		}

		System.out.println("----------------------------------------");
		Collections.sort(tbList);
		for (String tb : tbList) {
			System.out.println(tb);
		}
	}

	static void modifyFileName() throws IOException {
		// K:\李小鹏\吸血鬼日记\第二季
		String path = "K:\\李小鹏\\吸血鬼日记\\第三季";
		// String repStr = "[中英双字]";
		// String repWith = "";

		Collection<File> listFiles = FileUtils.listFiles(FileUtils.getFile(path), new String[] { "rmvb" }, false);

		for (File file : listFiles) {
			// String newFileName = file.getName().replace(repStr, repWith);
			//
			// try {
			// FileUtils.moveFile(file, new File(file.getParentFile(),
			// newFileName));
			// } catch (Exception e) {
			// e.printStackTrace();
			// }

			// System.out.println(newFileName);

			// [鐢靛奖澶╁爞www.dy2018.net]鍚歌楝兼棩璁扮浜屽05闆哰涓嫳鍙屽瓧]
			// [鐢靛奖澶╁爞www.dy2018.net]鍚歌楝兼棩璁扮涓夊01闆哰涓嫳鍙屽瓧]
			// [鐢靛奖澶╁爞www.dy2018.net]鍚歌楝兼棩璁扮涓夊04闆哰涓嫳鍙屽瓧]
			try {
				int i = file.getAbsolutePath().indexOf("涓夊");
				int j = file.getAbsolutePath().indexOf("闆哰涓");

				String no = file.getAbsolutePath().substring(i + "涓夊".length(), j);

				FileUtils.moveFile(file, new File(file.getParentFile(), "吸血鬼日记[第三季]" + no + ".rmvb"));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
