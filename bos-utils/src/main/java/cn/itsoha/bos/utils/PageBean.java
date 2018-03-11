package cn.itsoha.bos.utils;

import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

/**
 * 分页查询工具类
 */
public class PageBean {
    /**
     * 当前页面
     */
    private int currentPage;
    /**
     * 每一页显示多少数据
     */
    private int pageSize;
    /**
     * 总数
     */
    private int total;
    /**
     * 返回查询的数据集合
     */
    private List rows;
    /**
     * 离线查询对象
     */
    private DetachedCriteria criteria;


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public DetachedCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(DetachedCriteria criteria) {
        this.criteria = criteria;
    }
}
