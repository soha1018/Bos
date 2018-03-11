package cn.itsoha.bos.utils;

import cn.itsoha.bos.domain.User;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpSession;

public class BOSUtils {
    /**
     * 获取Session
     */
    public static HttpSession getSession() {
        return ServletActionContext.getRequest().getSession();
    }

    public static User getLoginUser() {
        return (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
    }

}
