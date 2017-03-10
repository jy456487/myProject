package jim.yang.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jim.yang.example.service.CompanyNewsService;
import jim.yang.example.util.CommonMapUtil;
import jim.yang.example.util.Pager;
import jim.yang.example.util.ReturnMsg;
import jim.yang.example.util.WebUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller("companyNewsController")
@RequestMapping("/common/companyNews")
public class CompanyNewsController {
	private static final Logger log = LoggerFactory.getLogger(CompanyNewsController.class);
	private static String TABLE_NAME = "common_company_news";
	
	@Autowired
	private CompanyNewsService service;
	
	@RequestMapping("/addOne")
	public ReturnMsg addEntity(HttpServletRequest req,HttpSession session) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		Map<String, Object> map =CommonMapUtil.buildCommonMapUtil(TABLE_NAME);
		WebUtil.setProperties(map, req);
		String content=req.getParameter("content");
//		if(StringUtils.isNotBlank(content)){
//			content=content.replace("+", "");
//			map.put("content", content);
//		}
		map.put("lab_id", WebUtil.getLoginfo(session, "user_org_id"));
		log.info("添加公司新闻{}",map);
		int i= service.addEntity(map);
		if (i != 0) {
			rm.setSuccess("成功添加" + i + "条公司新闻");
		}else {
			rm.setFail("添加公司新闻失败");
		}
		return rm;
	}
	//公司新闻管理查询
//	@ResponseBody
	@RequestMapping("/listCompanyNews")
	public Map<String, Object> qryCompanyNewsInf(HttpServletRequest req,HttpSession session) throws Exception{
		Map<String, Object> map =CommonMapUtil.buildCommonMapUtil(TABLE_NAME);
		WebUtil.setProperties(map, req);
//		map.put("lab_name", req.getParameter("lab_name"));
		//添加实验室ID
//		String labId=WebUtil.getLoginfo(session, "user_org_id").toString();
//		if("000000000000000000000".equals(labId)){
//			map.put("lab_id", "" );
//		}else{
//			map.put("lab_id", labId );
//		}
		int count = service.count(map);
		log.info("查询公司新闻总数：{}", count);
		
		Pager pager = WebUtil.buildPager(req, count);
		Map<String, Object> param = WebUtil.buildPageMap(req, pager);
		param.put("param", map);
		List<Map<String, Object>> list = service.getList(param);
		return WebUtil.buildFlexigridData(pager, list, null);
	}
	
	
	@RequestMapping("/selectCompanyNews")
	public Map<String, Object> selectCompanyNewsInf(HttpServletRequest req,HttpSession session) throws Exception{
		Map<String, Object> map =CommonMapUtil.buildCommonMapUtil(TABLE_NAME);
		WebUtil.setProperties(map, req);
		map.put("lab_name", req.getParameter("lab_name"));
		//添加实验室ID
		String labId=WebUtil.getLoginfo(session, "user_org_id").toString();
		if("000000000000000000000".equals(labId)){
			map.put("lab_id", "" );
		}else{
			map.put("lab_id", labId );
		}
		int count = service.countEntityByLab(map);
		log.info("查询公司新闻总数：{}", count);
		
		Pager pager = WebUtil.buildPager(req, count);
		Map<String, Object> param = WebUtil.buildPageMap(req, pager);
		param.put("param", map);
		List<Map<String, Object>> list = service.selectListByLab(param);
		return WebUtil.buildFlexigridData(pager, list, null);
	}
	
	@RequestMapping("/mdOne")
	public ReturnMsg modifyEntity(HttpServletRequest req) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		Map<String, Object> map =CommonMapUtil.buildCommonMapUtil(TABLE_NAME);
		WebUtil.setProperties(map, req);
		int i = service.modifyEntity(map);
		if (i != 0) {
			rm.setSuccess("成功更新" + i + "个公司新闻");
		} else {
			rm.setFail("更新公司新闻失败");
		}
		return rm;
	}
	
	@RequestMapping("/qryOne")
	public Map<String, Object> queryOne(HttpServletRequest req) throws Exception {
		Map<String, Object> map =CommonMapUtil.buildCommonMapUtil(TABLE_NAME);
		WebUtil.setProperties(map, req);
		return service.getEntity(map);
	}
	
	@RequestMapping("/rmList")
	public ReturnMsg removeList(HttpServletRequest req,HttpSession session) throws Exception {
		ReturnMsg rm = new ReturnMsg();
		Map<String,Object> map = new HashMap<String, Object>();
		WebUtil.setIds(map, req);
		int i = service.removeEntity(map);
		if (i != 0) {
			rm.setSuccess("成功删除" + i + "条公司新闻");
		}else {
			rm.setFail("删除公司新闻失败");
		}
		return rm;
	}
	
	
	
	@RequestMapping("/checkNewsTitle")
	@ResponseBody
	public boolean checkNewsTitle(HttpServletRequest req,HttpSession session) throws Exception {
		log.info("判断新闻标题是否唯一");//应该加lab_id判断
		Map<String,Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(req.getParameter("title"))){
			map.put("title", req.getParameter("title")); 
			map.put("lab_id", req.getParameter("lab_id")); 
			int count = service.count(map);//title是精确查询
			if(count>0){
				return false;
			}
			return true;
		}
		return false;
	}
	
	
}
