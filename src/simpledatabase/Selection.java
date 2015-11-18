package simpledatabase;
import java.util.ArrayList;

public class Selection extends Operator
{
	ArrayList<Attribute> attributeList;
	String whereTablePredicate;
	String whereAttributePredicate;
	String whereValuePredicate;
	
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
		// start to ask for a tuple
		Tuple tupleToBeSelected = child.next();
		
		// if the table is the destination table
		String table = child.from;
		if (table.equals(whereTablePredicate))
		{
			while (tupleToBeSelected != null)
			{
				attributeList = tupleToBeSelected.getAttributeList();
				
				// check whether the record fulfills the requirement or not
				for (int i = 0; i < attributeList.size(); i++)
				{
					if (attributeList.get(i).getAttributeName().equals(whereAttributePredicate))
					{
						// if the requirement is fulfilled, return the tuple
						if (attributeList.get(i).getAttributeValue().equals(whereValuePredicate))
						{
							return tupleToBeSelected;
						}
					}
				}
				
				// continue to ask for tuples until a required tuple is found
				tupleToBeSelected = child.next();
			}
			
			return null;
		}
		else	// if the table is not the destination table, checking is not needed
			return tupleToBeSelected;
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