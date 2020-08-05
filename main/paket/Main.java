/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paket;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author CAR
 */
public class Main extends Frame{
     @Resource(lookup = "jms/__defaultConnectionFactory")
      public  static ConnectionFactory cf;
     @Resource(lookup ="zahtevi" )
         public static Queue queueZahtvi;
     @Resource(lookup ="odgovori" )
         public static Queue queueOdgovori;
     @Resource(lookup = "timerTopic")
     public static Topic timerT;
     
    
   
    
  public Main(){
      super("Register base");
       setSize(400,100);
      System.out.println(cf);
      
       addButtons();
      addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
          SenderClass.prekini();
          dispose(); //To change body of generated methods, choose Tools | Templates.
      }
          
});
      setVisible(true);
}  

  private void addButtons(){
  Panel panel = new Panel();
      panel.setLayout(new GridLayout(2, 1));
      Button sendReq= new Button("Send request");
      Button checkStatus = new Button("Check status");
      checkStatus.addActionListener(l->{
      new CheckDoneDialog(Main.this);
      });
      
      sendReq.addActionListener(l->{
               new SendRequest(Main.this);
      });
      
      
   panel.add(sendReq); panel.add(checkStatus);
   add(panel);
  }
  
  
  public static void main(String[] args){
   new Main();
      //System.out.println("paket.Main.main()  "+ DatabaseReader.getInstance().getZahtvByID(1)  );
  
  
  }
    
}
