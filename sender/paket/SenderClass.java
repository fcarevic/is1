package paket;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.TimerService;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SenderClass  {
    @Resource(lookup = "jms/__defaultConnectionFactory")
        static ConnectionFactory cf;
     @Resource(lookup ="zahtevi" )
        static Queue queueZahtevi;
     @Resource(lookup ="odgovori" )
        static Queue queueOdgovori;
 
     
     
   private static final String baseURL="http://virtserver.swaggerhub.com/petar.noki0x60/ETFTask/1.0.0";
   private static final String centerId="17065", kodovanje= "UTF-8";
   
   private static Thread nit;
   public SenderClass(){
   nit = new Handler(cf, queueZahtevi, queueOdgovori);
   nit.start();
   }
   
   
private static String generateEncodedParamString(String key, String value) throws UnsupportedEncodingException{
 
  return  String.format(key+"=%s", 
                   URLEncoder.encode(value, kodovanje));
     
            

}
  
    public static boolean checkTimeslot(String termin)  {

        try {
            String path = "/terminCentar/checkTimeslotAvailability";
            String params = "?regionalniCentarId=" + centerId + "&" + "termin="+ termin;
            String url= baseURL + path + params;
              
            
            
            
            HttpURLConnection httpClient =
                    (HttpURLConnection) new URL(url).openConnection();
              //connection.setRequestProperty("Accept-Charset", kodovanje);
            
            
            // optional default is GET
            httpClient.setRequestMethod("GET");
           
//add request header
            httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
            System.out.println("\nSending 'GET' request to termin  ");
            
            int responseCode = httpClient.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            boolean flag=false;
            switch(responseCode){
            
                case 200:  //TODO: PROMENI OVDE CITANJE
                    
                    String response= getStringFromResponse(httpClient);
               //promeni flas ovde
                    JSONObject obj = (JSONObject)(new JSONParser().parse(response));
                    String dostupnost = obj.get("dostupnost")+"";
                    System.err.println(response);
                    if(dostupnost.equals("true")) flag=true;
                    
                    
                    break;
            
                
                case 400:  System.err.println("Nevalidan parametar "); break;
                
                default: System.err.println("default u swtich-u"); break;
            
            
            }
            
            return flag;
            
            }    catch (MalformedURLException ex) {
           Logger.getLogger(SenderClass.class.getName()).log(Level.SEVERE, null, ex);
          }
        catch (IOException ex) {
                Logger.getLogger(SenderClass.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(SenderClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        return false;
            
         
    }
    private static void addStringToBody(HttpURLConnection httpClient,String string) throws IOException{
         try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(string);
            wr.flush();
        }
    }
    private String convertZahtevitoJson(Zahtevi zahtev){
    JSONObject obj = new JSONObject();
    obj.put("id", zahtev.getId());
    obj.put("JMBG", zahtev.getJmbg());
    obj.put("ime", zahtev.getIme());
    obj.put("prezime", zahtev.getPrezime());
    obj.put("imeMajke", zahtev.getImeMajke());
    obj.put("imeOca", zahtev.getImeOca());
    obj.put("prezimeMajke", zahtev.getPrezimeMajke());
    obj.put("prezimeOca", zahtev.getPrezimeOca());
    obj.put("pol",zahtev.getPol());
    obj.put("datumRodjenja", zahtev.getDatumRodjenja());
    obj.put("nacionalnost", zahtev.getNacionalnost());
    obj.put("prfesija", zahtev.getProfesija());
    obj.put("bracnoStanje", zahtev.getBracnoStanje());
    obj.put("opstinaPrebivalista", zahtev.getOpstinaPrebivalista());
    obj.put("ulicaPrebivalista", zahtev.getUlicaPrebivalista());
    obj.put("brojPrebivalista",zahtev.getBrojPrebivalista());
    System.out.print(obj.toJSONString());
    return obj.toJSONString();
    }

    private static String convertZahteviToXML(Zahtevi zahtev) throws JAXBException{
    JAXBContext jaxbContext = JAXBContext.newInstance(Zahtevi.class);
    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

// output pretty printed
    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    StringWriter sw = new StringWriter();
    jaxbMarshaller.marshal(zahtev, sw);
    String xmlString = sw.toString();
     return xmlString;   
    }
    
    
    private static  String getStringFromResponse(HttpURLConnection httpClient) throws IOException{
        BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpClient.getInputStream()));
                
                String line;
                StringBuilder response = new StringBuilder();
                
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                
                //print result
                System.out.println(response.toString());
          return response.toString();
    
    
    
    }
    
    
    //vraca status zahteva
    public static String sendRequestToPerso(Zahtevi zahtev){
        
            
            String string=null;
        try {
            String url = baseURL + "/persoCentar/submit";
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            
            //add reuqest header
            httpClient.setRequestMethod("POST");
            httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
            httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            
            
            
            
            
            // Send post request
            
            httpClient.setDoOutput(true);
            //String xmlRepr = converZahteviToXML(zahtev);
            String jsonRepr = convertZahteviToXML(zahtev);
            addStringToBody(httpClient, jsonRepr);
            System.out.println("\nSending 'POST' request to Perso centar : ");
            System.out.println("Post parameters :\n " + jsonRepr);
            
            int responseCode = httpClient.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            
            switch(responseCode){
            
                case 201: 
                    string ="Zahtev poslat na obradu";
                    System.out.println("zahtev poslat na obradu");
                    
                    break;
            
                
                case 400:
                    string= "Los zahtev";
                    System.err.println("Los zahtev");
                    
                    
                    break;
            
            
            
            
            
            
            
            }
            
                
            
            
        }  catch (MalformedURLException ex) {
           Logger.getLogger(SenderClass.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ProtocolException ex) {
           Logger.getLogger(SenderClass.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(SenderClass.class.getName()).log(Level.SEVERE, null, ex);
       } catch (JAXBException ex) {
           Logger.getLogger(SenderClass.class.getName()).log(Level.SEVERE, null, ex);
       }
        return string;

    }

    private static String getStatusFromResponse(String response) throws ParseException{
      
        JSONObject obj = (JSONObject)(new JSONParser().parse(response));
        return (String) obj.get("status");
    
    
    
    }
    
    
    
    
    
    
    
    public static String checkDocumentFinished(String idDok){
        String str=null;
     try {
            String url = baseURL + "/persoCentar/" + idDok;
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            
            //add reuqest header
            httpClient.setRequestMethod("GET");
            httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
            
            
            
            
            
           System.out.println("Slanje provere na perso centar za dokument  "+  idDok  ); 
            
            // Send post request
            
           
            int responseCode = httpClient.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            
            switch(responseCode){
            
                case 200:
                    String response = getStringFromResponse(httpClient);
                    str= getStatusFromResponse(response);
                    break;
            
                
                case 400: str="Los zahtev" ; break;
            
                case 404: str="Nema dokument"; break;
            
            
            
            
            
            }
            
            
            
        }  catch (MalformedURLException ex) {
           Logger.getLogger(SenderClass.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ProtocolException ex) {
           Logger.getLogger(SenderClass.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(SenderClass.class.getName()).log(Level.SEVERE, null, ex);
       }    catch (ParseException ex) { 
                Logger.getLogger(SenderClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        return str;

        
    
    }
    
    
    
    
    
    
    private static void sendPost() throws Exception {

		// url is missing?
        //String url = "https://selfsolve.apple.com/wcResults.do";
        String url = "https://httpbin.org/post";

        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        httpClient.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }

        int responseCode = httpClient.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            //print result
            System.out.println(response.toString());

        }

    }
    
    public static void prekini(){
   if(nit!=null) nit.interrupt();
    
    }
    private class Handler extends Thread{
        private JMSConsumer consumer;
        private JMSProducer producer;
        private JMSContext context;
        private Queue queueZahtevi;
        private Queue queueOdgovori;
        public Handler(ConnectionFactory cf, Queue queueZahtevi, Queue queueOdgovori){
            this.queueOdgovori= queueOdgovori;
            this.queueZahtevi=queueZahtevi;
          context =cf.createContext();
            consumer = context.createConsumer(queueZahtevi);
            producer = context.createProducer();
        }
        
        @Override
        public void run() {
        try{
          while(!interrupted()){
              System.out.println(cf);
              TextMessage msg = (TextMessage) consumer.receive();
              switch(msg.getText()){
              
                  case  "persoZahtev":
                      ObjectMessage objsmg= (ObjectMessage) consumer.receive();
                      TextMessage txtmsg= context.createTextMessage();
                      Zahtevi zahtev = (Zahtevi) objsmg.getObject();
                     // if(!checkTimeslot("sadasnje vreme")){
                        //  txtmsg.setText("Nije validan termin");
                      //}
                      //else{
                      String status = sendRequestToPerso(zahtev);
                      if(status!=null){
                        txtmsg.setText(status);
                      }
                      else txtmsg.setText("Greska");
                      //}
                      
                      producer.send(queueOdgovori, txtmsg); //kao odgovor prvo se salje txt message kao nevalidan termin ili kao status zahteva
                      break;
                      
                      
                  case "checkDone":
                      TextMessage txtmsg2= (TextMessage) consumer.receive();
                      String idZahtev=txtmsg2.getText();
                      String status2 = checkDocumentFinished(idZahtev);
                      TextMessage txtsend = context.createTextMessage(status2);
                      producer.send(queueOdgovori, txtsend);
                      
                      
                      
                      break;
              
                  default:
                      System.out.println("paket.SenderClass.Handler.run() deafult");
                      break;
              
              
              }
              
              
              
          }
        }catch(Exception e){}
        }
        
        
        
    }
    
    ///////TIMER
    public static void main(String [] args){
        new SenderClass();
        
        
        
        
        }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
