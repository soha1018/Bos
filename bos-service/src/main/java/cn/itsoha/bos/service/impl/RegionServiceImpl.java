package cn.itsoha.bos.service.impl;

import cn.itsoha.bos.dao.IRegionDao;
import cn.itsoha.bos.domain.Region;
import cn.itsoha.bos.service.RegionService;
import cn.itsoha.bos.utils.PageBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RegionServiceImpl implements RegionService {
    @Resource(name = "regionDaoImpl")
    private IRegionDao regionDao;

    /**
     * 批量保存区域数据
     */
    @Override
    public void saveBatch(ArrayList<Region> list) {
        for (Region region : list) {
            regionDao.saveOrUpdate(region);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public void pageQuery(PageBean pageBean) {
        regionDao.pageQuery(pageBean);
    }

    @Override
    public List<Region> findListByQ(String q) {

        return regionDao.findListByQ(q);
    }

    /**
     * 查询所有分区
     */
    @Override
    public List<Region> findAll() {
        return regionDao.getAll();
    }
}
