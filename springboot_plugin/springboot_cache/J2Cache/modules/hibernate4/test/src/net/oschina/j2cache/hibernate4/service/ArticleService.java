package net.oschina.j2cache.hibernate4.service;

import net.oschina.j2cache.hibernate4.bean.Article;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ArticleService {

    @Resource
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public void save(Article article) {
        getSession().saveOrUpdate(article);
    }

    public List<Article> find(Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(Article.class);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        criteria.setCacheable(true);
        return criteria.list();
    }

    public void delete(String id) {
        getSession().delete(getSession().get(Article.class, id));
    }

    public Article findUnique(Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(Article.class);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        criteria.setCacheable(true);
        return (Article)criteria.uniqueResult();
    }

    public Article get(String id) {
        return (Article)getSession().get(Article.class,id);
    }
}
