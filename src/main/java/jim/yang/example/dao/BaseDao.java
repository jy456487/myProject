package jim.yang.example.dao;

import java.util.List;

public abstract interface BaseDao <T, E extends Exception>{
	 public abstract int insertEntity(T paramT) throws Exception;
			  
	 public abstract int insertList(List<T> paramList) throws Exception;
			  
	 public abstract int deleteEntity(T paramT) throws Exception;
			  
	 public abstract int updateEntity(T paramT) throws Exception;
			  
	 public abstract int countEntity(T paramT) throws Exception;
			  
	 public abstract T selectEntity(T paramT) throws Exception;
			  
	 public abstract List<T> selectList(T paramT) throws Exception;
}
