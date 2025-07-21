package it.unisa.tsw_proj.model.bean;

import it.unisa.tsw_proj.model.OrderState;

import java.util.ArrayList;
import java.util.List;

public class OrderBean {

    // Costruttore
    public OrderBean() {
        orderItems = new ArrayList<>();
    }

    public OrderBean(int id, int idUser, String orderNr, String orderDate, double totalPrice, double shippingCost, AddressBean address, OrderState state, String tracking, String userName) {
        this.id = id;
        this.idUser = idUser;
        this.orderNr = orderNr;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.shippingCost = shippingCost;
        this.address = address;
        this.state = state;
        this.tracking = tracking;
        this.userName = userName;
        orderItems = new ArrayList<>();
    }

    // Metodi di accesso
    public int getId() {
        return id;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getOrderNr() {
        return orderNr;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public AddressBean getAddress() {
        return address;
    }

    public OrderState getState() {
        return state;
    }

    public String getTracking() {
        return tracking;
    }

    public String getUserName() {
        return userName;
    }

    public List<OrderItemBean> getOrderItems() {
        return orderItems;
    }

    // Metodi modificatori
    public void setId(int id) {
        this.id = id;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setOrderNr(String orderNr) {
        this.orderNr = orderNr;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public void addItem(OrderItemBean o) {
        orderItems.add(o);
    }

    // Attributi
    private int id;
    private int idUser;
    private String orderNr;
    private String orderDate;
    private double totalPrice;
    private double shippingCost;
    private AddressBean address;
    private OrderState state;
    private String tracking;
    private String userName;
    private final List<OrderItemBean> orderItems;
}
