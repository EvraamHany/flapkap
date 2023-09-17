package flapkap.demo.model;

import java.util.List;
import java.util.Map;

public class PurchaseResponseDto {
    private double totalSpent;
    private List<Product> purchasedProducts;
    private Map<Integer, Integer> change;

    public double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public List<Product> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(List<Product> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    public Map<Integer, Integer> getChange() {
        return change;
    }

    public void setChange(Map<Integer, Integer> change) {
        this.change = change;
    }
}
