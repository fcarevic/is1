/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paket;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CAR
 */
public class SendRequest extends Dialog{
     private Panel panel;
      private static final String[] labele = {"JMBG","Ime", "Prezime", "Ime majke","Prezime majke","Ime oca","Prezime oca","Pol","Datum rođenja",
 "Nacionalnost","Profesija", "Bračno stanje", "Opština prebivališta" ,"Ulica prebivališta" ,"Broj prebivalista"};
     private static final int NUMOFDATAROWS= 15, NUMOFDATACOLS=2; 
     private TextField[] polja;
     private Button sendRequest;
     private Button checkTime;
     private Label statusnaLabela = new Label();
    
    public SendRequest(Frame owner){
        super(owner, "Send request", true);
       setSize(800,600);
       dodajLabele();
       napraviDugme();
       
      addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
          dispose(); //To change body of generated methods, choose Tools | Templates.
      }
          
});
      setVisible(true);
  
    
    }
    private void dodajLabele(){
    panel= new Panel();
       panel.setLayout(new GridLayout(NUMOFDATAROWS, NUMOFDATACOLS));
       polja= new TextField[NUMOFDATAROWS];
       for(int i=0;i<NUMOFDATAROWS;i++) {
           polja[i] = new TextField(10);
           
           panel.add( new Label(labele[i]));
           panel.add(polja[i]);
       }
       add(panel,BorderLayout.CENTER);
       
    }
    private void napraviDugme(){
  Panel panel = new  Panel();
  sendRequest= new Button("Send request");
  sendRequest.setEnabled(false);
  checkTime = new Button("Check time");
  panel.add(sendRequest);
  panel.add(checkTime);
  
  checkTime.addActionListener(arg->{
        if(SenderClass.checkTimeslot(getCurrentTime())){
            sendRequest.setEnabled(true);
        }else {
            sendRequest.setEnabled(false);
        statusnaLabela.setText("Nema terimina");
        }
  });
  
  sendRequest.addActionListener(l->{
      Zahtevi zahtev= generateZahtevFromFields();
          if(zahtev==null)return;
            if(DatabaseReader.getInstance().checkJMBGExists(zahtev.getJmbg())) {
            statusnaLabela.setText("Zahtev vec postoji"); return;
            }
            
            String status= Proxy.getInstance().sendZahtev(zahtev);
             DatabaseReader.getInstance().persistZahtev(zahtev);
           
// String sendRequestToPerso = SenderClass.sendRequestToPerso(zahtev);
            statusnaLabela.setText(status);
            //dodaj slanje do sendera
            
            sendRequest.setEnabled(false);
      
  });
  
  
  
  
  
  Panel panel2= new Panel();
  panel2.setLayout(new GridLayout(2, 1));
  panel2.add(statusnaLabela);
  panel2.add(panel);
  add(panel2,BorderLayout.SOUTH);
    }
        //  private static final String[] labele = {"JMBG","Ime", "Prezime", "Ime majke","Prezime majke","Ime oca","Prezime oca","Pol","Datum rođenja",
// "Nacionalnost","Profesija", "Bračno stanje", "Opština prebivališta" ,"Ulica prebivališta" ,"Broj prebivalista"};
 
    private Zahtevi generateZahtevFromFields(){
      if(polja[0].getText().equals("")) {
      
      statusnaLabela.setText("Uneti JMBG");
      return null;}
      if(polja[1].getText().equals("")){statusnaLabela.setText("Uneti ime"); return null;}
      if(polja[2].getText().equals("")){statusnaLabela.setText("Uneti prezime"); return null;}
      Zahtevi zahtev = new Zahtevi();
     //dodaj id 
     
     zahtev.setJmbg(polja[0].getText());
     
     zahtev.setIme(polja[1].getText());
     zahtev.setPrezime(polja[2].getText());
     zahtev.setImeMajke(polja[3].getText());
     zahtev.setPrezimeMajke(polja[4].getText());
    zahtev.setImeOca(polja[5].getText());
   zahtev.setPrezimeOca(polja[6].getText());
   zahtev.setPol(polja[7].getText());
   zahtev.setDatumRodjenja(polja[8].getText());
   zahtev.setNacionalnost(polja[9].getText());
   zahtev.setProfesija(polja[10].getText());
   zahtev.setBracnoStanje(polja[11].getText());
   zahtev.setOpstinaPrebivalista(polja[12].getText());
   zahtev.setUlicaPrebivalista(polja[13].getText());
   zahtev.setBrojPrebivalista(polja[14].getText());
   return zahtev;
  }
    
    
    private String getCurrentTime(){
    
    
          Date date = new Date();  
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
          String termin = sdf.format(date) ;
         return termin+"-07:00";
    }
    
  
    
}
