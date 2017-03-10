package jim.yang.example.util;

import java.util.HashMap;
import java.util.Map;

public class CommonMapUtil {
	
	public static Map<String, Object> buildCommonMapUtil(String table_name){
		Map<String, Object> map = null;
		if ("common_todo_list".equals(table_name)) {
			map = CommonMapUtil.buildCommonTodoEntity();
		}else if("common_company_news".equals(table_name)){
			map = CommonMapUtil.buildNewsinfo();
		}
		return map;
	}
	
	private static Map<String, Object> buildCommonTodoEntity() {
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("lab_id",null);
		map.put("matter_id",null);
		map.put("sub_type",null);
		map.put("rel_patient_id",null);
		map.put("rel_user_id",null);
		map.put("receive_time",null);
		map.put("rel_operation",null);
		map.put("rel_product_id",null);
		map.put("is_delete",null);
		return map ;
	}
	
	private static Map<String, Object> buildNewsinfo() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("lab_id", null);
		map.put("news_id", null);
		map.put("title", null);
		map.put("publish_date", null);
		map.put("publish_person", null);
		map.put("content", null);
		map.put("end_date", null);
		
		return map;
	}

	public static Map<String,Object> buildCommonEntity(){
		Map<String,Object> map = null ;
		map = CommonMapUtil.buildAreaCode();
		return map ;
	}
	
	private static Map<String, Object> buildAreaCode() {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("area_id",null);
		map.put("area_name",null);
		return map;
	}

	public static Map<String, Object> buildMedicineEntity() {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("medicine_id",null);//药品ID
		map.put("medicine_name",null);//药品名称
		map.put("medicine_prepa",null);//药品规格
		return map;
	}
}
