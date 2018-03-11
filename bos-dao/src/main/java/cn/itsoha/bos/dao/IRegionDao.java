package cn.itsoha.bos.dao;

import cn.itsoha.bos.domain.Region;

import java.util.List;

public interface IRegionDao extends IBaseDao<Region>{
    List<Region> findListByQ(String q);
}
