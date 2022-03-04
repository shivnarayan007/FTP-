import java.io.*;
import java.net.*;
class ftpprojectserver{
	public static void main(String args[]){
		int i=1;
        	System.out.println("                             *-*-* FTP SERVER *-*-*                            ");
			System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        	System.out.println("Server has started running");
        	System.out.println("Waiting for connections\n");
        	try{
				ServerSocket ss = new ServerSocket(100);
            		for(;;){
						Socket incoming = ss.accept();
                		System.out.println("New Client Connected with id " + i+" from "+incoming.getInetAddress().getHostName()+"..." );
                		Thread t = new ThreadedServer(incoming,i);
                		i++;
                		t.start();
            		}
         	}
         	catch(Exception e){
			 System.out.println("Error: " + e);
		}
     	}
}
class ThreadedServer extends Thread{
	int n;
    	String c,fn,fc;
    	String filenm,fname,fname1;
    	Socket incoming;
    	int counter;
	//String dirn="c:/FTP SERVER DIRECTORY";
	// String dirn="c:/Users/Shivnarayan V";
	String dirn="Downloads";
	//String dirn1="c:/FTP SERVER DIRECTORY";
    	public ThreadedServer(Socket i,int c){
		incoming=i;
        	counter=c;
    	}
	public void run(){
		try{
					BufferedReader in =new BufferedReader(new InputStreamReader(incoming.getInputStream()));
            	   	PrintWriter out = new PrintWriter(incoming.getOutputStream(), true);
            		OutputStream output=incoming.getOutputStream();
            		fn=in.readLine();
            		c=fn.substring(0,1);
			//String clname=" ";
			/*System.out.println(fn);
			String cc=fn.substring(1,(fn.length()-1));
			clname=cc;
			System.out.println(cc);
			System.out.println(clname);*/
			if(c.equals("#")){
					n=fn.lastIndexOf("#");
                		filenm=fn.substring(1,n);
                		FileInputStream fis=null;
                		boolean filexists=true;
                		System.out.println("Request to download file "+filenm+"recieved from "+incoming.getInetAddress().getHostName()+"...");
                		try{
					fis=new FileInputStream(filenm);
                		}
                		catch(FileNotFoundException exc){
					filexists=false;
                    			System.out.println("FileNotFoundException:"+exc.getMessage());
                		}
                		if(filexists){
					sendBytes(fis, output);
					fis.close();
                		}
             		}
			else if(c.equals("@")){
					String clname=" ";
			    	System.out.println(fn);
		        	String cc=fn.substring(1,(fn.length()-1));
			    	clname=cc;
			    	System.out.println(cc);
			    	System.out.println(clname);
			    	int jl=0,jk=0;
				for(int i=0;i<fn.length();i++){
					if(fn.charAt(i)=='['){
						jl=i;
				    	}
			    	}
				jk=fn.indexOf(']');
				String gg=fn.substring(jl+1,jk);
				System.out.println(gg);
				n=fn.lastIndexOf("@");
			    	fname=fn.substring(1,n);
				String fl=fn.substring(jk+2,(fn.length()-1));
				System.out.println(fname);
				System.out.println("HI"+gg+"/"+fl);
				System.out.println("Request to delete file that's residing in client's directory "+fname+" recieved from "+incoming.getInetAddress().getHostName()+"...");
				//File f = new File("c:/FTP CLIENT DIRECTORY/"+fname);
				File f=new File(gg+"/"+fl);
				f.delete();
			}
			else if(c.equals("$")){
				try{
					n=fn.lastIndexOf("$");
				    	fname1=fn.substring(1,n);
				    	System.out.println("Request to delete file that's residing in server's directory "+fname1+" recieved from "+incoming.getInetAddress().getHostName()+"...");
				    	File f= new File("Downloads/"+fname1);
				    	System.out.println("Hello  "+"Downloads/"+fname1);
				    	f.delete();
				}
				catch(Exception es){
				}
			}
			else{
				try{
					boolean done=true;
                    System.out.println("Request to upload file " +fn+"recieved from "+incoming.getInetAddress().getHostName()+"...");
					File dir=new File(dirn);
					if(!dir.exists()){
						dir.mkdir();
					}
					else{
					}
                    			File f=new File(dir,fn);
                    			FileOutputStream fos=new FileOutputStream(f);
                    			DataOutputStream dops=new DataOutputStream(fos);
					while(done){
						fc=in.readLine();
						if(fc==null){
							done=false;
						}
						else{
							dops.writeChars(fc);
							// System.out.println(fc);
						}
					}
					fos.close();
                		}
                		catch(Exception ecc){
					System.out.println(ecc.getMessage());
                		}
            		}
               		incoming.close();
	        }
		catch(Exception e){
			  System.out.println("Error: " + e);
          	}
	}
	private static void sendBytes(FileInputStream f,OutputStream op)throws Exception{
		byte[] buffer=new byte[1024];
		int bytes=0;
		while((bytes=f.read(buffer))!=-1){
			op.write(buffer,0,bytes);
		}
	}
}
			   