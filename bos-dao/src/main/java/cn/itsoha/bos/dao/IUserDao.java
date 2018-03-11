package cn.itsoha.bos.dao;


import cn.itsoha.bos.domain.User;

public interface IUserDao extends IBaseDao<User> {

	public User findUserByUsernameAndPassword(String username, String password);

}
