package jim.yang.example.dao;

import java.util.List;

import jim.yang.example.util.Article;
import jim.yang.example.util.User;

public interface IUserOperation {
    
	public User selectUserByID(int id);
	public List<User> selectUsers(String userName);	
	public void addUser(User user);
	public void updateUser(User user);
	public void deleteUser(int id);
	
	public List<Article> getUserArticles(int id);
	
}
