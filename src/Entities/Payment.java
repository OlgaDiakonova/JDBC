package Entities;

import java.time.LocalDate;

public class Payment {
    private int id;
    private LocalDate dt;
    private Merchant merchant;
    private Customer cust;
    private String goods;
    private double sumPaid;
    private double chargePaid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDt() {
        return dt;
    }

    public void setDt(LocalDate dt) {
        this.dt = dt;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Customer getCust() {
        return cust;
    }

    public void setCust(Customer cust) {
        this.cust = cust;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public double getSumPaid() {
        return sumPaid;
    }

    public void setSumPaid(double sumPaid) {
        this.sumPaid = sumPaid;
    }

    public double getChargePaid() {
        return chargePaid;
    }

    public void setChargePaid(double chargePaid) {
        this.chargePaid = chargePaid;
    }

    public Payment(int id, LocalDate dt, Merchant merchant, Customer cust, String goods, double sumPaid, double chargePaid) {
        this.id = id;
        this.dt = dt;
        this.merchant = merchant;
        this.cust = cust;
        this.goods = goods;
        this.sumPaid = sumPaid;
        this.chargePaid = chargePaid;
    }
}
