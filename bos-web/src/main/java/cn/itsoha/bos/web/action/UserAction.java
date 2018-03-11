package cn.itsoha.bos.web.action;

import cn.itsoha.bos.domain.User;
import cn.itsoha.bos.service.IUserService;
import cn.itsoha.bos.utils.BOSUtils;
import cn.itsoha.bos.web.action.base.BaseAction;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User>{
    private String checkcode;
    @Resource(name = "userServiceImpl")
    private IUserService service;

    public String Login() {
        String code = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
        if (StringUtils.isNotBlank(checkcode) && checkcode.equals(code)) {
            //验证码正确,然后登陆
            User user = service.login(model);
            if (user != null) {
                ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
                return HOME;
            } else {
                this.addActionError("用户名或密码错误");
                return LOGIN;
            }
        } else {
            //验证码错误
            this.addActionError("验证码不正确！");
            return LOGIN;
        }
    }

    /**
     * 修改用户密码
     */
    public String editPass() throws Exception{
        User user = BOSUtils.getLoginUser();
        String tag = "1";
        try {
            service.editPass(user.getId(),model.getPassword());
        } catch (Exception e) {
            tag = "0";
            e.printStackTrace();
        }
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        ServletActionContext.getResponse().getWriter().write(tag);
        return NONE;
    }

    public String logout() {
        ServletActionContext.getRequest().getSession().removeAttribute("loginUser");
        return LOGIN;
    }

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
}
