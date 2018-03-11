package cn.itsoha.bos.service.impl;

import cn.itsoha.bos.dao.StaffDao;
import cn.itsoha.bos.domain.Staff;
import cn.itsoha.bos.service.StaffService;
import cn.itsoha.bos.utils.PageBean;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
    @Resource(name = "staffDaoImpl")
    private StaffDao staffDao;

    /**
     * 添加取派员
     */
    @Override
    public void addStaff(Staff model) {
        staffDao.saveOrUpdate(model);
    }

    /**
     * 分页查询取派员
     */
    @Override
    public void pageQuery(PageBean pageBean) {
        staffDao.pageQuery(pageBean);
    }

    /**
     * 删除取派员
     */
    @Override
    public void deleteBatch(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] split = ids.split(",");
            for (String id : split) {
                //逻辑删除
                staffDao.executeUpdate("staff.deleteStaff", "1",id);
            }
        }
    }

    /**
     * 查询未被删除的取派员
     */
    @Override
    public List<Staff> findListByNotDelete() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Staff.class);
        criteria.add(Restrictions.eq("deltag","0"));
        return staffDao.findByCriteria(criteria);
    }
}
