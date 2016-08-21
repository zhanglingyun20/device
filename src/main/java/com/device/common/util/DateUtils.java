package com.device.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static int getDateDiffMinutes(Date startTime,Date endTime){
		if (startTime==null||endTime==null) {
			return 0;
		}
		Long start = startTime.getTime();
		Long end = endTime.getTime();
		return (end.intValue()-start.intValue())/(1000*60);
	}
	
	public static String dateToString(Date date,String format){
		 SimpleDateFormat formatter = new SimpleDateFormat(format);
		 return  formatter.format(date);
	}
}
