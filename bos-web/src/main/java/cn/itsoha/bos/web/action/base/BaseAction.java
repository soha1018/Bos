package cn.itsoha.bos.web.action.base;

import cn.itsoha.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
    protected static final String HOME = "home";
    protected static final String LIST = "list";
    protected PageBean pageBean = new PageBean();
    private DetachedCriteria detachedCriteria = null;


    public void setPage(int page) {
        pageBean.setCurrentPage(page);
    }

    public void setRows(int rows) {
        pageBean.setPageSize(rows);
    }

    protected T model;
    @Override
    public T getModel() {
        return model;
    }

    /**
     * java转json并响应网站
     */
    public void java2json(Object o,String[] excludes) {
        //使用json封装数据
        JsonConfig config = new JsonConfig();
        //排除不需要封装的数据
        config.setExcludes(excludes);

        String json = JSONObject.fromObject(o,config).toString();
        //响应数据
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        try {
            ServletActionContext.getResponse().getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将指定Java对象转为json，并响应到客户端页面
     * @param o
     * @param exclueds
     */
    public void java2json(List o , String[] exclueds){
        JsonConfig jsonConfig = new JsonConfig();
        //指定哪些属性不需要转json
        jsonConfig.setExcludes(exclueds);
        String json = JSONArray.fromObject(o,jsonConfig).toString();
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        try {
            ServletActionContext.getResponse().getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BaseAction() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
        detachedCriteria = DetachedCriteria.forClass(clazz);
        //设置返回的结果集
        detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
        pageBean.setCriteria(detachedCriteria);
        try {
            model = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
