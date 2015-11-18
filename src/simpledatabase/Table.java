package simpledatabase;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Table extends Operator
{
	private BufferedReader br = null;
	private Tuple tuple;
	String attributeLine, dataTypeLine, tupleLine;
	
	public Table(String from)
	{
		this.from = from;
		
		// Create buffer reader
		try
		{
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/datafile/"+from+".csv")));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// read the attribute line
		try
		{
			attributeLine = br.readLine();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// read the data type line
		try
		{
			dataTypeLine = br.readLine();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
     * Create a new tuple and return the tuple to its parent.
     * Set the attribute list if you have not prepare the attribute list
     * @return the tuple
     */
	@Override
	public Tuple next()
	{
		// read the next record line
		try
		{
			tupleLine = br.readLine();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// set the attribute list and return the tuple (record)
		if (tupleLine != null)
		{
			Tuple newTuple = new Tuple(attributeLine, dataTypeLine, tupleLine);
			newTuple.setAttributeName();
			newTuple.setAttributeType();
			newTuple.setAttributeValue();
			return newTuple;
		}
		else	// if all records are read
			return null;
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList()
	{
		return tuple.getAttributeList();
	}
}