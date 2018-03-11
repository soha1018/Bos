package cn.itsoha.bos.service.impl;

import cn.itsoha.bos.dao.ISubareaDao;
import cn.itsoha.bos.domain.Subarea;
import cn.itsoha.bos.service.SubareaService;
import cn.itsoha.bos.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class SubareaServiceImpl implements SubareaService {
    @Resource(name = "subareaDaoImpl")
    private ISubareaDao subareaDao;
    /**
     * 添加分区
     */
    @Override
    public void save(Subarea model) {
        subareaDao.saveOrUpdate(model);
    }

    /**
     * 查询分页数据
     */
    @Override
    public void pageQuery(PageBean pageBean) {
       subareaDao.pageQuery(pageBean);
    }

    /**
     * 查询所有分区数据
     */
    @Override
    public List<Subarea> findAll() {
        return subareaDao.getAll();
    }

    /**
     * 查询所有未关联到分区的定区
     */
    @Override
    public List<Subarea> findListByNotAssociation() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Subarea.class);
        //比较分区关联的字段等于NULL
        criteria.add(Restrictions.isNull("decidedzone"));
        return subareaDao.findByCriteria(criteria);
    }
}
