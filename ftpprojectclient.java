import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.util.*;
class ftpprojectclient implements ActionListener,ItemListener{
	File f;
	File f1 = new File("Downloads");
	String fn,filenm,fc;
	StringBuilder sb;
	String dirn,dirn1;
	String pname1[] = f1.list();
	String pname;
	JPanel pnl;
	JLabel lbltle,lblud;
	JFrame jf;
	JTextField txtfn;
	JTextArea ja,ja1,ja2;
	JButton btnu,btnd,btndel,df,btndel1,con,res;
	JToolBar jtb;
	JComboBox jcb;
	Font fnt;
	Socket s;
	InputStreamReader in;
	OutputStream out;
	BufferedReader br;
	PrintWriter pw;
	JFileChooser jc = new JFileChooser();
	int flag=0;
	public ftpprojectclient(){
		jc.setDialogTitle("Choosing CLIENT directory");
		int returnVal=jc.showOpenDialog(jf);
		jc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		String path=jc.getSelectedFile().getAbsolutePath();
		String filenaming=jc.getSelectedFile().getName();
		System.out.println(filenaming);
		int j;
		dirn=path.replace("\\","/");
		System.out.println(dirn);  
		j=dirn.lastIndexOf("/");
		System.out.println(j);
		for(int i=0;i<dirn.length();i++){
			if(i<=(j-1)){
				dirn.replace("dirn.charAt(i)","");
			}
		}
		System.out.println(dirn);
		sb=new StringBuilder(dirn);
		sb.delete(j,dirn.length());
		System.out.println(sb);
		dirn=sb.toString();
		System.out.println(dirn);
		f=new File(dirn);
		pname=Arrays.toString(f.list());
		DefaultMutableTreeNode abt = new DefaultMutableTreeNode("About");
		DefaultMutableTreeNode sr = new DefaultMutableTreeNode("SERVER");
		DefaultMutableTreeNode cl = new DefaultMutableTreeNode("CLIENT");
		abt.add(sr);
		abt.add(cl);
		jtb = new JToolBar();
		JTree tr = new JTree(abt);
		jf = new JFrame("FTP");
		jf.setLayout(new BorderLayout());
		jf.setSize(2000,2000);
		pnl=new JPanel(null);
		df = new JButton("Files");
		jtb.add(df);
		jtb.setRollover(true);
		jtb.addSeparator();
		JScrollPane jp = new JScrollPane();
		jf.add(jp);
		jf.add(jtb,BorderLayout.NORTH);
		jf.add(tr,BorderLayout.SOUTH);
		fnt=new Font("Times New Roman",Font.BOLD,25);
		lbltle=new JLabel("FTP CLIENT");
		lbltle.setFont(fnt);
		lbltle.setBounds(225,35,200,30);
		pnl.add(lbltle);
		lblud=new JLabel("ENTER  FILE-NAME :");
		lblud.setBounds(100,100,150,35);
		pnl.add(lblud);
		txtfn=new JTextField();
		txtfn.setBounds(300,100,260,25);
		txtfn.setText(filenaming);
		pnl.add(txtfn);
		btnu=new JButton("UPLOAD");	
		btnu.setBounds(150,200,120,35);
		pnl.add(btnu);
		con=new JButton("CONFIRM");
		con.setBounds(430,135,120,25);
		pnl.add(con);
		res=new JButton("RESET");
		res.setBounds(300,135,120,25);
		pnl.add(res);
		ja = new JTextArea();
		ja.setBounds(100,75,250,20); 
		ja.setText("REQUEST:");
		pnl.add(ja);
		btnd=new JButton("DOWNLOAD");
		btnd.setBounds(320,200,120,35);
		pnl.add(btnd);
		btndel=new JButton("Delete from client's directory");
		btndel.setBounds(500,200,320,35);
		pnl.add(btndel);
		btndel1=new JButton("Delete from server's directory");
		btndel1.setBounds(500,300,320,35);
		pnl.add(btndel1);
		Font font = new Font("Segoe Script",Font.BOLD,15);
		ja1 = new JTextArea();
		ja1.setBounds(850,50,1050,800);
		ja2 = new JTextArea();
		ja2.setBounds(10,500,700,250);
		ja2.setText("*************");
		ja2.append("\nInstructions:");
		ja2.append("\n*************");
		ja2.append("\n1.Click on File icon on the toolbar to get the filenames of .txt files that are present in server and client directory");
		ja2.append("\n2.Click on the About Checkbox on toolbar to know about server (or) client,1 at a time");
		ja2.append("\n3.Enter the filename to be uploaded or deleted in textarea field that's provided near label (Enter the file name)");
		ja2.append("\n4.Click on any 1 button for an action(download or upload)");
		ja2.append("\n5.Click on Delete from server's directory button to delete a file residing in server's directory");
		ja2.append("\n6.Click on Delete from client's directory button to delete a file residing in client's directory");
		pnl.add(ja1);
		pnl.add(ja2);
		jcb = new JComboBox(new String[] {"ABOUT","SERVER","CLIENT"});  
		btnu.addActionListener(this);
		btnd.addActionListener(this);
		btndel.addActionListener(this);
		btndel1.addActionListener(this);
		res.addActionListener(this);
		con.addActionListener(this);
		df.addActionListener(this);
		jcb.addItemListener(this);
		jtb.add(jcb);
		jtb.add(tr);
		jf.add(pnl);
		jf.setVisible(true);
		try{
			s=new Socket("localhost",100);
			br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw=new PrintWriter(s.getOutputStream(),true);
			out=s.getOutputStream();
		}
		catch(Exception e){
			System.out.println("ExCEPTION :"+e.getMessage());
		}
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==con){
			flag=1;
		}
		if(e.getSource()==res){
			txtfn.setText(" ");
		}
		if(e.getSource()==btnu){
			try{
				if(flag==1){
					ja.append("File upload request");
					filenm=txtfn.getText();
					pw.println(filenm);
					FileInputStream  fis=new FileInputStream(filenm);
					byte[] buffer=new byte[1024];
					int bytes=0;
					ja.append("File upload request");   
					while((bytes=fis.read(buffer))!=-1){
							out.write(buffer,0,bytes);
						}
					fis.close();
				}
			}
			catch(Exception exx){
				System.out.println(exx.getMessage());
			}
		}
		if(e.getSource()==btnd){
			try{
				if(flag==1){
					ja.append("File download request");
					File dir=new File(dirn);
					if(!dir.exists()){
						dir.mkdir();
					}
					else{
					}
				boolean done=true;
				filenm=txtfn.getText();
				fn=new String("#"+filenm+"#");
				//System.out.println(filenm);
				pw.println(fn);
				File f=new File(dir,filenm);
				FileOutputStream fos=new FileOutputStream(f);
				DataOutputStream dops=new DataOutputStream(fos);
				while(done){
					fc=br.readLine();
					if(fc==null){
						done=false;
					}
					else{
						dops.writeChars(fc);
						//  System.out.println(fc);
					}
				fos.close();
				}	
				}
			}
			catch(Exception exx){
			}
		}
		if(e.getSource()==btndel){
			try{
				if(flag==1){
					ja.append("File delete request");
					String g="."+Integer.toString(sb.length())+"["+sb+"]"+".";
					//pw.println(g);
					String fname;
					fname=txtfn.getText();
					//try{
					pw.println("@"+g+fname+"@");
					//}
				}
			}
			catch(Exception edel){
		    }	
		}
		if(e.getSource()==btndel1){
			try{
				if(flag==1){
					ja.append("File delete request");
					String fname1;
					fname1=txtfn.getText();
					//try{
					pw.println("$"+fname1+"$");
					//}
				}
			}
			catch(Exception edel1){
			}
		}
		int j=1;
		int k=1;
		int l=1;
		int m=1;
		if(e.getSource()==df){
		   ja1.setText("\n\t\t\t\t*******************************************************");
		   ja1.append("\n\t\t\t\t\tClient Directory files:");
		   ja1.append("\n\t\t\t\t*******************************************************");       
		   ja1.append("\n");
		   pname=pname.replace("["," ");
		   pname=pname.replace("]"," ");
		   String pnamea[]=pname.split(" ");
		   for(int i=1;i<pnamea.length;i++){
			   ja1.append(Integer.toString(j++));
			   ja1.append(". ");
			   ja1.append( pnamea[i]);
			   ja1.append("           ");
			   k++;
			   if(k%6==0){
				   ja1.append("\n");
				}
			}
			ja1.append("\n\t\t\t\t*******************************************************");
			ja1.append("\n\t\t\t\t\tServer Directory files:");
			ja1.append("\n\t\t\t\t*******************************************************");
			ja1.append("\n");
			for(int i=0;i<pname1.length;i++){
				//if(pname1[i].endsWith(".txt")){
				ja1.append(Integer.toString(l++));
				ja1.append(". ");
				ja1.append(pname1[i]);
				ja1.append("           ");
				m++;
				if(m%6==0){
					ja1.append("\n");
				//}
				}
			}
		}
   }
    public void itemStateChanged(ItemEvent ie){
		if(jcb.getSelectedItem()=="SERVER"){
			ja1.setText("Server can upload the necessary files to the client's directory according to");
			ja1.append("\nthe client's request(i.e.filename) and also download the files that client sends to it, in its directory");
			ja1.append("\nServer's directory is-->");
			ja1.append("Downloads");
		}
		else if (jcb.getSelectedItem()=="CLIENT"){
			ja1.setText("Client can upload their files to the server's directory,");
			ja1.append("\ndownload files from the server's directory and delete file in server's directory");
			ja1.append("\nClient's directory is-->");
			ja1.append(dirn);
		}
	}

 
	public static void main(String args[]){
		ftpprojectclient ftpc = new ftpprojectclient();
		//ftpc.setSize(600,300);
		//ftpc.show();
	}
}
