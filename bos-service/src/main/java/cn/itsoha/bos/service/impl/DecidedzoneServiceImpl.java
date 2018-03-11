package cn.itsoha.bos.service.impl;

import cn.itsoha.bos.dao.DecidedzoneDao;
import cn.itsoha.bos.dao.ISubareaDao;
import cn.itsoha.bos.domain.Decidedzone;
import cn.itsoha.bos.domain.Subarea;
import cn.itsoha.bos.service.DecidedzoneService;
import cn.itsoha.bos.utils.PageBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class DecidedzoneServiceImpl implements DecidedzoneService {
    @Resource(name = "decidedzoneDaoImpl")
    private DecidedzoneDao decidedzoneDao;
    @Resource(name = "subareaDaoImpl")
    private ISubareaDao subareaDao;
    /**
     * 定区添加
     */
    @Override
    public void save(Decidedzone model, String[] subareaid) {
        //1.把数据加入定区表中
        decidedzoneDao.save(model);
        //2.查询分区表并关联到定区表
        for (String id :subareaid) {
            Subarea subarea = subareaDao.findById(id);
            subarea.setDecidedzone(model);
        }
    }

    /**
     * 返回定区所有数据
     */
    @Override
    public void pageQuery(PageBean pageBean) {
       decidedzoneDao.pageQuery(pageBean);
    }

}
