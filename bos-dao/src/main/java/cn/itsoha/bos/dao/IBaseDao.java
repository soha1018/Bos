package cn.itsoha.bos.dao;

import cn.itsoha.bos.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;

/**
 * 持久层通用接口
 */
public interface IBaseDao<T> {
    void save(T t);

    void saveOrUpdate(T t);

    void delete(T t);

    void update(T t);

    T findById(Serializable id);

    List<T> findByCriteria(DetachedCriteria criteria);

    List<T> getAll();

    void executeUpdate(String sqlName,Object...objects);

    void pageQuery(PageBean pageBean);
}
