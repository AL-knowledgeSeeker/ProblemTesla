package AppleCar.ProblemTesla;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AppTest extends BaseClass {

	@Test
	public void getBlueTeslas() {

		String teslatotal = "";
		String notes = "";
		int cnt = 0;
		Response response = given().when().get().then().statusCode(200).and().extract().response();
		JsonPath jsonPath = new JsonPath(response.toString());
		int carCount = jsonPath.getInt("Car.size");
		for (int CarCount = 0; CarCount < carCount; CarCount++) {
			if (jsonPath.getString("Car[" + CarCount + "].metadata.Color").equalsIgnoreCase("blue")
					&& jsonPath.getString("Car[" + CarCount + "].make").equalsIgnoreCase("tesla")) {
				teslatotal = jsonPath.getString("Car[" + CarCount + "]");
				notes = jsonPath.getString("Car[" + CarCount + "].metadata.Notes");
				System.out.println("Blue Tesla- " + teslatotal);
				System.out.println("Notes-" + notes);
			}
			if (jsonPath.getString("Car[" + CarCount + "].make").equalsIgnoreCase("tesla")) {
				teslatotal = jsonPath.getString("Car[" + CarCount + "]");
				cnt = cnt + 1;
				notes = jsonPath.getString("Car[" + CarCount + "].metadata.Notes");
			}

		}
		System.out.println("Total Teslas recieved- " + cnt);

	}

	@Test
	public void getPrice() {
		int per_day_rental_cost;
		String make = "";
		new ArrayList<Float>();
		Response response = given().when().get().then().statusCode(200).and().extract().response();
		JsonPath jsonPath = new JsonPath(response.toString());

		int carCount = jsonPath.getInt("Car.size");
		HashMap<Integer, ArrayList<String>> hm = new HashMap<Integer, ArrayList<String>>();

		for (int cars = 0; cars < carCount; cars++) {
			per_day_rental_cost = jsonPath.get("Car[" + cars + "].perdayrent.Price");
			jsonPath.get("Car[" + cars + "].perdayrent.Discount");
			make = jsonPath.get("Car[" + cars + "].make");
			System.out.println(make);
			ArrayList<String> al = null;
			if (!hm.containsKey(per_day_rental_cost)) {
				al = new ArrayList<String>();
			} else {
				al = hm.get(per_day_rental_cost);
			}
			al.add(make);
			hm.put(per_day_rental_cost, al);
		}
		List<Entry<Integer, ArrayList<String>>> entryList = new ArrayList<>(hm.entrySet());
		Collections.sort(entryList, new Comparator<Entry<Integer, ArrayList<String>>>() {
			@Override
			public int compare(Entry<Integer, ArrayList<String>> e1, Entry<Integer, ArrayList<String>> e2) {
				return e1.getKey().compareTo(e2.getKey());
			}
		});
		System.out.println("Lowest price cars for the day- " + entryList.get(0));
		System.out.println("Finsh");
	}

	@Test
	public void getPriceAfterDiscount() {
		int per_day_rental_cost;
		int discount;
		String make = "";
		new ArrayList<Float>();
		Response response = given().when().get().then().statusCode(200).and().extract().response();
		JsonPath jsonPath = new JsonPath(response.toString());
		int carCount = jsonPath.getInt("Car.size");
		HashMap<Integer, ArrayList<String>> hm = new HashMap<Integer, ArrayList<String>>();
		// calculating discounts for all the cars
		for (int cars = 0; cars < carCount; cars++) {
			per_day_rental_cost = jsonPath.get("Car[" + cars + "].perdayrent.Price");
			discount = jsonPath.get("Car[" + cars + "].perdayrent.Discount");
			per_day_rental_cost -= discount;
			make = jsonPath.get("Car[" + cars + "].make");
			System.out.println(make);
			ArrayList<String> al = null;
			if (!hm.containsKey(per_day_rental_cost)) {
				al = new ArrayList<String>();
			} else {
				al = hm.get(per_day_rental_cost);
			}
			al.add(make);
			hm.put(per_day_rental_cost, al);
		}
		List<Entry<Integer, ArrayList<String>>> entryList = new ArrayList<>(hm.entrySet());
		Collections.sort(entryList, new Comparator<Entry<Integer, ArrayList<String>>>() {
			@Override
			public int compare(Entry<Integer, ArrayList<String>> e1, Entry<Integer, ArrayList<String>> e2) {
				return e1.getKey().compareTo(e2.getKey());
			}
		});
		System.out.println("Lowest price cars after discount for the day- " + entryList.get(0));
	}
}
