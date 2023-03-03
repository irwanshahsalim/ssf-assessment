package ibf.ssfassessment.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Delivery {

    @NotNull(message="Please fill in your name")
    @NotEmpty(message="Please fill in your name")
    @Size(min=2, message="Name must be at least 2 characters long")
    private String name; 

    @NotNull(message="Please fill in your address")
    @NotEmpty(message="Please fill in your address")
    private String address;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;} 
    
}

