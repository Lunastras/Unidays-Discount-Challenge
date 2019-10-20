/**
 * This is a small class I wrote to test out the UnidaysDiscountChallenge.java class.
 * Simply execute it and follow the instructions in the console.
 */

import java.io.*;
import java.util.Scanner;

public class Main {
	public static void main(String[] args)
	{
		String input = "";
		Scanner in = new Scanner(System.in);
		File pricingRules = new File("Input.in");
		
		UnidaysDiscountChallenge a = new UnidaysDiscountChallenge(pricingRules);	
		Prices result = a.CalculateTotalPrice;
		float delivery;
		float price;
		
		System.out.println("Hello! Welcome to our store.");
		
		while(!input.equals("close"))
		{
			System.out.println("\n Please write one of the following:");
			System.out.println("'clear' to empty your basket.");
			System.out.println("'price' to see the total price of your basket.");
			System.out.println("Or write a string of products to add. (e.g. ABAC) \n");

			input = in.next();
				
			switch(input)
			{
				case "clear":
					System.out.println("Basket has been emptied!");
					a.EmptyBasket();
					break;
				
				case "price":
					delivery = result.DeliveryCharge();
					price = result.Total();
					System.out.println("The price of the items is " + price + 
							"£, with a delivery of " + delivery + "£, coming to a total of " +
							(delivery + price) + "£.");
					break;
				
				default:
					System.out.println("Items added! (If they exist)");
					a.AddToBasket(input);
			}				
		}
	}
}
