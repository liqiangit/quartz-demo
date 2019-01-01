package com.liqiangit.quartz.demo;

public enum Time {
	SECOND(0,"秒","秒"),MINUTE(0,"分","分钟"),HOUR(0,"点","小时"),DAY(0,"日","日"),MONTH(0,"月","月"),WEEK(0,"周","周"),YEAR(0,"年","年");
	private int index;
	private String label;
	private String interval;
	public int getIndex() {
		return index;
	}
	public String getLabel() {
		return label;
	}
	public String getInterval() {
		return interval;
	}
	Time(int index,String label,String interval){
		this.index=index;
		this.label=label;
		this.interval=interval;
	}
	public static Time getTime(int i){
		Time[] times=values();
		return times[i];
	}
}
