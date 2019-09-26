
	import java.io.File;
	import java.io.PrintStream;
	import java.sql.Connection;
	import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
	import java.sql.Statement;
	import java.text.Format;
	import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
	import java.util.StringTokenizer;
	import javax.mail.BodyPart;
	import javax.mail.Flags;
	import javax.mail.Flags.Flag;
	import javax.mail.Folder;
	import javax.mail.Message;
	import javax.mail.Multipart;
	import javax.mail.Part;
	import javax.mail.Session;
	import javax.mail.Store;
	import javax.mail.internet.ContentType;
	import javax.mail.internet.MimeBodyPart;
	import javax.mail.internet.MimeMultipart;



	public class GetEmailAtt
	  implements Runnable
	{
	  Thread t;
	  static boolean flag = false;
	  int msgcount=0;
	  int mltprtcount=0;
	  static int interval = 0;
	  static String updatesecondaryserver = "";
	  static String host = "";
	  static String username = "";
	  static String password = "";
	  static String body = "";
	  static String msgname = "";
	  static java.sql.Date msgdate;
	  static int msgno = 0;
	  static int totalmsgno = 0;
	  static int msgsize = 0;
	  static String subject = "";
	  static String tbname = "";
	  static String tbname1 = "";
	  static String nokiatable = "";
	  static String tbname2 = "";
	  static String cardId;
	  static String invaliddata;
	  static String stamp;
	  static String orignalstamp;
	  static String dt;
	  static String tm;
	  static String lat;
	  static String lon;
	  static String latdir;
	  static String tstamp;
	  static String sen5;
	  static String londir;
	  static String dd;
	  static String speed;
	  static String distance;
	  static String adc1;
	  static String adc2;
	  static String date1;
	  static String time1;
	  static String insertdate;
	  static String inserttime;
	  static String storeddate;
	  static String storedtime;
	  static String sen1;
	  static String sen2;
	  static String sen3;
	  static String sen4;
	  static String unittype;
	  static String location;
	  static Format frt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  static Format frt1 = new SimpleDateFormat("yyyy-MM-dd");
	  static Format frt2 = new SimpleDateFormat("HH:mm:ss");
	  static String maildate = "";
	  static String maildate2 = "";
	  
	  static String mailtime = "";
	  static String mailtime2 = "";

	  static String dbdriver;
	  static String connstring;
	  static String dbuser;
	  static String dbpass;
	  static String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	  static Properties props = new Properties();
	  static Session session = Session.getDefaultInstance(props, null);
	  private static final long serialVersionUID = 1L;
	  static Connection conn;
	  static Connection conn1;
	  static Connection Mysqlconn;
	  
	  static Statement st;
	  static Statement st2;
	  static Statement stsecond;
	  static Store store;
	  
	  
	  
	  
	  public GetEmailAtt()
	  {
	    this.t = new Thread(this);
	    this.t.start();
	  }
	  
	  public static void download(String[] args)
	  {
	    new GetEmailAtt();
	  }
	  
	  	  
	  @SuppressWarnings("null")
	public void download()
	  {
	    try
	    {
	    	GetConnection();
	    	
	    	String mysqlUrl="jdbc:mysql://localhost:3306/VehicleData";
			String user="root";
			String password="pass";	

			 try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Mysqlconn=DriverManager.getConnection(mysqlUrl,user,password);
					System.out.println("Connected to Mysql");
					
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	      int k = 0;
	      flag = false;

	        
	      host="a.mobileeye.in";
	    	 username="trackfile";
	         password="transworld";
	      
	      StringTokenizer stk = new StringTokenizer(tbname, ",");
	        if (stk.hasMoreTokens()) {
	          tbname1 = stk.nextElement().toString();
	        }
	        if (stk.hasMoreTokens()) {
	          tbname2 = stk.nextElement().toString();
	        }
	        System.out.println(username);
	        System.out.println(k);
	        System.out.println(tbname1);
	        System.out.println(tbname2);
	        
	        System.out.println("host-->" + host);
	        System.out.println("username-->" + username);
	        System.out.println("password-->" + password);
	        
	        store.connect(host, 110, username, password);
	        
	        System.out.println("Connected");
	        Folder folder = store.getFolder("INBOX");
	        System.out.println("Folder--->" + folder);
	        if (folder.isOpen())
	        {
	          System.out.println("Folder is open");
	         // folder.close(true);
	          System.out.println("Closing Folder");
	        }
	        folder.open(2);
	        if (folder.isOpen()) {
	          System.out.println("Folder is open in read write mode");
	        }
	        System.out.println("Read Write operation done on Folder contents");
	        Message[] messages = folder.getMessages();
	        int msgcount=messages.length;
	        System.out.println("messages:"+msgcount);
	        
	        List<String> dateList=new ArrayList<String>();
	        List<String> timeList=new ArrayList<String>();
	        subject = "";
	        for(int i=0; i<msgcount; i++) {
		        String contenttype=messages[i].getContentType();
		        
	            maildate2 = new SimpleDateFormat("yyyy-MM-dd").format(messages[i].getSentDate());
	            dateList.add(maildate2);
	            timeList.add(mailtime2);
	            
	            mailtime2 = new SimpleDateFormat("HH:mm:ss").format(messages[i].getSentDate());
	            

	            System.out.println("mail date&time:" + maildate2+" : "+mailtime2);
	            
	            subject = messages[i].getSubject();
	            System.out.println("Subject:"+subject);
	            
		        if(contenttype.contains("multipart")) {
		        	System.out.println("contenttype:"+contenttype);
			        Multipart multiPart = (Multipart) messages[i].getContent();
		        	 System.out.println("MultiPart length="+multiPart.getCount());
		        	for (int j = 0; j < multiPart.getCount(); j++) {
			        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(j);
		        	    if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
		        	    	String att_name=part.getFileName();
		        	    	String name="emaildate"+maildate2+"emailtime"+mailtime2+"att"+att_name;
		        	    	System.out.println(name+":"+i+"");
		        	    	
		        	    	part.saveFile("/home/ganesh/EmailAttachment/" + name);
		        	    	
		        	    	String query="insert into email_attachments(att_name, email_date, email_time) values(?,?,?)";
		        	    	
		        	    	PreparedStatement pst=Mysqlconn.prepareStatement(query);
		        	    	pst.setString(1, att_name);
		        	    	pst.setString(2, maildate2);
		        	    	pst.setString(3, mailtime2);
		        	    	pst.execute();
		        	    	
		        	    	
		        	    	
//		        	    	File savefile = new File("/home/Ganesh/EmailAttachment/" + name);
//		                      part.saveFile(savefile);
		                      //String path = savefile.getAbsolutePath();
		                      
		        	   }
		        	}
		        }

	        }
	        msgcount = folder.getUnreadMessageCount();
	        System.out.println("messageCount--->" + msgcount);
	        if (msgcount < k) {
	          k = msgcount;
	        }
	        subject = "";
	        for (int i = 0; i < k;) {
	          try
	          {
	                          
	          
	            ContentType ct = new ContentType(messages[i].getContentType());
	            System.out.println("ct----> " + ct);
	            
	            msgno = i + 1;
	            subject = messages[i].getSubject();
	            System.out.println("subject----> " + subject);
	            
	            msgsize = messages[i].getSize();
	            System.out.println("msgsize----> " + msgsize);
	            body = messages[i].getContent().toString();
	            
	            maildate = new SimpleDateFormat("yyyy-MM-dd").format(messages[i].getSentDate());
	            System.out.println("maildate----> " + maildate);
	            mailtime = new SimpleDateFormat("HH:mm:ss").format(messages[i].getSentDate());
	            System.out.println("mailtime----> " + mailtime);
	            
	           
	            i++;
	          }
	        
	          catch (Exception e)
	          {
	            System.out.println("******      " + e);
	          }
	          

	        }
	       folder.close(true);
	        store.close();
	      
	  
	    }
	    catch (Exception e)
	    {
	      System.out.println("Exceptions in download ----->" + e);
	      e.printStackTrace();
	    }
	  }
	  
	  public void sqliteTomysql() {
		  
	  }
	  
	  
	  public void GetConnection()
	  {
	    try
	    {
	    	System.out.println("in connection");
	      props.setProperty("mail.pop3.port", "110");
	      props.setProperty("mail.pop3.socketFactory.port", "110");
	      store = session.getStore("pop3");
	      
	      
	      
	      
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      System.out.print("Exception in get connection--->" + e);
	    }
	  }
	  
	 
	  
	  public void run()
	  {
	    try
	    {
		      for (int i=0;i<msgcount;i++)
	      {
	        
	        
	        download();
	        Thread.sleep(interval);
	      }
	    }
	    catch (Exception e)
	    {
	      System.out.println("exception in run" + e);
	    }
	  }
	}



