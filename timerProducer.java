/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.timer;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author tasha
 */
@Stateless
public class timerProducer {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    public ConnectionFactory conn;
        
    @Resource(lookup = "timerTopic")
    public Topic timerTopic;
    
    public JMSContext context;
    public JMSProducer producer;
    
    public int vr=0;
    
    @Schedule(second="*/10", minute = "*",  hour = "*")
    public void test(){
        if(conn!=null && timerTopic!=null){
            if(context==null){
                context=conn.createContext();
                producer=context.createProducer();
                System.out.println("Kreiran producer");
            }
           
            TextMessage txtMsg=context.createTextMessage("Test "+vr);
            producer.send(timerTopic, txtMsg);
            vr++;
            System.out.println("timer");
        }
    }
}
