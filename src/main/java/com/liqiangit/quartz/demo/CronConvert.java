package com.liqiangit.quartz.demo;

import java.util.Stack;

/**
 * http://cron.qqe2.com/<br/>
 * quartz表达式转换成中文<br/>
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
 */
public class CronConvert {
	public static void main(String[] args) {
		String[] crons = new String[] { "1-2 * * * * ? ", "0/1 * * * * ? ", "0 0 2 1 * ? *", "0 0 10,14,16 * * ?",
				"0 0/30 9-17 * * ? ", "0 0-5 14 * * ? ", "0/1 0 0 0 0 1#1 " };
		for (String cron : crons) {
			// String cron="1-2 * * * * ? ";
			String str = showCron(cron);
			System.out.println(str);
		}
	}

	public static String showCron(String cron) {
		Stack<String> strings = new Stack<String>();
		String[] strs = cron.split(" ");
		for (int i = 0; i < strs.length; i++) {
			String str = strs[i];
			Time time = Time.getTime(i);
			if (str.equals("*") || str.equals("?")) {

			} else if (str.indexOf("-") >= 0) {
				// 周期从 - 秒
				String[] sp = str.split("-");
				String number1 = sp[0];
				String number2 = sp[1];
				strings.push(String.format("从 %s-%s%s", number1, number2, time.getLabel()));
			} else if (str.indexOf("/") >= 0) {
				// 从 秒开始,每 秒执行一次
				String[] sp = str.split("/");
				String number1 = sp[0];
				String number2 = sp[1];
				strings.push(String.format("从%s%s开始,每%s%s执行一次", number1, time.getLabel(), number2, time.getLabel()));
			} else if (str.equals("L")) {
				// 本月最后一天
				strings.push("本月最后一天");
			} else if (str.indexOf("W") >= 0 && str.length() > 1) {
				// 每月 号最近的那个工作日
				String number1 = str.replace("W", "");
				strings.push(String.format("每月%s号最近的那个工作日", number1));
			} else if (str.indexOf("L") >= 0 && str.length() > 1) {
				// 本月最后一个星期
				String number1 = str.replace("L", "");
				strings.push(String.format("本月最后一个星期%s", number1));
			} else if (str.indexOf("#") >= 0) {
				// 第 周 的星期
				String[] sp = str.split("#");
				String number1 = sp[0];
				String number2 = sp[1];
				strings.push(String.format("第%s周的星期%s", number1, number2));
			} else {
				// 指定
				strings.push(String.format("%s%s", str, time.getLabel()));
			}
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < strings.size(); i++) {
			builder.append(strings.pop());
		}
		return builder.toString();
	}
}
