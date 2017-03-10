package jim.yang.example.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;


public class WebUtil {
	
	private static Logger log = LoggerFactory.getLogger(WebUtil.class);
	
	/**
	 * 组分页信息
	 * @param req
	 * @param count
	 * @return
	 */
	public static Pager buildPager(HttpServletRequest req, int count) {
		int currentPage = 1;
		int pagePerNum = 10;
		if(!StringUtils.isBlank(req.getParameter("page"))) {
			currentPage = Integer.parseInt(req.getParameter("page"));
		}
		if(!StringUtils.isBlank(req.getParameter("rp"))) {
			pagePerNum = Integer.parseInt(req.getParameter("rp"));
		}
		return new Pager(count, currentPage, pagePerNum);
	}
	
	
	/**
	 * 将请求中的参数值设置到指定key的map中取
	 * @map map
	 * @map req
	 */
	public static void setProperties(Map<String, Object> map, HttpServletRequest req) {
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			map.put(key, req.getParameter(key));
		}
	}
	/**
	 * 组jTreetable用数据
	 * @param list
	 * @param id
	 * @param parent
	 * @param children
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public static Map<String, Object> buildJTreetableList(List<Map<String, Object>> list, String id, String parent, String children) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for(int i = 0; i < list.size(); i ++) {
			Map<String, Object> node = list.get(i);
			if("#".equals(node.get(parent))) {
				//组一级菜单数据
				Map<String, Object> newNode = new HashMap<String, Object>();
				newNode.putAll(node);
				newNode.put(children, new ArrayList<Map<String, Object>>());
				result.add(newNode);
			}else {
				//组二级菜单数据
				for(int j = 0; j < result.size(); j ++) {
					Map<String, Object> data = result.get(j);
					if(node.get(parent).equals(data.get(id).toString())) {
						List<Map<String, Object>> l = (List<Map<String, Object>>) data.get(children);
						Map<String, Object> newNode = new HashMap<String, Object>();
						newNode.putAll(node);
						newNode.put(children, new ArrayList<Map<String, Object>>(1));
						l.add(newNode);
						break;
					}else{
						List<Map<String, Object>> l = (List<Map<String, Object>>) data.get(children);
						for (int k = 0; k < l.size(); k++) {
							Map<String, Object> subdata = l.get(k);
							if (node.get(parent).equals(subdata.get(id).toString())) {
								List<Map<String, Object>> n = (List<Map<String, Object>>) subdata.get(children);
								Map<String, Object> newNode1 = new HashMap<String, Object>();
								newNode1.putAll(node);
								newNode1.put(children, new ArrayList<Map<String, Object>>(1));
								n.add(newNode1);
								break;
							}
						}
					}
				}
				//addChild(result, node, id, parent, children);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("list", result);
		return map;
	}
	
	private static void addChild(List<Map<String, Object>> result, Map<String, Object> node, String id, String parent, String children) {
		for(int i = 0; i < result.size(); i ++) {
			Map<String, Object> data = result.get(i);
			if(node.get(parent).equals(data.get(id).toString())) {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> l = (List<Map<String, Object>>) data.get(children);
				Map<String, Object> newNode = new HashMap<String, Object>();
				newNode.putAll(node);
				newNode.put(children, new ArrayList<Map<String, Object>>(1));
				l.add(newNode);
				break;
			}
		}
	}

	/**
	 * 组分页用map参数
	 * @param req
	 * @param count
	 * @return
	 */
	public static Map<String, Object> buildPageMap(HttpServletRequest req, Pager pager) {
		Map<String, Object> map = new HashMap<String, Object>();
		int currentPage = 1;
		int pagePerNum = 10;
		if(!StringUtils.isBlank(req.getParameter("page"))) {
			currentPage = Integer.parseInt(req.getParameter("page"));
		}
		if(!StringUtils.isBlank(req.getParameter("rp"))) {
			pagePerNum = Integer.parseInt(req.getParameter("rp"));
		}
		map.put("page_num", currentPage);
		//mysql用分页信息
		map.put("page_start_1", pager.getCurrentStartRow() - 1);
		map.put("page_limit", pagePerNum);
		//oracle用分页信息
		map.put("page_start", pager.getCurrentStartRow());
		map.put("page_end", pager.getCurrentEndRow());
		//排序用信息
		if(!StringUtils.isBlank(req.getParameter("sortname"))) {
			map.put("sortname", req.getParameter("sortname"));
		}
		if(!StringUtils.isBlank(req.getParameter("sortorder"))) {
			map.put("sortorder", req.getParameter("sortorder"));
		}
		return map;
	}
	
	
	


	/**
	 * 将结果集拼装成FlexiGrid用的格式
	 * @map pager
	 * @map list
	 * @map extraData(可存放汇总数据等)
	 * @return
	 */
	public static Map<String, Object> buildFlexigridData(Pager pager, List<Map<String, Object>> list, Map<String, ?> extraData) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("page", pager.getCurrentPage());
		result.put("total", pager.getTotal());
		result.put("rows", list);
		if(extraData != null && extraData.size() > 0) {
			Set<String> keySet = extraData.keySet();
			for(Iterator<String> iter=keySet.iterator(); iter.hasNext();) {
				String key = iter.next();
				String val = String.valueOf(extraData.get(key));
				result.put(key.toLowerCase(), val.trim());
			}
		}
		return result;
	}
	
	/**
	 * 获取session中登录信息
	 * @param session
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static Object getLoginfo(HttpSession session, String key) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) session.getAttribute("loginfo");
		if(map == null || map.get(key) == null) {
			throw new Exception("会话过期,请重新登录系统");
		}
		return map.get(key);
	}
	
	/**
	 * 将请求中的ids参数值(逗号分隔)转换为数组设置到指定map的key(ids)中
	 * @map map
	 * @map req
	 */
	public static void setIds(Map<String, Object> map, HttpServletRequest req) {
		map.put("ids", req.getParameter("ids").split(","));
	}
	
	
	/**
	 * 将上传文件保存至指定目录
	 * @param file 上传文件 springmvc中可在controller参数中设置<br/>
	 * 如： xxxUpload(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response)
	 * @param newFilePath 保存至目录
	 * @param newFileName 新的文件名
	 * @return 新的文件
	 * @throws IOException
	 */
	public static File fileUpload(MultipartFile file, String newFilePath, String newFileName) throws IOException {
		byte[] bytes = file.getBytes();
        String sep = System.getProperty("file.separator");
        String newFile = new StringBuffer().append(newFilePath).append(sep).append(newFileName).toString();
        File uploadedFile = new File(newFile);  
        FileCopyUtils.copy(bytes, uploadedFile);
        log.info("文件[{}]成功保存至[{}]", file.getOriginalFilename(), newFile);
		return uploadedFile;
	}
	
	/**
	 * 将上传文件保存至指定目录
	 * @param file 上传文件 springmvc中可在controller参数中设置<br/>
	 * 如： xxxUpload(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response)
	 * @param newFilePath 保存至目录
	 * @param newFileName 新的文件名
	 * @return 新的文件
	 * @throws IOException
	 */
	public static File fileUpload(byte[] bytes, String newFilePath, String newFileName) throws IOException {
        String sep = System.getProperty("file.separator");
        String newFile = new StringBuffer().append(newFilePath).append(sep).append(newFileName).toString();
        File uploadedFile = new File(newFile);  
       // FileOutputStream outputStream = new FileOutputStream(uploadedFile);
       // outputStream.write(bytes);
        FileCopyUtils.copy(bytes, uploadedFile);
        log.info("文件成功保存至[{}]", newFile);
		return uploadedFile;
	}
	
	/**
	 * @param file 上传文件 springmvc中可在controller参数中设置<br/>
	 * 如： xxxUpload(@RequestParam MultipartFile file, String []columns)
	 * @return 数组
	 * @throws IOException
	 */
