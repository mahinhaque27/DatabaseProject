public class quote 
{
	// Variables
	protected String note;
	protected String treesize;
	protected String treeheight;
	protected String status;
	protected String user_email;
	protected String quote_id;
	protected String quote_date;
	
	
	// Constructors
	public quote() {
		
	}
	
	public quote(String note) {
		this.note = note;
		this.user_email = "default@gmail.com";
	}
	
	public quote(String note, String user_email) {
		this.note = note;
		this.user_email = user_email;
	}
	
	public quote(String note, String user_email, String treesize, String treeheight, String status, String quote_id, String quote_date) {
		this.note = note;
		this.user_email = user_email;
		this.treesize = treesize;
		this.treeheight = treeheight;
		this.status = status;
		this.quote_date = quote_date;
		this.quote_id = quote_id;
	}
	
	public quote(String note, String user_email, String treesize, String treeheight, String quote_date) {
		this.note = note;
		this.user_email = user_email;
		this.treesize = treesize;
		this.treeheight = treeheight;
		this.quote_date = quote_date;
		
	}
	
	// Set/Get methods
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getUserEmail() {
		return user_email;
	}
	
	public void setUserEmail(String user_email) {
		this.user_email = user_email;
	}
	
	public String getTreeSize() {
		return treesize;
	}
	
	public void setTreeSize(String treesize) {
		this.treesize = treesize;
	}
	
	public String getTreeHeight() {
		return treeheight;
	}
	
	public void setTreeHeight(String treeheight) {
		this.treeheight = treeheight;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getQuoteid() {
		return quote_id;
	}
	
	public void setQuoteid(String quote_id) {
		this.quote_id = quote_id;
	}
	
	public String getQuoteDate() {
		return quote_date;
	}
	
	public void setQuoteDate(String quote_date) {
		this.quote_date = quote_date;
	}
	
}
