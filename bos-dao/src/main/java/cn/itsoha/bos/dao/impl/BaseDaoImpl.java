package cn.itsoha.bos.dao.impl;

import cn.itsoha.bos.dao.IBaseDao;
import cn.itsoha.bos.utils.PageBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
    private Class<T> clazz;
    public BaseDaoImpl() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] types = type.getActualTypeArguments();
        clazz = (Class<T>) types[0];
    }

    @Resource(name = "sessionFactory")
    public void setSF(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
    @Override
    public void save(T t) {
        getHibernateTemplate().save(t);
    }

    /**
     * 增加或者更新
     */
    @Override
    public void saveOrUpdate(T t) {
        getHibernateTemplate().saveOrUpdate(t);
    }

    @Override
    public void delete(T t) {
        getHibernateTemplate().delete(t);
    }

    @Override
    public void update(T t) {
        getHibernateTemplate().update(t);
    }

    @Override
    public T findById(Serializable id) {
        return getHibernateTemplate().get(clazz,id);
    }

    @Override
    public List<T> findByCriteria(DetachedCriteria criteria) {
        return (List<T>) getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public List<T> getAll() {
        String hql = "FROM " + clazz.getSimpleName();
        return (List<T>) getHibernateTemplate().find(hql);
    }

    @Override
    public void executeUpdate(String sqlName, Object... objects) {
        Session session = this.getSessionFactory().getCurrentSession();
        Query query = session.getNamedQuery(sqlName);
        int i = 0;
        for (Object o :objects) {
            query.setParameter(i++, o);
        }
        query.executeUpdate();
    }

    /**
     * 分页查询
     */
    @Override
    public void pageQuery(PageBean pageBean) {
        int currentPage = pageBean.getCurrentPage();
        int pageSize = pageBean.getPageSize();
        //查询总数
        DetachedCriteria criteria = pageBean.getCriteria();
        criteria.setProjection(Projections.rowCount());
        List<Long> total = (List<Long>) getHibernateTemplate().findByCriteria(criteria);
        pageBean.setTotal(total.get(0).intValue());
        pageBean.getCriteria().setProjection(null);
        //起始索引
        int startIndex = (currentPage-1)*pageSize;
        //查询数据集
        List list = getHibernateTemplate().findByCriteria(criteria, startIndex, pageSize);
        pageBean.setRows(list);
    }


}
