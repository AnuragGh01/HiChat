import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client {
	
	Socket socket;

	BufferedReader br;
	PrintWriter out;
	
	public client() {
		
		try {
			System.out.println("sending req to server");
			socket=new Socket("IP_ADDRESS",PORT);//put ipaddress and port number
			System.out.println("conn done");
			
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()) );
			out=new PrintWriter(socket.getOutputStream());
			
			startReading();
			startWriting();
			
			
			
			
		}catch(Exception e) {
			
		}
		
	}
public void startReading() {
		
		Runnable r1=()->{
			
			System.out.println("reader started...");
			try {
			while(true) {
				
				String msg=br.readLine();
				if(msg.equals("exit")) {
					System.out.println("server terminated the chat");
				   socket.close();
					break;
				}
				
				System.out.println("server: "+msg);
				
				
			}
			}catch(Exception e) {
				//e.printStackTrace();
				System.out.println("connection closed");
			}
			
			
		};
		new Thread(r1).start();
	}

public void startWriting() {
	
	Runnable r2=()->{
		System.out.println("writer started");
		try {
		while(!socket.isClosed()) {
			
				BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
				String content=br1.readLine();
				out.println(content);
				out.flush();

				if(content.equals("exit")) {
					socket.close();
					
					break;
				}
				
			
			
		}
		System.out.println("connection closed");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	};
	new Thread(r2).start();
	
}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("this is client");
		new client();

	}

}