//	public static List<Map<String, Object>> fileRead(MultipartFile file, String []columns) throws IOException {
//		InputStream is = file.getInputStream(); 
//		 HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
//	        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//	        for (int i = 0; i < hssfWorkbook.getNumberOfSheets(); i++) {
//	            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(i);
//	            if (hssfSheet == null) {
//	                continue;
//	            }
//	            for (int row = 1; row <= hssfSheet.getLastRowNum(); row++) {
//	                HSSFRow hssfRow = hssfSheet.getRow(row);
//	                if (hssfRow == null) {
//	                    continue;
//	                }
//	                Map<String, Object> map = new HashMap<String, Object>() ;
//					for(int col = 0; col < columns.length; col++){
//						Cell cell = hssfRow.getCell(col);
//						cell.setCellType(Cell.CELL_TYPE_STRING);
//						map.put(columns[col], cell.getStringCellValue().toString());
//						
//						
//					}
//					list.add(map);
//	            }
//	        }
//	        return list;
//	}
	
	/**
	 * 下载指定文件  请注意文件命名不要重复
	 * @param response
	 * @param file
	 * @param autoDelete
	 * @throws IOException 
	 */
	public static void fileDownload( HttpServletRequest request,HttpServletResponse response,File file, boolean autoDelete) throws IOException {
		response.setContentType("multipart/form-data");  //设置文件ContentType类型，这样设置，会自动判断下载文件类型
		//response.setCharacterEncoding("UTF-8");
		

		//文件名编码
		//String fileName = URLDecoder.decode(file.getName(), "UTF-8");
		//String fileName = new String(file.getName().getBytes(),"UTF-8");
		String fileName = encodeDownloadFileName(request, file.getName());
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);  //设置文件头：最后一个参数是设置下载文件名
		ServletOutputStream out = null;
