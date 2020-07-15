package org.bytebrain.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	private static ThreadLocal<SimpleDateFormat> dateFormatOut = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("dd-MM-yyyy HH:mm");
        }
    };
    
    private static ThreadLocal<SimpleDateFormat> dateFormatIn = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmm");
        }
    };

    public String formatDate(Date date) {
        return dateFormatOut.get().format(date);
    }
	
	public static void main(String[] args) throws ParseException {
		
		String inDateAsString = "202007151229";
		
		Date inDate = dateFormatIn.get().parse(inDateAsString);
		
		DateUtil dateUtil = new DateUtil();
		String formatDate = dateUtil.formatDate(inDate);
		System.out.println(formatDate);
		
		
	}
	
	

}
