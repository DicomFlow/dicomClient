package br.ufpb.dicomflow.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {


	public static String dateToString(Date date, String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);

	}

	public static Date stringToDate(String date, String format) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.parse(date);
	}

	public static String dateToMailDateString(Date date){
		return dateToString(date, "EEE, dd MMM yyyy hh:mm:ss Z");
	}

	public static String dateToTimestamp(Date date){
		return dateToString(date, "yyyy-MM-dd hh:mm:ssZ");
	}

	public static void main(String[] args) {
		System.out.println(dateToMailDateString(new Date()));
		System.out.println(dateToTimestamp(new Date()));
	}

}
