package informations;

public class Produtcion {
	public String productionName;
	public int productionNumber;
	public String destination;
	public ProductionStatus status;
	public Produtcion(String productionName, int productionNumber, String destination, ProductionStatus status) {
		super();
		this.productionName = productionName;
		this.productionNumber = productionNumber;
		this.destination = destination;
		this.status = status;
	}
	public String getProductionName() {
		return productionName;
	}
	public void setProductionName(String productionName) {
		this.productionName = productionName;
	}
	public int getProductionNumber() {
		return productionNumber;
	}
	public void setProductionNumber(int productionNumber) {
		this.productionNumber = productionNumber;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public ProductionStatus getStatus() {
		return status;
	}
	public void setStatus(ProductionStatus status) {
		this.status = status;
	}
	
}
