package views.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.dooyd.R;
import com.bumptech.glide.Glide;
import datamodel.MainItem;
import views.listener.ProductRecyclerListener;

import java.util.List;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.MainViewHolder> {

    private Context context;
    private List<MainItem> mainItemList;
    private ProductRecyclerListener productRecyclerListener;
    private int selectedPosition;


    public ProductRecyclerAdapter(Context context, List<MainItem> itemList, ProductRecyclerListener listener, int selectedPosition) {
        this.context = context;
        this.mainItemList = itemList;
        this.productRecyclerListener = listener;
        this.selectedPosition = selectedPosition;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_main_item, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

        MainItem mainItem = mainItemList.get(holder.getAdapterPosition());

        holder.productCard.setTag(position);
        holder.itemName.setText(mainItem.getItemName());
        holder.itemPrice.append(String.valueOf(mainItem.getItemPrice()));
        holder.itemCutPrice.append(String.valueOf(mainItem.getItemCutPrice()));

        Glide.with(context).load(mainItem.getItemImageUrl()).into(holder.itemImageView);

    }

    @Override
    public int getItemCount() {
        return mainItemList.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView productCard;

        private AppCompatTextView itemName;
        private AppCompatTextView itemPrice;
        private AppCompatTextView itemCutPrice;
        private AppCompatTextView itemAddToCart;

        private AppCompatImageView itemImageView;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.item_name_view);
            itemPrice = itemView.findViewById(R.id.item_price_view);
            itemCutPrice = itemView.findViewById(R.id.item_cut_price_view);
            itemImageView = itemView.findViewById(R.id.item_image_view);
            productCard = itemView.findViewById(R.id.productCard);
            itemAddToCart = itemView.findViewById(R.id.item_add_cart);

            itemCutPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            productCard.setOnClickListener(this);
            itemAddToCart.setOnClickListener(this);

            //[1] -> Design & Engineering
            if (selectedPosition == 1) {
                itemAddToCart.setClickable(false);
                itemAddToCart.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.productCard: {
                    productRecyclerListener.onItemClick((int) v.getTag(), mainItemList.get((int) v.getTag()).getItemId(), v.getId());
                    break;
                }

                case R.id.item_add_cart: {
                    productRecyclerListener.onItemClick(0, "", v.getId());
                    break;
                }
            }
        }
    }
}
