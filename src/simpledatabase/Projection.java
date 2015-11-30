package simpledatabase;
import java.util.ArrayList;

public class Projection extends Operator
{
	ArrayList<Attribute> newAttributeList;
	private String attributePredicate;
	
	// Projection constructor
	public Projection(Operator child, String attributePredicate)
	{
		this.attributePredicate = attributePredicate;
		this.child = child;
		newAttributeList = new ArrayList<Attribute>();
	}
	
	/**
	 * Return the data of the selected attribute as tuple format.
	 * @return tuple
	 */
	@Override
	public Tuple next()
	{
		// ask for a tuple
		Tuple tupleToBeProjected = child.next();
		
		if (tupleToBeProjected != null)
		{
			newAttributeList = tupleToBeProjected.getAttributeList();
			
			// remove the not required attribute(s)
			for (int i = 0; i < newAttributeList.size(); i++)
			{
				if (!newAttributeList.get(i).getAttributeName().equals(attributePredicate))
				{
					newAttributeList.remove(i);
					i = -1;
				}
			}
			
			// return the tuple
			return tupleToBeProjected;
		}
		else	// if all tuples are read
			return null;
	}
	
	/**
	 * The function is used to get the attribute list of the tuple.
	 * @return attribute list
	 */
	public ArrayList<Attribute> getAttributeList()
	{
		return child.getAttributeList();
	}
}