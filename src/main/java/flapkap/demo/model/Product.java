package flapkap.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "product")

public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    Long id;
    @Column(name = "amount_available")
    String amountAvailable;
    @Column(name = "cost")
    Integer cost;
    @Column(name = "product_name")
    BigDecimal productName;
    @Column(name = "seller_id")
    BigDecimal sellerId;

    public Product(Long id, Integer cost) {
        this.id=id;
        this.cost=cost;
    }

    public Product() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(String amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public BigDecimal getProductName() {
        return productName;
    }

    public void setProductName(BigDecimal productName) {
        this.productName = productName;
    }

    public BigDecimal getSellerId() {
        return sellerId;
    }

    public void setSellerId(BigDecimal sellerId) {
        this.sellerId = sellerId;
    }

}
