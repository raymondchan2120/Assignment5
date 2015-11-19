package simpledatabase;
import java.util.ArrayList;

public class Join extends Operator
{
	private ArrayList<Attribute> newAttributeList;
	private ArrayList<Attribute> attributeList2;
	private String joinPredicate;
	ArrayList<Tuple> tupleList;
	
	// Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate)
	{
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		attributeList2 = new ArrayList<Attribute>();
		tupleList = new ArrayList<Tuple>();
		
		// collect all the tuples from the left child first
		Tuple t = leftChild.next();
		while (t != null)
		{
			tupleList.add(t);
			t = leftChild.next();
		}
	}
	
	/**
	 * It is used to return a new tuple which is already joined by the common attribute.
	 * @return the new joined tuple
	 */
	// The record after join with two tables
	@Override
	public Tuple next()
	{
		// ask for a tuple
		Tuple tupleToBeJoined = rightChild.next();
		
		if (tupleToBeJoined != null)
		{
			newAttributeList = tupleToBeJoined.getAttributeList();
			attributeList2 = tupleList.get(0).getAttributeList();
			
			// start to find out the common attribute for joining
			for (int i = 0; i < newAttributeList.size(); i++)
			{
				for (int j = 0; j < attributeList2.size(); j++)
				{
					// if common attribute is found
					if (newAttributeList.get(i).getAttributeName().equals(attributeList2.get(j).getAttributeName()))
					{
						// start to find the common value for joining
						for (int tupleCount = 0; tupleCount < tupleList.size(); tupleCount++)
						{
							// if common value is found
							if (newAttributeList.get(i).getAttributeValue().equals(tupleList.get(tupleCount).getAttributeValue(j)))
							{
								// remove the extra one first
								Attribute removable = newAttributeList.get(i);
								newAttributeList.remove(removable);
								
								// join together
								attributeList2 = tupleList.get(tupleCount).getAttributeList();
								for (int count = 0; count < attributeList2.size(); count++)
									newAttributeList.add(attributeList2.get(count));
								
								// return the new tuple
								return tupleToBeJoined;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * The function is used to get the attribute list of the tuple.
	 * @return attribute list
	 */
	public ArrayList<Attribute> getAttributeList()
	{
		if (joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}
}