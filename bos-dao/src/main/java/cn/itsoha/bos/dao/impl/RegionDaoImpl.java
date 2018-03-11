package cn.itsoha.bos.dao.impl;

import cn.itsoha.bos.dao.IRegionDao;
import cn.itsoha.bos.domain.Region;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao {
    /**
     * 根据q参数进行模糊查询
     */
    @Override
    public List<Region> findListByQ(String q) {
        String hql = "FROM Region r WHERE r.shortcode LIKE ? "
                + "	OR r.citycode LIKE ? OR r.province LIKE ? "
                + "OR r.city LIKE ? OR r.district LIKE ?";
        return (List<Region>) this.getHibernateTemplate().
                find(hql, "%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%");
    }
}
