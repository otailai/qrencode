package informations;

public class Vendor {
	public String vendorName;
	public String vendorAddress;
	public VendorStatus status;
	public Vendor(String vendorName, String vendorAddress, VendorStatus status) {
		super();
		this.vendorName = vendorName;
		this.vendorAddress = vendorAddress;
		this.status = status;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorAddress() {
		return vendorAddress;
	}
	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}
	public VendorStatus getStatus() {
		return status;
	}
	public void setStatus(VendorStatus status) {
		this.status = status;
	}
	
}
