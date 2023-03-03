package ibf.ssfassessment.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Item {
 
    @NotNull(message="Please select an item from the drop down list")
    private String itemName;

    @NotNull(message="Please specify the quantity")
    @Min(value=1 , message="You must add at least 1 item")
    private Integer quantity;

    public String getItemName() {return itemName;}
    public void setItemName(String itemName) {this.itemName = itemName;}
    public Integer getQuantity() {return quantity;}
    public void setQuantity(Integer quantity) {this.quantity = quantity;}
    
    @Override
    public String toString() {
        return "Item [itemName=" + itemName + ", quantity=" + quantity + "]";
    } 
}
