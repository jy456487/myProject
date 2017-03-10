package jim.yang.example.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jim.yang.example.dao.CompanyNewsDao;
import jim.yang.example.service.CompanyNewsService;
import jim.yang.example.util.IdWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("companyNewsServiceImpl")
public class CompanyNewsServiceImpl implements CompanyNewsService{
	
	private static Logger log = LoggerFactory.getLogger(CompanyNewsServiceImpl.class);
	private static String TABLE_NAME = "common_company_news";
	private static String TABLE_ID = "news_id";
	private static String TABLE_ID_PREFIX = "CNI";
	private static IdWorker worker = new IdWorker(0, 0);
	@Autowired 
	private CompanyNewsDao dao;
	
	public int addEntity(Map<String, Object> arg0) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		arg0.put(TABLE_ID, TABLE_ID_PREFIX + String.valueOf(worker.nextId()));
		paramMap.put("param", arg0);
		paramMap.put("tbl_name", TABLE_NAME);
		int i=dao.insertEntity(paramMap);
		return i;
	}

	public int count(Map<String, Object> map) throws Exception {
		log.debug("查询条件：{}", map);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("param", map);
		paramMap.put("tbl_name", TABLE_NAME);
		return dao.countEntity(paramMap);
	}
	
	public int countEntityByLab(Map<String, Object> map) throws Exception {
		log.debug("查询条件，关联实验室表：{}", map);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("param", map);
		paramMap.put("tbl_name", TABLE_NAME);
		return dao.countEntityByLab(paramMap);
	}

	public List<Map<String, Object>> getList(Map<String, Object> arg0) throws Exception {
		log.debug("查询条件：{}", arg0);
		arg0.put("tbl_name", TABLE_NAME);
		return dao.selectList(arg0);
	}
	
	public int modifyEntity(Map<String, Object> map) throws Exception {
		log.debug("更新信息：{}", map);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("param", map);
		paramMap.put("tbl_name", TABLE_NAME);
		paramMap.put("tbl_id", map.get(TABLE_ID));
		return dao.updateEntity(paramMap);
	}
	
	public Map<String, Object> getEntity(Map<String, Object> arg0) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("param"   , arg0);
		paramMap.put("tbl_name", TABLE_NAME);
		paramMap.put("tbl_id", arg0.get(TABLE_ID));
		log.info("msg{}",paramMap);
		Map<String, Object> resultMap  = dao.selectEntity(paramMap);
		log.info("resultMaps{}",resultMap);
		return resultMap;
	}
	
	public List<Map<String, Object>> selectListByLab(Map<String, Object> arg0) throws Exception{
		log.debug("查询条件,关联实验室：{}", arg0);
		arg0.put("tbl_name", TABLE_NAME);
		Object sortName=arg0.get("sortname");
		if(sortName==null){
			arg0.put("sortname", "news_id");
			arg0.put("sortorder", "desc");
			
		}
		return dao.selectListByLab(arg0);
	
	}
	
	public int modifyEntityList(Map<String, Object> paramMap) throws Exception {
		paramMap.put("tbl_name", TABLE_NAME);
		log.info("批量修改条件：{}", paramMap);
		return dao.updateEntityList(paramMap);
	}
	
	public int removeEntity(Map<String, Object> arg0) throws Exception {
		log.info("删除公司新闻", arg0);
		arg0.put("tbl_name", TABLE_NAME);
		return dao.deleteEntity(arg0);
	}
	
	
	
}
