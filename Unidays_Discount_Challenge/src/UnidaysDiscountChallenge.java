/**
 * This class contains the current shopping cart of a user and the products 
 * available for everybody. 
 * 
 * 							............THE CONSTRUCTORS............
 * 
 * The class is first instantiated with a given set of pricing rules found in the passed through the constructor. 
 * It can also be instantiated without creating any pricing rules, but it is not recommended. That constructor
 * only exists if the user wants to create another shopping cart without adding any other products to the
 * available items list.
 * 
 * New items with their own discount rules can be added by calling the constructor and passing in an input file (type File). 
 * With this comes the most glaring flaw of this class, that being users who created their shopping carts before 
 * the inclusion of the new items cannot purchase the added products. However, the program is secure from crashing if
 * an old user wants to add a new item to their basket, as the class will simply ignore the request and go to the next one.
 * 
 * The format of the input file for each line is the following, all separated by a space
 * 
 * 
 *	 	(char)'name', (float)'price' (String)'whether it has a discount or not'
 * 		(int)'amount required for the discount to apply'
 * 		(String)'the discount type'
 *   	(float)'the actual discount'
 *   
 *  (I)   This is the name of the item. It must be a char. Items NEED to have different names, 
 *  	  otherwise you will encounter bugs.
 *  
 *  (II)  This is the price of the item.
 *  
 *  (III) What indicates whether or not an item has a discount is the third element, which is either 
 *  	  a 'disc' (has discount) or '*' (does not have discount).
 * 		  If it has one, then the line will contain 3 more elements describing the discount, otherwise,
 *  	  the program continues to read the next items. 
 *   
 *  (IV)  This is the amount of items required to be bought in order for the discount to be applied.
 *   
 *  (V)   We have 3 discount types, (NOTE, 'a' and 'b' represent (IV) and (VI) respectively):
 *  	
 *  		 a for b (if you take 'a' items, you get them for the price of 'b' amount of items)
 *  		 a forPrice b (if you take 'a' items, you get them for the price of 'b')
 *  		 a free b (if you take 'a' items, you get 'b' items for free)
 *  
 *  (VI)  The final (and perhaps most confusing) element of the values above is 'the actual discount'.
 *  	  The meaning of this variable solely depends on the discount type that the item has, which is 
 *  	  explained in (II), being the value. 
 *  
 *  
 *  NOTE: If the previous instructions are not properly followed, the program is prone to crashing and throw
 *  	  a 'TypeMiscmatch' exception while reading the file.
 *  
 *   				............ADDING ITEMS TO THE BASKET............
 *   
 *   The method 'void AddToBasket' takes an argument of type String, and it considers every element in the string to
 *   be a product. It will search the available products and see if it exists, if so, it increments it in the
 *   'productAmount' in its respective index. If it does not exist, it will simply ignore the item and go to
 *   the next one. 
 *   
 *   As stated previously, a given user may only have access to items that existed when he instantiated 
 *   the class.
 *   
 *                   ................GETTING THE PRICES................
 *                   
 *   For this, we use the interface 'Prices', which has the 'float Total()' and 'float DeliveryCharge()'
 *   methods. Simple enough, 'Total' returns the price of all products in the shopping cart, while 'DeliveryCharge'
 *   the delivery price.
 *   
 *   					..............CLEARING THE SHOPPING CART................
 *   
 *   The 'EmptyBasket()' is quite self explanatory, it removes all items from the user's shopping cart.
 *   By that, I mean it gets rid of any incrementation we did in 'AddToBasket' and sets every value in 
 *   'productAmount[]' to 0.	
 */


import java.util.ArrayList;
import java.io.*;
import java.util.Scanner; 

public class UnidaysDiscountChallenge
{
	
	private final String ERROR_FILENOTFOUND = "ERROR, could not find the file";
	
	/*
	 * The reason why the list of products is static is because we do not
	 * want to have duplicates of it when other users use the store.
	 */
	private static ArrayList<Item> products = new ArrayList<Item>();
	
	private int[] productAmount; //Stores the amount of products added to the basket.
	private float deliveryPrice = 7; //Default delivery price.
	private float freeDeliveryReq = 50; //Price required for the delivery to be free.
	
	/**
	 * We use this constructor in case we want to have a new 
	 * basket but keep the current products and discounts.
	 */
	public UnidaysDiscountChallenge()
	{
		productAmount  = new int[products.size()];
		EmptyBasket();
	}
	
	/**
	 * Creates the all the items in the database with
	 * their respective discounts found in pricingRules.
	 * @param pricingRules.
	 */
	public UnidaysDiscountChallenge(File pricingRules){
		
		try 
		{
			Scanner sc = new Scanner(pricingRules);
			
			while(sc.hasNext())
			{
				char item = sc.next().charAt(0);
				float price = sc.nextFloat();
				String hasDisc = sc.next();
				
				int discountAmount = 0; //Amount required to apply discount.
				String discType = ""; //Type of discount.
				int discount = 0; //The actual discount that will change the final price.
				
				/* Because the following variables will only have a value if the product
				 * has a discount, we first check if it has one.
				 */
				if(hasDisc.equals("disc"))
				{
					discountAmount = sc.nextInt();
					discType = sc.next();
					discount = sc.nextInt();
				}
				
				products.add(new Item(item, price, hasDisc.equals("disc"), discType, discountAmount,discount));
			}
			
			sc.close();
			
			productAmount  = new int[products.size()];
			EmptyBasket();
			
		} catch(FileNotFoundException e)
		{
			System.out.println(ERROR_FILENOTFOUND);
		}	
	}
	
	/**
	 * Empties the basket.
	 */
	public void EmptyBasket()
	{
		for(int i = 0; i < productAmount.length; i++)
		{
			productAmount[i] = 0;
		}
	}
	
	/**
	 * The items in a will only be added to
	 * the list if they exist, otherwise, the 
	 * items that do not exist will be ignored.
	 * @param a.
	 */
	public void AddToBasket(String a) 
	{		
		for(int i = 0; i < a.length(); i++) 
		{
			for(int j = 0; j < productAmount.length; j++)
			{		
				if(products.get(j).GetName() == a.charAt(i))
				{
					productAmount[j]++;
					break;
				}
			}
		}
	}
	
	/**
	 * The interface used to get the prices.
	 */
	public Prices CalculateTotalPrice = new Prices() 
	{
		
		/**
		 * Returns the total price of the products.
		 * @return Price
		 */
		public float Total()
		{
			float price = 0;
			
			for(int i = 0; i < productAmount.length; i++)
			{
				price += products.get(i).GetPrice(productAmount[i]);
			}
			
			return price;
		}
		
		/**
		 * Returns the delivery price.
		 * @return Price
		 */
		public float DeliveryCharge()
		{
			float price = Total();
			
			if(price >= freeDeliveryReq || price == 0)
			{
				return 0;
			}
			else
			{
				return deliveryPrice;
			} 	
		}	
	};
}

