package com.mycompany.task.persistence.dao;

/*import com.mycompany.task.persistence.entity.Model;
import java.util.List;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.CriteriaSpecification.DISTINCT_ROOT_ENTITY;
import static org.hibernate.criterion.Restrictions.idEq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ModelDAO {
    @Autowired
    private SessionFactory sessionFactory;
    
    public List<Model> getAll() {
        return sessionFactory.getCurrentSession()
                .createSQLQuery("SELECT * FROM model").addEntity(Model.class)
                .setResultTransformer(DISTINCT_ROOT_ENTITY)
                .list();
    }

    public Model get(Long id) {
        return (Model) sessionFactory.getCurrentSession()
                .createCriteria(Model.class)
                .add(idEq(id))
                .uniqueResult();
    }

    public void delete(Model model) {
        sessionFactory.getCurrentSession().delete(model);
    }

    public void saveOrUpdate(Model model) {
        sessionFactory.getCurrentSession().saveOrUpdate(model);
    }
    
}*/
