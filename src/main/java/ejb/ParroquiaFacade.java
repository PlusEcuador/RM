/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entidades.Parroquia;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Bryan_Mora
 */
@Stateless
public class ParroquiaFacade extends AbstractFacade<Parroquia> {

    @PersistenceContext(unitName = "RM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParroquiaFacade() {
        super(Parroquia.class);
    }
    
     //funcion que devuelve el id segun la consulta
     //funcion que devuelve el id segun la consulta
    public Long asignarID() {
        Query q;
        try {
            q = em.createQuery("select max(a.parId) from Parroquia a");
            return ((Long) q.getSingleResult()) + 1;
        } catch (Exception e) {
            return new Long(1);
        }
    }
    
}
