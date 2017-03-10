package jim.yang.example.util;


public class Pager {

	private int total;
	private int pagePerNum;
	
	private int currentPage;
	private int currentStartRow;
	private int currentEndRow;
	
	private int nextPage;
	private int nextStartRow;
	private int nextEndRow;
	
	public Pager(int total, int currentPage, int pagePerNum) {
		this.total = total;
		this.currentPage = currentPage;
		this.pagePerNum = pagePerNum;
		countCurrentRow();
		countNextRow();
	}
	
	private void countCurrentRow() {
		this.currentEndRow = this.currentPage * this.pagePerNum;
		if(this.currentEndRow > this.total) {
			this.currentEndRow = this.total;
		}
		this.currentStartRow = (this.currentPage - 1) * this.pagePerNum + 1;
	}
	
	private void countNextRow() {
		if(this.currentEndRow < this.total) {
			this.nextPage = this.currentPage + 1;
			this.nextStartRow = this.currentEndRow + 1;
			this.nextEndRow = this.nextStartRow + this.pagePerNum;
			if(this.nextEndRow > this.total) {
				this.nextEndRow = this.total;
			}
		}else {
			this.nextPage = this.currentPage;
			this.nextStartRow = this.currentStartRow;
			this.nextEndRow = this.currentEndRow;
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPagePerNum() {
		return pagePerNum;
	}

	public int getTotal() {
		return total;
	}

	public int getCurrentStartRow() {
		return currentStartRow;
	}

	public int getCurrentEndRow() {
		return currentEndRow;
	}

	public int getNextPage() {
		return nextPage;
	}
	
	public int getNextStartRow() {
		return nextStartRow;
	}

	public int getNextEndRow() {
		return nextEndRow;
	}
	
}
