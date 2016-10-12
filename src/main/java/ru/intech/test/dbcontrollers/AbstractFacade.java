/*
 * Code author Solonin Ivan (osmos_two@rambler.ru)
 */
package ru.intech.test.dbcontrollers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class AbstractFacade<T> {
    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
    
//    Netbeans сгенерировал фасад с criteria-реализацией поиска всех элементов. 
//    Критерию использовать не приходилось поэтому сделал через SQL
//    Проще было бы через JPQL, но в ТЗ значилось "native SQL"
    protected List<T> findAll(String tableName){
        Query query = getEntityManager().createNativeQuery("select * from "+tableName, entityClass);
        List<T> resultList = query.getResultList();
        return resultList;
    }
    
    
//    public List<T> findAll() {
//        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        cq.select(cq.from(entityClass));
//        return getEntityManager().createQuery(cq).getResultList();
//    }    
}
