package jim.yang.example.util;

import java.util.Map;

public class BaseBean {
	public BaseBean() {
	}

	public Map<String, Object> toMap() throws Exception {
		return BeanUtils.converToMap(this);
	}
}