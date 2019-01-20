import java.net.*;
import java.io.*;
public class SimpleServer implements Runnable
{
	DataOutputStream dout=null;
	DataInputStream din=null;
	static Thread t=null;
	Socket s=null;
	public SimpleServer(Socket s)
	{
		this.s=s;
		t=new Thread(this,"Server");
		t.start();
	}
	public void run()
	{
		try
		{
			dout=new DataOutputStream(s.getOutputStream());
			din=new DataInputStream(s.getInputStream());
			dout.writeUTF("Server :- Connection Established Successfully");
			new WriteServer(dout);
			new ReadServer(din);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}	
	}
	public static void main(String[] args)throws IOException
	{
		ServerSocket srv=new ServerSocket(1234);
		Socket s=srv.accept();
		new SimpleServer(s);
		if(!t.isAlive())
		{		
			if(s!=null)
			{
				s.close();
			}
			if(srv!=null)
			{
				srv.close();
			}
		}
	}
}
class ReadServer implements Runnable
{
	DataInputStream din=null;
	Thread t=null;
	ReadServer(DataInputStream temp)throws IOException
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
				{
					din.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
class WriteServer implements Runnable
{
	BufferedReader br=null;
	DataOutputStream dout=null;
	Thread t=null; 
	WriteServer(DataOutputStream temp)throws IOException
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
						dout.writeUTF("Server :- "+br.readLine());
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
				{
					dout.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
