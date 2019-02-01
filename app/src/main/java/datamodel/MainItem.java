package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainItem {

    @SerializedName("id")
    @Expose
    private String itemId;

    @SerializedName("name")
    @Expose
    private String itemName;

    @SerializedName("cost")
    @Expose
    private int itemPrice;

    @SerializedName("cutcost")
    @Expose
    private int itemCutPrice;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    @SerializedName("isactive")
    @Expose
    private int itemActive;

    @SerializedName("imageUrl")
    @Expose
    private String itemImageUrl;

    @SerializedName("createdDate")
    @Expose
    private String createdDate;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemCutPrice() {
        return itemCutPrice;
    }

    public void setItemCutPrice(int itemCutPrice) {
        this.itemCutPrice = itemCutPrice;
    }

    public int getItemActive() {
        return itemActive;
    }

    public void setItemActive(int itemActive) {
        this.itemActive = itemActive;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
