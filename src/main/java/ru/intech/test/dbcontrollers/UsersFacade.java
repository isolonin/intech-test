/*
 * Code author Solonin Ivan (osmos_two@rambler.ru)
 */
package ru.intech.test.dbcontrollers;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ru.intech.test.dbobjects.Users;

/**
 *
 * @author ivan
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users>{

    @PersistenceContext(unitName = "intech-base")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
    
    public List<Users> findAll() {
        List<Users> users = super.findAll("users");
        return users;
    }
}
