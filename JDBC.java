import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
class JDBC extends Frame implements ActionListener{
	String dno=null,dname=null,dloc=null,txt;
	   Button insert;
       Button find;
       Button update;
       Button delete;
       Button clear;
	   TextField t1,t2,t3;
	   Label t4,t5;
	    Frame f;
   String dno1=null,dname1=null,dloc1=null;
	JDBC(){
	      f=new Frame();
       Font myFont = new Font("Serif",Font.BOLD,26);
	    f.setSize(800,800);
f.setLayout(new GridLayout(10,0,10,10));
		f.addWindowListener(new WindowAdapter(){
           public void windowClosing(WindowEvent windowEvent){
            System.exit(0);  }
        } );
	  Label l1=new Label("DEPTNO    :");
	  l1.setFont(myFont);
	    t1=new TextField();
		t1.setFont(myFont);
	  f.add(l1);
	  f.add(t1);
	   Label l2=new Label("DNAME      : ");
	   l2.setFont(myFont);
	     t2=new TextField();
		 t2.setFont(myFont);
	  f.add(l2);
	  f.add(t2);
	  Label l3=new Label("DLOC          : ");
	  l3.setFont(myFont);
	    t3=new TextField();
		t3.setFont(myFont);
	  f.add(l3);
	  f.add(t3);
	   dno=t1.getText();
	   dname=t2.getText();
	   dloc=t3.getText();
       insert=new Button("INSERT");
	   f.add(insert);
	   insert.addActionListener(this);
	   update=new Button("UPDATE");
	   f.add(update);
	   update.addActionListener(this);
	   delete=new Button("DELETE");
	   f.add(delete);
	   delete.addActionListener(this);
	   find=new Button("FIND");
	   f.add(find);
	   find.addActionListener(this);
	   clear=new Button("CLEAR");
	   f.add(clear);
	   clear.addActionListener(this);
	   Button x=new Button();
	   f.add(x);
	   t4=new Label("ENTER AND PROCEED...");
       t4.setFont(myFont);
	   t4.setAlignment(Label.RIGHT); 
	   f.add(t4); 
	   t5=new Label("");
	   t5.setFont(myFont);
	   t5.setAlignment(Label.LEFT);
	   f.add(t5);
	   
	  f.setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		dno=t1.getText();
		dname=t2.getText();
		dloc=t3.getText();
       Connection con=null;
	   PreparedStatement st=null;
	   try{
	     Class.forName("oracle.jdbc.driver.OracleDriver");
		 con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
	   }
	   catch(SQLException|ClassNotFoundException e1){
	     System.out.println("unable to continue...\n"+e1);
	   }
	   int f=0;
	  
	   if(e.getSource()==insert) //1
		{
	      try{
		     st=con.prepareStatement("INSERT INTO DEPT VALUES(?,?,?)");
			 st.setInt(1,Integer.parseInt(dno));
			 st.setString(2,dname);
			 st.setString(3,dloc);
			 int i=st.executeUpdate();
			 t4.setText(i+" rec inserted");
			 t5.setText(" successfully");
			 
			 dno1=dno;
			 dname1=dname;
			 dloc1=dloc;
		  }
		  catch(SQLException se){
		  t4.setText("duplicate values not allowed");
		  t5.setText("");
		  }
		   catch(NumberFormatException se){
		   t4.setText("Enter values correctly to insert .. .");
		   t5.setText("");
		   }
		 // System.out.println("dno1 : "+dno1+" dname1 : "+dname1+" dloc1: "+dloc1);
	   }//if
	   else if(e.getSource()==delete){ //2
		   try{  
	if(dno.equals("")&&!dname.equals("")&&dloc.equals("")){
		          st=con.prepareStatement("delete from dept where dname=?");
				  st.setString(1,dname);
				  int i=st.executeUpdate();
				   if(i!=0){
			    	  t4.setText(i+" record deleted");
				      t5.setText("whose dname = "+dname);
				   } 
				  else{
                      t4.setText("no record existed");  
		           t5.setText("");
			   }
	      }
		else  if(!dno.equals("")&&dname.equals("")&&dloc.equals("")){
		          st=con.prepareStatement("delete from dept where deptno=?");
				  st.setInt(1,Integer.parseInt(dno));
				  int i=st.executeUpdate();
				   if(i!=0){
			    	  t4.setText(i+" record deleted");
				      t5.setText("whose deptno = "+dno);
				    }
				  else{
                      t4.setText("no record existed");
					  t5.setText("");
		          	   } 
		       }
			else if(dno.equals("")&&dname.equals("")&&!dloc.equals("")){
		          st=con.prepareStatement("delete from dept where loc=?");
				  st.setString(1,dloc);
				  int i=st.executeUpdate();
				  if(i!=0){
			    	  t4.setText(i+" record deleted");
				      t5.setText("whose dloc = "+dloc);
					}
				  else{
                      t4.setText("no record existed");
				    t5.setText("");
				  }
			   } 
			else{
				t4.setText("enter any one textfield!!!");
		      t5.setText("");
			  }
		   }
		   catch(SQLException se)
		   {
		    System.out.println("dont worry dude...\n");// se.printStackTrace();
		   }
	   
	   }//elseif
	 else if(e.getSource()==find){
	       try{
      	      if(!dno.equals("")&&dname.equals("")&&dloc.equals("")){
			   
				st=con.prepareStatement("SELECT * FROM DEPT WHERE deptno=?");
				st.setInt(1,Integer.parseInt(dno));
				st.executeQuery();
				ResultSet rs=st.getResultSet();
				rs.next();
				dno1=rs.getString(1);
				dname1=rs.getString(2);
                dloc1=rs.getString(3);
				t1.setText(dno1);
				t2.setText(dname1);
				t3.setText(dloc1);
				t4.setText("DESIRED ROW SELECTED...");
				t5.setText("whose deptno = "+dno);
			  }
			  else if(dno.equals("")&&!dname.equals("")&&dloc.equals("")){
			 
				st=con.prepareStatement("SELECT * FROM DEPT WHERE dname=?");
				 st.setString(1,dname);
				st.executeQuery();
				ResultSet rs=st.getResultSet();
				rs.next();
				dno1=rs.getString(1);
				dname1=rs.getString(2);
                dloc1=rs.getString(3);
				t1.setText(dno1);
				t2.setText(dname1);
				t3.setText(dloc1);
				t4.setText("DESIRED ROW SELECTED...");
				t5.setText("whose dname = "+dname);
			  }
			  else if(dno.equals("")&&dname.equals("")&&!dloc.equals("")){
			   
				st=con.prepareStatement("SELECT * FROM DEPT WHERE loc=?");
				st.setString(1,dloc);
				st.executeQuery();
				ResultSet rs=st.getResultSet();
				rs.next();
				dno1=rs.getString(1);
				dname1=rs.getString(2);
                dloc1=rs.getString(3);
				t1.setText(dno1);
				t2.setText(dname1);
				t3.setText(dloc1);
			    t4.setText("DESIRED ROW SELECTED...");
				t5.setText("whose loc = "+dloc);
			  }
			  else{
				   t4.setText("enter any one textfield!!!");
			       t5.setText("");
			  }
		   }
		   catch(SQLException se)
		   {
			 t4.setText(" no records existed");  
			 t5.setText("");
		   }
	 }//elseif
	 else if(e.getSource()==update){
	 System.out.println("dno : "+dno+" dname : "+dname+" dloc : "+dloc);
        System.out.println("dno1 : "+dno1+" dname1 : "+dname1+" dloc1 : "+dloc1); 
  		   try{
		        if(!dno.equals(dno1)&&dname.equals(dname1)&&dloc.equals(dloc1)){
				   st=con.prepareStatement("UPDATE DEPT SET DEPTNO=? where dname=? and loc=?");
				   st.setInt(1,Integer.parseInt(dno));   
				   st.setString(2,dname1);
				   st.setString(3,dloc1);
				   int i=st.executeUpdate();
                   t4.setText(i+" records updated whose");
                   t5.setText("dname = "+dname1+" dloc: "+dloc1);
		           dno1=dno;
			  }
	         else if(dno.equals(dno1)&&!dname1.equals(dname)&&dloc.equals(dloc1)){
				   st=con.prepareStatement("UPDATE DEPT SET DNAME=? where deptno=? and loc=?");
				   st.setString(1,dname);   
				   st.setInt(2,Integer.parseInt(dno1));
				   st.setString(3,dloc1);
				   int i=st.executeUpdate();
                   t4.setText(i+" records updated whose");
				   t5.setText("dno: "+dno1+" dloc: "+dloc1);
				   dname1=dname;
		      }
			  else if(dno.equals(dno1)&&dname.equals(dname1)&&!dloc.equals(dloc1)){
				   st=con.prepareStatement("UPDATE DEPT SET LOC=? where DNAME=? and deptno=?");
				   st.setString(1,dloc);   
				   st.setString(2,dname1);
				   st.setInt(3,Integer.parseInt(dno1));
				   int i=st.executeUpdate();
                   t4.setText(i+" records updated whose");
				   t5.setText("dname: "+dname1+" deptno: "+dno1);
				   dloc1=dloc;
		      }
			  else{
			    t4.setText("enter values correctly or");
				t5.setText("records are already existed");
			  }
		   }
		   catch(SQLException se){
		      t4.setText("invalid update dude...");
			  t5.setText("");
		   }
	    
	 }
	else{
	   t1.setText("");
	   t2.setText("");
	   t3.setText("");
	   t4.setText("TEXTFIELDS  CLEARED... ");
	   t5.setText("ENTER AND PROCEED");
	}
}
	public static void main(String[] args) { 
      JDBC s=new JDBC();
	  
   }
}
