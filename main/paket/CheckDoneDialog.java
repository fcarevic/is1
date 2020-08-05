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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author CAR
 */
public class CheckDoneDialog extends Dialog{

  private String idDok;
  private Panel panel;
  private static final String CEKANAURUCENJE="Ceka na urucenje";
      private static final String[] labele = {"JMBG","Ime", "Prezime", "Ime majke","Prezime majke","Ime oca","Prezime oca","Pol","Datum rođenja",
 "Nacionalnost","Profesija", "Bračno stanje", "Opština prebivališta" ,"Ulica prebivališta" ,"Broj prebivalista","Status"};
     private static final int NUMOFDATAROWS= 16, NUMOFDATACOLS=2; 
     private TextField[] polja;
     private TextField idField=new TextField(15);
     private Label idLabel = new  Label("Uneti id");
     private Button getRequest;
     private Button refresh;
     private Button uruci;
     private Label statusnaLabela = new Label();
    
    public CheckDoneDialog(Frame owner){
        super(owner, "Check request", true);
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
        Panel p = new Panel();
        p.add(idLabel);
        p.add(idField);
        add(p,BorderLayout.NORTH);
        
    panel= new Panel();
       panel.setLayout(new GridLayout(NUMOFDATAROWS, NUMOFDATACOLS));
       polja= new TextField[NUMOFDATAROWS];
       for(int i=0;i<NUMOFDATAROWS;i++) {
           polja[i] = new TextField(10);
           polja[i].setEditable(false);
           panel.add( new Label(labele[i]));
           panel.add(polja[i]);
       }
       add(panel,BorderLayout.CENTER);
       
    }
    private void napraviDugme(){
  Panel panel = new  Panel();
  getRequest= new Button("Get request");
  
  refresh = new Button("Update status");
  uruci=new Button("Uruci");
  refresh.setEnabled(false);
  panel.add(getRequest);
  panel.add(refresh);
  panel.add(uruci);
  
  uruci.setEnabled(false);
  uruci.addActionListener(l->{
      
    DatabaseReader.getInstance().updateZahtev(idDok, "urucen");
      Proxy.getInstance().timercons.setMessageListener(lll->{
      });
  });
  
  
 
  
  getRequest.addActionListener(l->{
       idDok = idField.getText();
      Zahtevi zahtev = DatabaseReader.getInstance().getZahtvByID(idDok);
      System.out.println("paket.CheckDoneDialog.napraviDugme() "+ zahtev);  
      if(zahtev==null) {resetFields(); return; }
        updateFieldsFromZahtev(zahtev);
        
        refresh.setEnabled(true);
   
        Proxy.getInstance().timercons.setMessageListener(ll->{
            String status=Proxy.getInstance().checkDone(idDok);
      
     // String status =SenderClass.checkDocumentFinished(idDok);
       System.err.println(status);
       if(!status.equals("Los zahtev") && !status.equals("Nema dokument")) 
       {  DatabaseReader.getInstance().updateZahtev(idDok, status);
        polja[15].setText(status);
        statusnaLabela.setText("Azurirano");
        if(status.equals(CEKANAURUCENJE))uruci.setEnabled(true);
       } else statusnaLabela.setText(status);
        
        });
        
        
        
  });
  
   
  
  refresh.addActionListener(l->{
  
        String status=Proxy.getInstance().checkDone(idDok);
      
     // String status =SenderClass.checkDocumentFinished(idDok);
       System.err.println(status);
       if(!status.equals("Los zahtev") && !status.equals("Nema dokument")) 
       {  DatabaseReader.getInstance().updateZahtev(idDok, status);
        polja[15].setText(status);
        statusnaLabela.setText("Azurirano");
        if(status.equals(CEKANAURUCENJE))uruci.setEnabled(true);
       } else statusnaLabela.setText(status);
  
  
  
  });
  
  
  Panel panel2= new Panel();
  panel2.setLayout(new GridLayout(2, 1));
  panel2.add(statusnaLabela);
  panel2.add(panel);
  add(panel2,BorderLayout.SOUTH);
    }
        //  private static final String[] labele = {"JMBG","Ime", "Prezime", "Ime majke","Prezime majke","Ime oca","Prezime oca","Pol","Datum rođenja",
// "Nacionalnost","Profesija", "Bračno stanje", "Opština prebivališta" ,"Ulica prebivališta" ,"Broj prebivalista"};
 
    private void resetFields(){
     uruci.setEnabled(false);
        refresh.setEnabled(false);
    for(int i=0;i<NUMOFDATAROWS;i++) polja[i].setText("");
    statusnaLabela.setText("Ne postoji zahtev");
    
    }
    
    private void updateFieldsFromZahtev(Zahtevi zahtev){
      
        
      statusnaLabela.setText("Dohvaceno");
      
     
      polja[0].setText(zahtev.getJmbg());
      polja[1].setText(zahtev.getIme());
      polja[2].setText(zahtev.getPrezime());
      polja[3].setText(zahtev.getImeMajke());
      polja[4].setText(zahtev.getPrezimeMajke());
      polja[5].setText(zahtev.getImeOca());
      polja[6].setText(zahtev.getPrezimeOca());
      polja[7].setText(zahtev.getPol());
      polja[8].setText(zahtev.getDatumRodjenja());
      polja[9].setText(zahtev.getNacionalnost());
     polja[10].setText(zahtev.getProfesija());
       polja[11].setText(zahtev.getBracnoStanje());
      polja[12].setText(zahtev.getOpstinaPrebivalista());
   polja[13].setText(zahtev.getUlicaPrebivalista());
  polja[14].setText(zahtev.getBrojPrebivalista());
   polja[15].setText(zahtev.getStatus());
   if(zahtev.getStatus().equals(CEKANAURUCENJE)) uruci.setEnabled(true);
      
   }
    
    
}
