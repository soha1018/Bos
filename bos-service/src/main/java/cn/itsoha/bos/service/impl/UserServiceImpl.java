package cn.itsoha.bos.service.impl;

import cn.itsoha.bos.dao.IUserDao;
import cn.itsoha.bos.domain.User;
import cn.itsoha.bos.service.IUserService;
import cn.itsoha.bos.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
    @Resource(name = "userDaoImpl")
    private IUserDao dao;

    /**
     * 登陆
     */
    @Override
    public User login(User model) {
        String md5 = MD5Utils.md5(model.getPassword());
        return dao.findUserByUsernameAndPassword(model.getUsername(), md5);
    }

    /**
     * 修改用户名密码
     */
    @Override
    public void editPass(String id, String password) {
        password = MD5Utils.md5(password);
        dao.executeUpdate("user.updatePass",password,id);
    }
}
