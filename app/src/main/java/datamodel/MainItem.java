package datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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

    @SerializedName("tag")
    @Expose
    private String itemTag;

    @SerializedName("isactive")
    @Expose
    private int itemActive;

    @SerializedName("productImage")
    @Expose
    private List<ImageUrl> itemImageUrls;

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

    public String getItemTag() {
        return itemTag;
    }

    public void setItemTag(String itemTag) {
        this.itemTag = itemTag;
    }

    public int getItemActive() {
        return itemActive;
    }

    public void setItemActive(int itemActive) {
        this.itemActive = itemActive;
    }

    public List<ImageUrl> getItemImageUrls() {
        return itemImageUrls;
    }

    public void setItemImageUrls(List<ImageUrl> itemImageUrls) {
        this.itemImageUrls = itemImageUrls;
    }

    public class ImageUrl {

        @SerializedName("fileUrl")
        @Expose
        private String imageUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

}
