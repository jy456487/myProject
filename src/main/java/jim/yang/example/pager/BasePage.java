package jim.yang.example.pager;

public class BasePage {
//	private Map<String,Object> mapData;
	private int current;        //当前页
	private int rowCount;   //当前页显示的条数
//	this.pageBean=this.deviceQueryService.deviceQueryList(current, rowCount,sort);
//	mapData.put("current", current);
//	mapData.put("rowCount", rowCount);
//	mapData.put("rows",this.pageBean.getList());
//	mapData.put("total", this.pageBean.getTotalrecord());

//	public Map<String, Object> getMapData() {
//	    return mapData;
//	}
//
//	public void setMapData(Map<String, Object> mapData) {
//	    this.mapData = mapData;
//	}

	public int getCurrent() {
	    return current;
	}

	public void setCurrent(int current) {
	    this.current = current;
	}

	public int getRowCount() {
	    return rowCount;
	}

	public void setRowCount(int rowCount) {
	    this.rowCount = rowCount;
	}

}
