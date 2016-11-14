/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SistemaControlRentaIVAFx.BusinessProcess;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author gelukkig
 */
public class PerisitenceManager {
    
    private static EntityManagerFactory emf=Persistence.createEntityManagerFactory("SistemaControlRentaIVAPU");
    
    public static EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    
}
