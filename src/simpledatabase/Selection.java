package simpledatabase;
import java.util.ArrayList;

public class Selection extends Operator
{
	ArrayList<Attribute> attributeList;
	String whereTablePredicate;
	String whereAttributePredicate;
	String whereValuePredicate;
	boolean next = true;
	
	public Selection(Operator child, String whereTablePredicate, String whereAttributePredicate, String whereValuePredicate)
	{
		this.child = child;
		this.whereTablePredicate = whereTablePredicate;
		this.whereAttributePredicate = whereAttributePredicate;
		this.whereValuePredicate = whereValuePredicate;
		attributeList = new ArrayList<Attribute>();
	}
	
	/**
     * Get the tuple which match to the where condition
     * @return the tuple
     */
	@Override
	public Tuple next()
	{
		// ask for a record
		Tuple t = child.next();
		
		if (child.from.equals(whereTablePredicate))
		{
			while (t != null)
			{
				// System.out.println("not null in selection: " + t);
				attributeList = t.getAttributeList();
				
				for (int i = 0; i < attributeList.size(); i++)
				{
					// System.out.println(attributeList.get(i).getAttributeName()+" vs "+whereAttributePredicate);
					if (attributeList.get(i).getAttributeName().equals(whereAttributePredicate))
					{
						if (attributeList.get(i).getAttributeValue().equals(whereValuePredicate))
						{
							System.out.println("return! " + t);
							return t;
						}
					}
				}
				
				t = child.next();
			}

			// System.out.println("null in selection: " + t);
			return null;
		}
		else
			return t;
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList()
	{
		return(child.getAttributeList());
	}
}