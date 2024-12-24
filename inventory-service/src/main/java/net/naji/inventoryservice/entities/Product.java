package net.naji.inventoryservice.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;



@Entity
@AllArgsConstructor @NoArgsConstructor @Setter  @Getter @ToString @Builder
public class Product {
    @Id
    private String id;
    private String name;
    private int price;
    private int quantity;
}
