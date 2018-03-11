package cn.itsoha.bos.web.intercept;

import cn.itsoha.bos.domain.User;
import cn.itsoha.bos.utils.BOSUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class BOSLoginIntercept extends MethodFilterInterceptor{
    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {
        User user = BOSUtils.getLoginUser();
        if (user == null) {
            return "login";
        }
        return invocation.invoke();
    }
}
