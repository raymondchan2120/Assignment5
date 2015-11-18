package simpledatabase;
import java.util.ArrayList;

public class Join extends Operator
{
	private ArrayList<Attribute> newAttributeList;
	private ArrayList<Attribute> list;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;
	
	// Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate)
	{
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		list = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();
		
		// collect all the tuples from the left child first
		Tuple t = leftChild.next();
		while (t != null)
		{
			tuples1.add(t);
			t = leftChild.next();
		}
	}
	
	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	// The record after join with two tables
	@Override
	public Tuple next()
	{
		Tuple t = rightChild.next();
		if (t != null)
		{
			System.out.println(tuples1);
			// find out the common attribute
			newAttributeList = t.getAttributeList();
			list = tuples1.get(0).getAttributeList();
			for (int i = 0; i < newAttributeList.size(); i++)
			{
				for (int j = 0; j < list.size(); j++)
				{
					// if common attribute is found
					if (newAttributeList.get(i).getAttributeName().equals(list.get(j).getAttributeName()))
					{
						System.out.println("common attribute found: "+list.get(j).getAttributeName());
						for (int tupleCnt = 0; tupleCnt < tuples1.size(); tupleCnt++)
						{
							// if common value is found
							if (newAttributeList.get(i).getAttributeValue().equals(tuples1.get(tupleCnt).getAttributeValue(j)))
							{
								// System.out.println("common value found: "+tuples1.get(tupleCnt).getAttributeValue(j));
								Attribute check = newAttributeList.get(i);
								// System.out.println(check.getAttributeName()+" "+check.getAttributeValue());
								
								// System.out.println(newAttributeList);
								newAttributeList.remove(check);
								// System.out.println(newAttributeList);
								list = tuples1.get(tupleCnt).getAttributeList();
								for (int count = 0; count < list.size(); count++)
									newAttributeList.add(list.get(count));
								// System.out.println("return the joined one: "+t);
								/*for (int k = 0; k < t.getAttributeList().size(); k++)
								{
									System.out.print(t.getAttributeName(k) + " ");
									System.out.print(t.getAttributeValue(k) + " ");
								}
								System.out.println();*/
								return t;
							}
						}
					}
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
		if (joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}
}