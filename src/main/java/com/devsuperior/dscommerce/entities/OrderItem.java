package com.devsuperior.dscommerce.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@NoArgsConstructor
@Entity
@Table(name = "tb_order_item")
public class OrderItem {
    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();
    @Getter
    @Setter
    private Integer quantity;
    @Getter
    @Setter
    private Double price;

    public OrderItem(Order order, Product product, Integer quantity, Double price){
        this.id.setOrder(order);
        this.id.setProduct(product);
        this.setQuantity(quantity);
        this.setPrice(price);
    }

    public Order getOrder(){
        return this.id.getOrder();
    }
    public void setOrder(Order order){
        this.id.setOrder(order);
    }

    public Product getProduct(){
        return this.id.getProduct();
    }

    public void setProduct(Product product){
        this.id.setProduct(product);
    }


}
