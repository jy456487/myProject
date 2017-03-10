package jim.yang.example.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jim.yang.example.dao.IUserOperation;
import jim.yang.example.util.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("article")
public class UserController {
	@Autowired
	IUserOperation userMapper;

	@RequestMapping("/list")
	public ModelAndView listall(HttpServletRequest request,HttpServletResponse response){
		List<Article> articles=userMapper.getUserArticles(1); 
		ModelAndView mav=new ModelAndView("list");
		mav.addObject("articles",articles);
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("/listData")
	public Map<String,Object> listData(HttpServletRequest request,HttpServletResponse response){
		List<Article> articles=userMapper.getUserArticles(1); 
		Map<String,Object> mapData=new HashMap<String,Object>();
		mapData.put("current", 2);
		mapData.put("rowCount", 2);
		mapData.put("rows",articles);
		mapData.put("total",articles.size());
		return mapData;
	}
	
}
