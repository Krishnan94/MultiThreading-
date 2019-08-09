package comp6441;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class fileReader {
	private static Map<String, Integer> bank = new HashMap<String, Integer>();
	private static Map<String, Integer> customer = new HashMap<String, Integer>();
	public static int banksize;
	public static  Map<String, Integer> Reader(int i) throws NumberFormatException, IOException
	{
		// TODO Auto-generated method stub
		if(i==0)
		{
		  BufferedReader inputCustomer = new BufferedReader(new FileReader("D:\\java_work\\comp6441\\src\\Files\\customers.txt"));
			String l = "";
			while ((l = inputCustomer.readLine()) != null) {
				String parts[] = l.substring(l.indexOf("{") + 1, l.indexOf("}")).split("\\,", 2);
				
				customer.put(parts[0],Integer.parseInt(parts[1]));
			}
			inputCustomer.close();
			return  customer;
		}
		else
		{
			 BufferedReader inputBank = new BufferedReader(new FileReader("D:\\java_work\\comp6441\\src\\Files\\banks.txt"));
				String line = "";
				while ((line = inputBank.readLine()) != null) {
					String parts[] = line.substring(line.indexOf("{") + 1, line.indexOf("}")).split("\\,", 2);
					
					bank.put(parts[0],Integer.parseInt(parts[1]));
				}
				inputBank.close();
				banksize=bank.size();
				return bank;
		}
		
	}


}
