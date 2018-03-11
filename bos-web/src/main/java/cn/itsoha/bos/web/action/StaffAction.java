package cn.itsoha.bos.web.action;

import cn.itsoha.bos.domain.Staff;
import cn.itsoha.bos.service.StaffService;
import cn.itsoha.bos.utils.PageBean;
import cn.itsoha.bos.web.action.base.BaseAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{
    private int page;
    private int rows;
    private String ids;
    @Resource(name = "staffServiceImpl")
    private StaffService staffService;

    /**
     * 添加取派员
     */
    public String add() {
        staffService.addStaff(model);
        return LIST;
    }


    /**
     *分页查询取派员
     */
    public String pageQuery() throws IOException {
        //查询分页数据
        staffService.pageQuery(pageBean);
        //封装成json发送给页面
        this.java2json(pageBean,new String[]{"currentPage","criteria","pageSize","decidedzones"});
        return NONE;
    }

    /**
     * 删除取派员
     */
    public String deleteBatch() throws Exception {
        staffService.deleteBatch(ids);
        return LIST;
    }

    /**
     * 修改取派员
     */
    public String edit() throws Exception{
        staffService.addStaff(model);
        return LIST;
    }

    /**
     * 查询未被删除的取派员
     */
    public String listAjax() throws Exception {
        List<Staff> list = staffService.findListByNotDelete();
        this.java2json(list,new String[]{"decidedzones","telephone","haspda","deltag","station","standard"});
        return NONE;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
