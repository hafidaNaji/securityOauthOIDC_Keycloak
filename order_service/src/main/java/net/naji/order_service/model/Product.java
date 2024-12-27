package net.naji.order_service.model;



import lombok.*;



@AllArgsConstructor @NoArgsConstructor @Setter  @Getter @ToString @Builder
public class Product {

    private String id;
    private String name;
    private int price;
    private int quantity;
}
