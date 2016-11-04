package informations;

public class Customer {
	public String customerName;
	public CusomerIdentifier identifier;
	public Customer(String customerName, CusomerIdentifier identifier) {
		// TODO Auto-generated constructor stub
		this.customerName = customerName;
		this.identifier = identifier;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public CusomerIdentifier getIdentifier() {
		return identifier;
	}
	public void setIdentifier(CusomerIdentifier identifier) {
		this.identifier = identifier;
	};
}
