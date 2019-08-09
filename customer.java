package comp6441;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class customer extends Thread {
	private String name;
	private int value;
	private int totalValue;
	private LinkedBlockingQueue<String> threadCommunication;
	private ArrayList<String>Banklist= new ArrayList<String>();
	private ArrayList<String>totalBanklist;
	private ArrayList<String>rejectedBankList = new ArrayList();;
	public customer(String key,int Value,int total,LinkedBlockingQueue<String> lbq,ArrayList<String> banks)
	{
		name=key;
		value=Value;
		threadCommunication=lbq;		
		totalValue=total;
		totalBanklist=banks;
		for(String values:banks)
		{
			Banklist.add(values);
		}
	}
	public  boolean sendfirstData() throws InterruptedException
	{
		Random random = new Random();
		int amountRequest = (random.nextInt(50)+1);
		if(this.bankcheck())
		{	
			System.out.println();
		int randomBankIndex = (random.nextInt(this.Banklist.size()));
		String randomBank=this.Banklist.get(randomBankIndex);	
		String data=randomBank+" "+ amountRequest+" "+this.name;
		try {
//			Thread.sleep(20);
			//System.out.println("hello");
			threadCommunication.put(data);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		}
		else
		{
			money.print(this.name+" was only able to borrow "+ (this.totalValue-this.value) +" dollar(s). Boo Hoo!");				
			
			threadCommunication.put("end"+ " "+ this.name);
			
			return false;	
		}
	}
	public boolean sendData() throws InterruptedException
	{
		
		Random random = new Random();
		if (this.value > 50)
		{
			if(this.bankcheck())
			{
				int y= this.Banklist.size();
				int amountRequest = (random.nextInt(50)+1);
				int randomBankIndex = ThreadLocalRandom.current().nextInt(this.Banklist.size());
//				Thread.sleep(50);
				try {
					threadCommunication.put(this.Banklist.get(randomBankIndex)+" "+ amountRequest+" "+this.name);
					//threadCommunication.put("rbc"+" "+amountRequest +" "+this.name);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
			else
			{
				money.print(this.name+" was only able to borrow "+ (this.totalValue-this.value) +" dollar(s). Boo Hoo!");
				
				threadCommunication.put("end"+ " "+ this.name);
				
				return false;
			}
		}
		else
		{
			if (this.value ==0)
			{
				money.print(this.name+" has reached the objective of "+ this.totalValue+" dollar(s). Woo Hoo!");
				
				
				threadCommunication.put("end"+ " "+ this.name);
				
				return false;
			}
			else
			{
				if(this.bankcheck())
				{
					int randomBankIndex = ThreadLocalRandom.current().nextInt(this.Banklist.size());
					try {
//						Thread.sleep(50);
						threadCommunication.put(this.Banklist.get(randomBankIndex)+" "+ this.value+" "+this.name);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return true;
				}
				else
				{
					money.print(this.name+" was only able to borrow "+ (this.totalValue-this.value) +" dollar(s). Boo Hoo!");
					
					
					threadCommunication.put("end"+ " "+ this.name);
					
					return false;
				}
			}
			
		}
		
	}
	private boolean bankcheck()
	{
		if( this.Banklist.size() >0)
			return true;
		else
		{
			return false;
		}
	}
	
	public void run()
	{
		boolean ds= false;
		int z=1;
		try {
			//Thread.sleep(10);
		
		if(z==1)
		{
		ds=sendfirstData();
		z++;
		}
		{
//			while (ds)
//			{
//				System.out.println(this.name);
				while(!(threadCommunication.isEmpty()))
				{
//					System.out.println(threadCommunication.peek());
					Iterator<String> listOfData = threadCommunication.iterator();
					while(listOfData.hasNext())
					{
						String s = listOfData.next();
						String[] receive = s.split(" ");
						//money.print("Customer data "+s);
					if(receive[0].equalsIgnoreCase(this.name))
					{						
						
						String request =  s;
						money.print(receive[3]+" "+receive[1]+" a loan of " +receive[2]+" dollars from "+ receive[0]);
						
						int requestAmount=Integer.parseInt(receive[2]);
						//money.print(receive[1]);
						if(receive[1].equalsIgnoreCase("approves"))
						{
							String requestBank = receive[3];
							this.value=this.value-requestAmount;
							try {
								ds=sendData();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else if(receive[1].equalsIgnoreCase("denies"))
						{
							String requestBank = receive[3];
						//	money.print(requestBank);
							int i= this.Banklist.indexOf(requestBank);
							if(i!=-1)
							{
							this.Banklist.remove(i);
							}
							try {
								 ds=sendData();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						listOfData.remove();
					}
				}
			
				
			
				
					//listOfData.remove();		
//		}
			//money.print("thissssssss class"+this.name+this.value);
			}
	}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
 }
}