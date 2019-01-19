package views.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.dooyd.R;
import com.bumptech.glide.Glide;
import datamodel.MainItem;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    private Context context;

    private List<MainItem> mainItemList;


    public MainRecyclerAdapter(Context context, List<MainItem> itemList) {
        this.context = context;
        this.mainItemList = itemList;
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

        holder.itemName.setText(mainItem.getItemName());
        holder.itemPrice.append(String.valueOf(mainItem.getItemPrice()));
        holder.itemCutPrice.append(String.valueOf(mainItem.getItemCutPrice()));

        Glide.with(context).load(mainItem.getItemImageUrls().get(0).getImageUrl()).into(holder.itemImageView);

    }

    @Override
    public int getItemCount() {
        return mainItemList.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView itemName;
        private AppCompatTextView itemPrice;
        private AppCompatTextView itemCutPrice;

        private AppCompatImageView itemImageView;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.item_name_view);
            itemPrice = itemView.findViewById(R.id.item_price_view);
            itemCutPrice = itemView.findViewById(R.id.item_cut_price_view);
            itemImageView = itemView.findViewById(R.id.item_image_view);

            itemCutPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        }
    }
}