//		FileInputStream in = null;
		try {
			out = response.getOutputStream();  //通过response获取ServletOutputStream对象(out)
//			in = new FileInputStream(file);
//			int b = 0;
//			byte[] buffer = new byte[512];
//			while (b != -1) {
//				b = in.read(buffer);
//				out.write(buffer, 0, b);
//			}
			out.write(FileUtil.getFileBytes(file));  //通过nio一次性写入到out中(以上注释的内容为传统方式)
			out.flush();
		} catch (IOException e) {
			log.error("文件下载异常[{}]", e);
			throw e;
		} finally {
			CloseUtil.close(out);
//			CloseUtil.close(in);
			if(autoDelete) {
				if(file != null && file.exists()) {
					file.delete();
				}
			}
		}
	} 
	
	
	public static void fileDownloadByOrginName( HttpServletRequest request,HttpServletResponse response,File file, String name) throws IOException {
		response.setContentType("multipart/form-data");  //设置文件ContentType类型，这样设置，会自动判断下载文件类型
		String fileName = encodeDownloadFileName(request, name);
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);  //设置文件头：最后一个参数是设置下载文件名
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();  //通过response获取ServletOutputStream对象(out)
			out.write(FileUtil.getFileBytes(file));  //通过nio一次性写入到out中(以上注释的内容为传统方式)
			out.flush();
		} catch (IOException e) {
			log.error("文件下载异常[{}]", e);
			throw e;
		} finally {
			CloseUtil.close(out);
		}
	}  
	

	public static String encodeDownloadFileName(HttpServletRequest request,String fileName){
		try{
			String agent = request.getHeader("USER-AGENT");
			if (null != agent && -1 != agent.indexOf("MSIE")){
				fileName=java.net.URLEncoder.encode(fileName, "UTF-8");
			} else if (null != agent && -1 != agent.indexOf("Mozilla")){
				String encoding=request.getCharacterEncoding();
				fileName =new String(fileName.getBytes(encoding),"ISO8859-1");
			}
		}catch (UnsupportedEncodingException e) {
			log.error("encodeDownloadFileName()", e);
		}
    	return fileName;
	}

}
