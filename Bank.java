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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.InetAddress;

public class Bank extends Thread{
	private String name;
	private int value;
	private int noOfCustomers;
	private LinkedBlockingQueue<String> threadCommunication;
	private ConcurrentHashMap<String, Integer> ConcurrentValue;
	public Bank(String key,int Value,LinkedBlockingQueue<String> lbq, int customerSize,ConcurrentHashMap Data)
	{
		name=key;
		value=Value;
		threadCommunication=lbq;
		noOfCustomers=customerSize;
		ConcurrentValue=Data;
	}
	private static String threadname;
	
	
	public  void run()
	{
		boolean sd=true;
		try {
			Thread.sleep(1000);
		
		
			
//			System.out.println(threadCommunication.peek());
				while(!(threadCommunication.isEmpty()))
						{
//					System.out.println(threadCommunication.peek());
						Iterator<String> listOfData = threadCommunication.iterator();
						while(listOfData.hasNext())
						{							
							String s = listOfData.next();
							String[] datas=s.split(" ");
//							money.print("Sent data" +" " +s);
//							money.print("class data"+" " +this.name);
							if(datas[0].equalsIgnoreCase(this.name))
							{
								
								money.print(datas[2]+" requests a loan of " +datas[1]+" dollar(s) from "+ datas[0]);
								String request = s;
								String requestName = datas[2];																
								int requestAmount=Integer.parseInt(datas[1]);								
								if (requestAmount <= this.value)
								{
									
									try {
										
										threadCommunication.put(requestName+" "+"approves"+" "+requestAmount+" "+this.name );
										this.value =this.value - requestAmount;
										
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								else 
								{
									try {
										
										threadCommunication.put(requestName+" "+"denies"+" "+requestAmount+" "+this.name );
//										this.noOfCustomers=this.noOfCustomers-1;
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								listOfData.remove();
							}
							else if(datas[0].equalsIgnoreCase("end"))
							{
								int key=((int)ConcurrentValue.get("Data"));
								if(((int)ConcurrentValue.get("Data"))!=this.noOfCustomers)
								{
								ConcurrentValue.put("Data",key+1);
								}
//								System.out.println(s);
								listOfData.remove();
							}
						}
			
				
		}
//			System.out.println(ConcurrentValue.get("Data")+ this.name+this.noOfCustomers);
			
//			if(((int)ConcurrentValue.get("Data"))==this.noOfCustomers)
//			{
//				
//				sd=false ;
//			}
//		}
				money.print(this.name+" has "+this.value+" dollar(s) remaining.");
			
		
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	}
}