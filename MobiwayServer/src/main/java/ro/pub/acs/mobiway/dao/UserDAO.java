package ro.pub.acs.mobiway.dao;

import java.io.Serializable;
import java.util.List;

import ro.pub.acs.mobiway.model.User;

public interface UserDAO extends Serializable {
	public List<User> list();

	public User get(int id);

	public User get(String email);

	public User get(String token, int id);

	public User get(String email, String password);

	public void delete(User user);

	public int update(User user);

	public int add(User user);

	public User loginUser(String username, String password);
	
    public List<User> getUsersWithPhone();
}