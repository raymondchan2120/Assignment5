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
		Tuple t = child.next();
		while (t != null)
		{
			tuplesResult.add(t);
			t = child.next();
		}
		
		System.out.println("Result: "+tuplesResult);
		for (int i = 0; i < tuplesResult.size(); i++)
		{
			t = tuplesResult.get(i);
			for (int j = 0; j < t.getAttributeList().size(); j++)
			{
				System.out.print(t.getAttributeName(j) + " ");
				System.out.print(t.getAttributeValue(j) + " ");
			}
			System.out.println();
		}
		
		if (tuplesResult.size() > 0)
		{
			Tuple min = tuplesResult.get(0);
			Tuple cur;
			newAttributeList = min.getAttributeList();
			for (int i = 0; i < newAttributeList.size(); i++)
			{
				if (newAttributeList.get(i).getAttributeName().equals(orderPredicate))
				{
					for (int tupleCnt = 0; tupleCnt < tuplesResult.size(); tupleCnt++)
					{
						cur = tuplesResult.get(tupleCnt);
						if (min.getAttributeValue(i).toString().compareTo(cur.getAttributeValue(i).toString()) > 0)
							min = cur;
						System.out.println(min.getAttributeValue(i));
					}
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