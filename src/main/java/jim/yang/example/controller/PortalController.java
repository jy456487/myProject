package jim.yang.example.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PortalController {

	
	@RequestMapping(value = "/userPage")
	public String view(HttpServletRequest request, HttpServletResponse response) throws Exception {
	   
		return "example/user-page";
	}
	
	@RequestMapping(value = "/userList")
	public String view2(HttpServletRequest request, HttpServletResponse response) throws Exception {
	   
		return "example/user-list";
	}
	
	@RequestMapping(value = "/first")
	public String first(HttpServletRequest request, HttpServletResponse response) throws Exception {
	   
		return "portal/first";
	}
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
	   
		return "html/index";
	}
	
	@RequestMapping(value = "/tables")
	public String tables(HttpServletRequest request, HttpServletResponse response) throws Exception {
	   
		return "table/tables";
	}
}
