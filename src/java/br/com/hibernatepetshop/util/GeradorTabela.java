
package br.com.hibernatepetshop.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class GeradorTabela {
     public static void main(String[] args) {
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("HibernatePetshpo1PU");
         emf.close();
    }
}
