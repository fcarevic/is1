/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paket;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author CAR
 */

public class DatabaseReader {
    private static final String ID ="170650000001";
    private static DatabaseReader primerak;
    public static DatabaseReader getInstance(){
     if(primerak==null)primerak = new DatabaseReader();
     return primerak;
    
    }
   private EntityManager em;
    
    
    private DatabaseReader(){
      EntityManagerFactory ef= Persistence.createEntityManagerFactory("persistence");
      em = ef.createEntityManager();
    
    }
    
    public  Zahtevi getZahtvByID(String id){
    
        
        List resultList = em.createNamedQuery("Zahtevi.findById").setParameter("id", id).getResultList();
        if(resultList.isEmpty())return null;
        return (Zahtevi) (resultList.remove(0));
        
    
    
    }
    public boolean checkJMBGExists(String JMBG){
    
        List resultList = em.createNamedQuery("Zahtevi.findByJmbg").setParameter("jmbg", JMBG).getResultList();
        return !resultList.isEmpty();
    }
    
    public void persistZahtev(Zahtevi zahtev){
        List<Zahtevi> resultList = em.createNamedQuery("Zahtevi.findAll", Zahtevi.class).getResultList();
         String id= null;
        if(resultList.isEmpty()) id =ID;
        else{
         
            Long idL = Long.parseLong(resultList.get(resultList.size()-1).getId());
             idL++;
             id=idL.toString();
        }
          zahtev.setId(id);
          zahtev.setStatus("kreiran");
            em.getTransaction().begin();
            em.persist(zahtev);
            em.flush();
            em.getTransaction().commit();
    }
    
    public void updateZahtev(String id, String status){
        List<Zahtevi> resultList = em.createNamedQuery("Zahtevi.findById",Zahtevi.class).setParameter("id", id).getResultList();
        if(!resultList.isEmpty()){
           Zahtevi zahtev= resultList.remove(0);
           em.getTransaction().begin();
           zahtev.setStatus(status);
           em.getTransaction().commit();
            
        
        }
    
    }
    
    
}
