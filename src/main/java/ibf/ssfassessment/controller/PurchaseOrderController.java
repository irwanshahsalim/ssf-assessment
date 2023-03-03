package ibf.ssfassessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ibf.ssfassessment.model.Cart;
import ibf.ssfassessment.model.Delivery;
import ibf.ssfassessment.model.Item;
import ibf.ssfassessment.model.Quotation;
import ibf.ssfassessment.model.Invoice;
import ibf.ssfassessment.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PurchaseOrderController {

    @Autowired
    private PurchaseService purchaseSvc;

    @Autowired
    private QuotationService quotationSvc; 

    @GetMapping(path={"/", "view1.html"})
    public String getLanding(Model model, HttpSession session) {

        Cart cart = (Cart) session.getAttribute("cart"); 
        if (null == cart) {
            cart = new Cart(); 
            session.setAttribute("cart", cart);
        }
        model.addAttribute("item", new Item()); 
        model.addAttribute("cart", cart);   

        return "view1"; 
    }

    @PostMapping("/")
    public String postAdd(@Valid Item item, BindingResult result, Model model, HttpSession session) {

        Cart cart = (Cart) session.getAttribute("cart"); 

        if (result.hasErrors()) {
            model.addAttribute("item", item);
            model.addAttribute("cart", cart); 
            return "view1"; 
        }
        cart = purchaseSvc.aggregate(cart, item); 
        System.out.println(">>> cart contents after aggregation: " + cart); 
        session.setAttribute("cart", cart);
        session.setAttribute("item", item);

        model.addAttribute("item", item); 
        model.addAttribute("cart", cart);        

        return "view1"; 
    }

    @GetMapping("/shippingaddress")
    public String getAddress(Model model, HttpSession session) {

        Cart cart = (Cart) session.getAttribute("cart"); 
        System.out.println(cart); 

        if (cart.getContents().isEmpty()) {
            model.addAttribute("item", new Item());
            model.addAttribute("cart", cart);            
            return "view1"; 
        }
        model.addAttribute("delivery", new Delivery());

        Delivery delivery = (Delivery) session.getAttribute("delivery"); 
        if (null == delivery) {
            delivery = new Delivery(); 
            session.setAttribute("delivery", delivery);
        }

        model.addAttribute("delivery", delivery);      

        return "view2"; 
    }

    @PostMapping("/quotation")
    public String postAddress(@Valid Delivery delivery, BindingResult result, HttpSession session, Model model) throws Exception { 

        if (result.hasErrors()) {
            model.addAttribute("delivery", delivery); 
            return "view2"; 
        }
        Cart cart = (Cart) session.getAttribute("cart"); 
        List<String> items = quotationSvc.getList(cart); 
        System.out.println(items);
        
        Quotation quote = quotationSvc.getQuotations(items); 

        Invoice invoice = purchaseSvc.createInvoice(quote, delivery, cart); 
        model.addAttribute("invoice", invoice); 
        
        session.invalidate();

        return "view3"; 
    }
}