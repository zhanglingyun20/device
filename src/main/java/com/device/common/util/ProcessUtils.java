package com.device.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class ProcessUtils {

	public static final String EXE = ".exe";
	public static void main(String[] args) throws IOException {
		System.out.println(getAllProcess());
	}
	/**
	 * 获取系统所有进程
	 * @return
	 */
	public static Set<String> getAllProcess() {
		Set<String> processSet = new HashSet<String>();
		Process proc;
		try {
			proc = Runtime.getRuntime().exec("tasklist");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			String info = br.readLine();
			while (info != null) {
				if (info.indexOf(EXE) >= 0) 
				{
					String processName = info.substring(0,info.indexOf(EXE))+EXE;
					processSet.add(processName.toLowerCase()); //全部转成小写存储，方便后面比较
				}
				info = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return processSet;
	}

}