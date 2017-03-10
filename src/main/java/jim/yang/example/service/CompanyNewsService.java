package jim.yang.example.service;

import java.util.List;
import java.util.Map;

public interface CompanyNewsService {
	public int addEntity(Map<String,Object> map) throws Exception;
	
	public  int modifyEntity(Map<String,Object> map) throws Exception;

	public int count(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> getList(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> getEntity(Map<String, Object> arg0) throws Exception;
	
	List<Map<String, Object>> selectListByLab(Map<String, Object> arg0) throws Exception;
	
	int modifyEntityList(Map<String, Object> map) throws Exception;
	
	public int countEntityByLab(Map<String, Object> map) throws Exception;
	
	public int removeEntity(Map<String, Object> map) throws Exception;
	
	
}
