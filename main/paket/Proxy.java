/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paket;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 *
 * @author CAR
 */
public class Proxy {
    
  
    private JMSProducer producer;
    private JMSConsumer consumer;
    private JMSContext context;
     public JMSConsumer timercons;
     public Topic timerT;
    private Queue queueOdgovori;
    private Queue queueZahtvi;
    private static Proxy instance;
    public static Proxy getInstance(){
    
    if(instance==null) instance=new Proxy();
    return instance;
    }
    
    private Proxy(){
        queueOdgovori=Main.queueOdgovori;
        queueZahtvi=Main.queueZahtvi;
        timerT=Main.timerT;
        System.out.println(Main.cf);
   context= Main.cf.createContext();
   producer=context.createProducer();
   consumer=context.createConsumer(Main.queueOdgovori);
   timercons=context.createConsumer(timerT);
   
   }
    
    
   public String sendZahtev(Zahtevi zahtev){
         
        try {
            TextMessage txtmsg = context.createTextMessage("persoZahtev");
            ObjectMessage obj = context.createObjectMessage(zahtev);
            producer.send(queueZahtvi, txtmsg);
            producer.send(queueZahtvi, obj);
            
            TextMessage txtmsgResp = (TextMessage) consumer.receive();
            return txtmsgResp.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
        }
       
      return "Greska";
   }
   
   
   public String checkDone(String idDok){
   
      try {
            TextMessage txtmsg = context.createTextMessage("checkDone");
            TextMessage obj = context.createTextMessage(idDok);
            producer.send(queueZahtvi, txtmsg);
            producer.send(queueZahtvi, obj);
            
            TextMessage txtmsgResp = (TextMessage) consumer.receive();
            return txtmsgResp.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
        }
       
      return "Greska";
   
   
   }
   
   
    
    
    
    
    
    
    
    
    
    
}
