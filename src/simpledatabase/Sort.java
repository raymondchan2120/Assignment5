package simpledatabase;
import java.util.ArrayList;

public class Sort extends Operator
{
	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;
	
	public Sort(Operator child, String orderPredicate)
	{
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();
	}
	
	/**
     * The function is used to return the sorted tuple
     * @return tuple
     */
	@Override
	public Tuple next()
	{
		// start to ask for a record
		Tuple tupleToBeSorted = child.next();

		// collect all the tuples from the table first
		while (tupleToBeSorted != null)
		{
			tuplesResult.add(tupleToBeSorted);
			tupleToBeSorted = child.next();
		}
		
		// if the tuple collection is not empty
		if (!tuplesResult.isEmpty())
		{
			Tuple min = tuplesResult.get(0);	// minimum tuple
			Tuple cur;							// current tuple
			newAttributeList = min.getAttributeList();
			
			// find out the destination attribute for sorting
			for (int i = 0; i < newAttributeList.size(); i++)
			{
				if (newAttributeList.get(i).getAttributeName().equals(orderPredicate))
				{
					// find out the minimum tuple
					for (int tupleCount = 0; tupleCount < tuplesResult.size(); tupleCount++)
					{
						cur = tuplesResult.get(tupleCount);
						if (min.getAttributeValue(i).toString().compareTo(cur.getAttributeValue(i).toString()) > 0)
							min = cur;
					}
					
					// remove and return the minimum tuple
					tuplesResult.remove(min);
					return min;
				}
			}
		}
		
		return null;
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList()
	{
		return child.getAttributeList();
	}
}