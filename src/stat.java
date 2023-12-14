public class stat {
	// Variable
	protected String stat_id;
	protected String user_email;
	protected String quoteCount;
	protected String updateQuoteCount;
	protected String billAmount;
	protected String billStatus;
	protected String billDate;
	protected String paidDate;
	protected String paidAmount;
	
	
	// Constructors
	public stat() {
		
	}
	
	public stat(String stat_id, String user_email, String quoteCount, String updateQuoteCount,
            String billAmount, String billStatus, String billDate, String paidDate, String paidAmount) {
    this.stat_id = stat_id;
    this.user_email = user_email;
    this.quoteCount = quoteCount;
    this.updateQuoteCount = updateQuoteCount;
    this.billAmount = billAmount;
    this.billStatus = billStatus;
    this.billDate = billDate;
    this.paidDate = paidDate;
    this.paidAmount = paidAmount;
    
	}
	
	public stat(String user_email) {
		this.user_email = user_email;
	}
	// Set/Get methods
	public String getStat_id() {
        return stat_id;
    }

    public void setStat_id(String stat_id) {
        this.stat_id = stat_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getQuoteCount() {
        return quoteCount;
    }

    public void setQuoteCount(String quoteCount) {
        this.quoteCount = quoteCount;
    }

    public String getUpdateQuoteCount() {
        return updateQuoteCount;
    }

    public void setUpdateQuoteCount(String updateQuoteCount) {
        this.updateQuoteCount = updateQuoteCount;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }
}
