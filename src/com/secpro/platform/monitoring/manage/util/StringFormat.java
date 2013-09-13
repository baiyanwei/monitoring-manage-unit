package com.secpro.platform.monitoring.manage.util;

import java.util.Date;

public class StringFormat {
	@SuppressWarnings("deprecation")
	public static String FormatDate(Date date) {
		return date.toLocaleString();
	}
}
