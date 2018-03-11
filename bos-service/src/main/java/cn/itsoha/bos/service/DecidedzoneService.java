package cn.itsoha.bos.service;

import cn.itsoha.bos.domain.Decidedzone;
import cn.itsoha.bos.utils.PageBean;

import java.util.List;

public interface DecidedzoneService {
    void save(Decidedzone model, String[] subareaid);

    void pageQuery(PageBean pageBean);
}
