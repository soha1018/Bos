package cn.itsoha.bos.service;

import cn.itsoha.bos.domain.User;

public interface IUserService {
    User login(User model);

    void editPass(String id, String password);
}
