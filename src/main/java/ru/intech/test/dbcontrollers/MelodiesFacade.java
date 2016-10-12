/*
 * Code author Solonin Ivan (osmos_two@rambler.ru)
 */
package ru.intech.test.dbcontrollers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ru.intech.test.dbobjects.Melodies;

/**
 *
 * @author ivan
 */
@Stateless
public class MelodiesFacade extends AbstractFacade<Melodies> {

    @PersistenceContext(unitName = "intech-base")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MelodiesFacade() {
        super(Melodies.class);
    }
    
}
