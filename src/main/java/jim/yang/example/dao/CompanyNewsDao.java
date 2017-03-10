package jim.yang.example.dao;

import java.util.List;
import java.util.Map;

public interface CompanyNewsDao extends BaseDao<Map<String, Object>, Exception>{
	
	List<Map<String, Object>> selectListByLab(Map<String, Object> arg0) throws Exception;
    int countEntityByLab(Map<String, Object> arg0) throws Exception;
	int updateEntityList(Map<String, Object> map) throws Exception;
}
