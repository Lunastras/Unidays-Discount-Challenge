/**
 * This is the class that represents an item, with 
 * its given discount.
 */

public class Item 
{
	
	private char name;
	private float price;
	private boolean hasDiscount;
	
	private String discountType; //Type of discount the item has.
	private int discountAmount; //Amount of products required for the discount to apply.
	private float discount; //The promotion per se of the item.
	
	/**
	 * Creates the item with its discount rules.
	 * @param name Name of the item.
	 * @param price Price of the item.
	 * @param hasDiscount Whether or not the product has a discount.
	 * @param discountType The type of discount to be applied.
	 * @param discountAmount Amount required for the discount to apply.
	 * @param discount The actual discount of the product.
	 */
	public Item(char name, float price, boolean hasDiscount, 
			String discountType, int discountAmount, float discount)
	{
		this.name = name;
		this.price = price;
		this.hasDiscount = hasDiscount;
		this.discountType = discountType;
		this.discountAmount = discountAmount;
		this.discount = discount;
	}

	/**
	 * Get Name.
	 * @return name.
	 */
	public char GetName()
	{
		return name;
	}
	/**
	 * Get the price with discount for specified amount of this item.
	 * @param amount.
	 * @return Discounted price.
	 */
	public float GetPrice(int amount)
	{
		if(hasDiscount) 
		{
			
			switch(discountType)
			{
				case "free": return ((amount / (discountAmount + discount) * discount) 
								+ (amount % (discountAmount + discount))) * price ;
						
				case "for": return ((amount / discountAmount) * discount 
									+ (amount % discountAmount))* price;
					
				case "forPrice": return (amount / discountAmount) * discount 
										+ (amount % discountAmount) * price;
				default: return price;
			}
			
		} else 
		{
			return amount * price;
		}
	}
}
