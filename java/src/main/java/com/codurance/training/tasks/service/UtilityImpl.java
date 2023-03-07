package com.codurance.training.tasks.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilityImpl implements Utility {
	@Override
	public Date parseDate(String deadline) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd-MM-yyyy").parse(deadline);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	@Override
	public boolean checkIdValidity(String id) {
		if(id.indexOf(" ") != -1)
			return false;
		Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
		Matcher matcher = pattern.matcher(id);
		return !(matcher.find());
	}

	@Override
	public String parseDateToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		return formatter.format(date);
	}
}
