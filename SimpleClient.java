import java.net.*;
import java.io.*;
public class SimpleClient implements Runnable
{
	DataInputStream din=null;
	DataOutputStream dout=null;
	static Thread t=null;
	Socket s=null;
	SimpleClient(Socket s)
	{
		this.s=s;
		t=new Thread(this,"Client");
		t.start();
		
	}
	public void run()
	{
		try
		{
			din=new DataInputStream(s.getInputStream());
			new ReadClient(din);
			dout=new DataOutputStream(s.getOutputStream());
			new WriteClient(dout);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args)throws IOException
	{
		Socket s=new Socket("localhost",1234);
		new SimpleClient(s);
		if(!t.isAlive())
		{
			s.close();
		}
	}
}
class ReadClient implements Runnable
{
	DataInputStream din=null;
	Thread t=null;
	ReadClient(DataInputStream temp)throws IOException
	{
		din=temp;
		t=new Thread(this,"Read");
		t.start();
	}
	public void run()
	{
		try
		{
			while(true)
			{
				if(din!=null)
				{
					System.out.println(din.readUTF());
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(din!=null)
				din.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
class WriteClient implements Runnable
{
	BufferedReader br=null;
	DataOutputStream dout=null;
	Thread t=null; 
	WriteClient(DataOutputStream temp)throws IOException
	{
		dout=temp;
		br=new BufferedReader(new InputStreamReader(System.in));
		t=new Thread(this,"write");
		t.start();
	}
	public void run()
	{
		try
		{
			while(true)
			{
				if(dout!=null)
				{
					if(br!=null)
					{
						dout.writeUTF("Client :- "+br.readLine());
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(dout!=null)
				dout.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
