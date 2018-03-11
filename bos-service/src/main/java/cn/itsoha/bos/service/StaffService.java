package cn.itsoha.bos.service;

import cn.itsoha.bos.domain.Staff;
import cn.itsoha.bos.utils.PageBean;

import java.util.List;

public interface StaffService {
    void addStaff(Staff model);

    void pageQuery(PageBean pageBean);

    void deleteBatch(String ids);

    List<Staff> findListByNotDelete();
}
