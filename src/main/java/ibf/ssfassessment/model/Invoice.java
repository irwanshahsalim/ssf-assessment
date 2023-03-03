package ibf.ssfassessment.model;

public class Invoice {

    private String invoiceId;
    private Delivery delivery; 
    private Float total;

    public String getInvoiceId() {return invoiceId;}
    public void setInvoiceId(String invoiceId) {this.invoiceId = invoiceId;}
    public Delivery getDelivery() {return delivery;}
    public void setDelivery(Delivery delivery) {this.delivery = delivery;}
    public Float getTotal() {return total;}
    public void setTotal(Float total) {this.total = total;}

    public String getName() {return delivery.getName();}
    public String getAddress() {return delivery.getAddress();}
    
}
