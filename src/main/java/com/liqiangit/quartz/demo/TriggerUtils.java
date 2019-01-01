package com.liqiangit.quartz.demo;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.impl.triggers.CronTriggerImpl;

/**
 * cron表达式详解 <br>
 * https://www.cnblogs.com/junrong624/p/4239517.html<br>
 * Support for specifying both a day-of-week AND a day-of-month parameter is not
 * implemented.<br>
 * '?' can only be specfied for Day-of-Month or Day-of-Week.<br>
 * quartz表达式写法规则：?只能用在天和星期，有且只能用一次，年可为空，*表示并，?表示或<br/>
 * 
 * @author 李强<br/>
 *         1 * * * * ? *<br/>
 *         每秒 允许的通配符[, - * /]<br/>
 *         分钟 允许的通配符[, - * /]<br/>
 *         小时 允许的通配符[, - * /]<br/>
 *         日 允许的通配符[, - * / L W]<br/>
 *         月 允许的通配符[, - * /]<br/>
 *         周 允许的通配符[, - * / L #]<br/>
 *         年 允许的通配符[, - * /] 非必填<br/>
 * 
 *
 */
public class TriggerUtils {
	public static void main(String[] args) {
		String[] crons = new String[] { "1-2 * * * * ? ", "0/1 * * * * ? ", "0/1 * * * * ? *", "0 0 2 1 * ? *",
				"0 0 10,14,16 * * ?", "0 0/30 9-17 * * ? ", "0 0-5 14 * * ? ", "0 15 10 ? * 6#3" };
		for (String cron : crons) {
			try {
				System.out.println(getNextFireTime(cron));
			} catch (Exception e) {
				System.err.println(cron);
			}
		}
	}

	public static Date getNextFireTime(String cronExpression) {
		List<Date> dates = computeFireTimes(cronExpression, 2);
		return dates.get(0);
	}

	public static Date getPreviousFireTime(String cronExpression) {
		List<Date> dates = computeFireTimes(cronExpression, 2);
		Date first = dates.get(0);
		Date second = dates.get(1);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(first);
		calendar.add(Calendar.MILLISECOND, (int) ((first.getTime() - second.getTime())));
		return calendar.getTime();
	}

	public static List<Date> computeFireTimes(String cronExpression, int numTimes) {
		CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
		try {
			cronTriggerImpl.setCronExpression(cronExpression);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Date> dates = org.quartz.TriggerUtils.computeFireTimes(cronTriggerImpl, null, numTimes);
		return dates;
	}
}
