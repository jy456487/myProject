package jim.yang.example.util;

import java.util.List;
import java.util.Map;

public class ReturnMsg extends BaseBean {
	private List<?> rows;
	private int total;
	private String rspcod;
	private String rspmsg;
	private String url;
	private Object obj;

	public ReturnMsg() {
	}

	public ReturnMsg(String rspcod, String rspmsg) {
		this.rspcod = rspcod;
		this.rspmsg = rspmsg;
	}

	public Object getObj() {
		return this.obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getRspcod() {
		return this.rspcod == null ? "200" : this.rspcod;
	}

	public void setRspcod(String rspcod) {
		this.rspcod = rspcod;
	}

	public String getRspmsg() {
		return this.rspmsg == null ? "失败" : this.rspmsg;
	}

	public void setRspmsg(String rspmsg) {
		this.rspmsg = rspmsg;
	}

	public String getUrl() {
		return this.url == null ? "" : this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ReturnMsg setFail() {
		setRspcod("201");
		return this;
	}

	public ReturnMsg setFail(String msg) {
		setRspcod("201");
		setRspmsg(msg);
		return this;
	}

	public ReturnMsg setFail(String msg, String url) {
		setRspcod("201");
		setRspmsg(msg);
		setUrl(url);
		return this;
	}

	public ReturnMsg setFail(String msg, Map<?, ?> data) {
		setRspcod("201");
		setRspmsg(msg);
		return this;
	}

	public ReturnMsg setFail(String msg, String url, Map<?, ?> data) {
		setRspcod("201");
		setRspmsg(msg);
		setUrl(url);

		return this;
	}

	public ReturnMsg setSuccess() {
		setRspcod("200");
		return this;
	}

	public ReturnMsg setSuccess(String msg) {
		setRspcod("200");
		setRspmsg(msg);
		return this;
	}

	public ReturnMsg setSuccess(String msg, String url) {
		setRspcod("200");
		setRspmsg(msg);
		setUrl(url);
		return this;
	}

	public ReturnMsg setSuccess(String msg, Map<?, ?> data) {
		setRspcod("200");
		setRspmsg(msg);

		return this;
	}

	public ReturnMsg setSuccess(String msg, String url, Map<?, ?> data) {
		setRspcod("200");
		setRspmsg(msg);
		setUrl(url);

		return this;
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getRows() {
		return this.rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public void setMsg(String rspcod, String rspmsg) {
		this.rspcod = rspcod;
		this.rspmsg = rspmsg;
	}
}