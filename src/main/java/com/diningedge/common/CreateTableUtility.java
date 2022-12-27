package com.diningedge.common;

public class CreateTableUtility {

	public static String createTableTest(String vendor) {
		String text;
		

		text = "<p>\n</p>"+"<table width='60%' border='1' align='left'>" + "<tr align='center'>"
				+ "<td width='25%'><b>Restaurant <b></td>" + "<td width='5%'><b> :: <b></td>"
				+ "<td width='25%'><b>Test automation<b></td>" + "</tr>" + "<tr align='center'>"
				+ "<td width='25%'><b>Location <b></td>" + "<td width='5%'><b> :: <b></td>"
				+ "<td width='25%'><b>loc10<b></td>" + "</tr>" + "<tr align='center'>"
				+ "<td width='25%'><b>Vendor <b></td>" + "<td width='5%'><b> :: <b></td>" + "<td width='25%'><b>"
				+ vendor + "<b></td>" + "</tr></table>\n\n\n\n"+"<br></br>";

		return text;
	}

}
