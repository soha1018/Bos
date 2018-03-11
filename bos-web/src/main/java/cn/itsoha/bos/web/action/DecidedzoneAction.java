package cn.itsoha.bos.web.action;

import cn.itsoha.bos.domain.Decidedzone;
import cn.itsoha.bos.service.DecidedzoneService;
import cn.itsoha.bos.web.action.base.BaseAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {
    private String[] subareaid;
    @Resource(name = "decidedzoneServiceImpl")
    private DecidedzoneService decidedzoneService;

    /**
     * 定区添加
     */
    public String add() throws Exception {
        decidedzoneService.save(model,subareaid);
        return LIST;
    }

    /**
     * 返回定区数据
     */
    public String pageQuery() throws Exception {
        decidedzoneService.pageQuery(pageBean);
        this.java2json(pageBean, new String[]{"currentPage", "criteria", "pageSize","subareas","decidedzones"});
        return NONE;
    }

    public String[] getSubareaid() {
        return subareaid;
    }

    public void setSubareaid(String[] subareaid) {
        this.subareaid = subareaid;
    }
}
