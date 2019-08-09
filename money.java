package comp6441;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.math.*;

public class money extends Thread {	
	private static String threadname;
	private static ArrayList<String> Threadslist = new ArrayList<String>();
	private static ArrayList<String> banks = new ArrayList<String>();
	private static   Map<String, Integer> customers = new HashMap<String, Integer>();
	private static Map<String, Integer> bank = new HashMap<String, Integer>();
	private static LinkedBlockingQueue<String> threadtoCommunication =new LinkedBlockingQueue<String>();
	private static ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<String, Integer>();
	public static void main(String[] args) throws NumberFormatException, IOException {
		fileReader fr= new fileReader();
		customers=fr.Reader(0);
		bank=fr.Reader(1);
		System.out.println("** Customers and loan objectives **");		
		for(Entry<String, Integer> entry : customers.entrySet())
		{	
			System.out.println(entry.getKey()+":"+ entry.getValue());			
		}
		System.out.println("** Banks and financial resources **");
		for(Entry<String, Integer> entry : bank.entrySet())
		{
			banks.add(entry.getKey());
			System.out.println(entry.getKey()+":"+ entry.getValue());			
		}
		m.put("Data", 0);
		threadInitiate();
		
	}
	public static void print(String str)
	{
		System.out.println(str);
	}
	/*public void run()
	{
		int i=1;
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 while(true)
		 {
			 while(!(threadtoCommunication.isEmpty()))
			 {
				 Iterator<String> listOfData = threadtoCommunication.iterator();
					while(listOfData.hasNext())
					{							
						String s = listOfData.next();
						String[] datas=s.split(" ");
//						money.print("Sent data" +" " +s);
//						money.print("class data"+" " +this.name);
						if(datas[0].equalsIgnoreCase("end"))
						{
							listOfData.remove();
							i++;
							if(i== customers.size())
							{
								
							}
							//print(datas[1]+)
							
						}
					}
			 }
			 }
				 
		 }*/
	
	public static void threadInitiate()
	{
		for(Entry<String, Integer> entry : bank.entrySet())
		{
			Bank objBank = new Bank(entry.getKey(),entry.getValue(),threadtoCommunication,customers.size(),m);
			Thread customerThread = new Thread(objBank);
			customerThread.start();
			customerThread.setName(entry.getKey());
		}
		for(Entry<String, Integer> entry : customers.entrySet())
		{
			customer objCustomer = new customer(entry.getKey(),entry.getValue(),entry.getValue(),threadtoCommunication,banks);
			Thread customerThread = new Thread(objCustomer);
			customerThread.start();
			customerThread.setName(entry.getKey());
		}
		
	}
	
		}
		

