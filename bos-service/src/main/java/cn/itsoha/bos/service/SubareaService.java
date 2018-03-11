package cn.itsoha.bos.service;

import cn.itsoha.bos.domain.Subarea;
import cn.itsoha.bos.utils.PageBean;

import java.util.List;

public interface SubareaService {
    void save(Subarea model);

    void pageQuery(PageBean pageBean);

    List<Subarea> findAll();

    List<Subarea> findListByNotAssociation();
}
