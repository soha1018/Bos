package cn.itsoha.bos.service;

import cn.itsoha.bos.domain.Region;
import cn.itsoha.bos.utils.PageBean;

import java.util.ArrayList;
import java.util.List;

public interface RegionService {
    void saveBatch(ArrayList<Region> list);

    void pageQuery(PageBean pageBean);

    List<Region> findListByQ(String q);

    List<Region> findAll();
}
