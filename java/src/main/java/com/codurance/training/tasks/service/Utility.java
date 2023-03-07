package com.codurance.training.tasks.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface Utility {
	public Date parseDate(String deadline);
	public String parseDateToString(Date date);
	public boolean checkIdValidity(String id);
}
