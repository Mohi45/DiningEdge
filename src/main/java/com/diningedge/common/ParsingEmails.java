package com.diningedge.common;

import java.util.ArrayList;
import java.util.List;

import com.diningedge.resources.BaseUi;

public class ParsingEmails extends BaseUi {

	public static List<String> parseCurrentVersion(String messageContent) {
		List<String> details = new ArrayList<>();
		details.add(OrderNumber(messageContent));
		log("Order Date : " + OrderDate(messageContent));
		details.add(OrderDate(messageContent));
		details.add(resturantName(messageContent));
		details.add(totalOrderAmount(messageContent));
		return details;
	}

	public static String OrderNumber(String messageContent) {
		messageContent = messageContent.replace("\n", "").replace("\r", "").replace("=", "");
		messageContent = messageContent.replaceAll("<[^>]*>", "");
		String orderId = messageContent.substring(messageContent.indexOf("Order #:") + "Order #:".length(),
				messageContent.indexOf("Restaurant:"));
		orderId = orderId.replaceAll("\\s", "");
		log("orderId #: " + orderId);
		return orderId;
	}

	public static String OrderDate(String messageContent) {
		messageContent = messageContent.replace("\n", "").replace("\r", "").replace("=", "");
		messageContent = messageContent.replaceAll("<[^>]*>", "");
		String orderDate = messageContent.substring(messageContent.indexOf("Order Date") + "Order Date".length(),
				messageContent.indexOf("Order Name:"));
		orderDate = orderDate.replaceAll("[^0-9]", "");
		// log("order Date : " + orderDate);
		return dateFormating(orderDate);
	}

	public static String resturantName(String messageContent) {
		messageContent = messageContent.replace("\n", "").replace("\r", "").replace("=", "");
		messageContent = messageContent.replaceAll("<[^>]*>", "");
		String resturant = messageContent.substring(messageContent.indexOf("Restaurant:") + "Restaurant:".length(),
				messageContent.indexOf("Location:"));
		resturant = resturant.replaceAll("\\s", " ");
		log("Resturant Name : " + resturant.trim());
		return resturant;
	}

	public static String totalOrderAmount(String messageContent) {
		messageContent = messageContent.replace("\n", "").replace("\r", "").replace("=", "");
		messageContent = messageContent.replaceAll("<[^>]*>", "");
		String totalAmount = messageContent.substring(messageContent.lastIndexOf("Total:") + 6);
		log("Total Order Amount : " + totalAmount.trim());
		return totalAmount;
	}

	public static String dateFormating(String date) {
		String mm = date.substring(0, 2);
		String dd = date.substring(2, 4);
		String yyyy = date.substring(4, 8);
		return mm + "/" + dd + "/" + yyyy;
	}

}
