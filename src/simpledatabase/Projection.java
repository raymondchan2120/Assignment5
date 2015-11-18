package simpledatabase;
import java.util.ArrayList;

public class Projection extends Operator
{
	ArrayList<Attribute> newAttributeList;
	private String attributePredicate;
	
	public Projection(Operator child, String attributePredicate)
	{
		this.attributePredicate = attributePredicate;
		this.child = child;
		newAttributeList = new ArrayList<Attribute>();
	}
	
	/**
     * Return the data of the selected attribute as tuple format
     * @return tuple
     */
	@Override
	public Tuple next()
	{
		// ask for a record
		Tuple t = child.next();
		// System.out.println("projection next() is called: " + t);
		if (t != null)
		{
			// System.out.println("not null: " + t);
			System.out.print("before selection: ");
			newAttributeList = t.getAttributeList();
			for (int i = 0; i < newAttributeList.size(); i++)
			{
				System.out.print(t.getAttributeName(i) + " ");
				System.out.print(t.getAttributeValue(i) + " ");
			}
			System.out.println();
			
			// remove the not required attribute(s)
			for (int i = 0; i < newAttributeList.size(); i++)
			{
				System.out.println(newAttributeList.get(i).getAttributeName()+" vs "+attributePredicate);
				if (!newAttributeList.get(i).getAttributeName().equals(attributePredicate))
				{
					System.out.println("delete!");
					newAttributeList.remove(i);
					i = -1;
				}
			}

			System.out.print("after selection: ");
			for (int i = 0; i < t.getAttributeList().size(); i++)
			{
				System.out.print(t.getAttributeName(i) + " ");
				System.out.print(t.getAttributeValue(i) + " ");
			}
			System.out.println();
			
			// return the tuple
			return t;
		}
		else	// if all records are read
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