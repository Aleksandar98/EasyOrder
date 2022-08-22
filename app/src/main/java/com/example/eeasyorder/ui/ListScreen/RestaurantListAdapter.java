package com.example.eeasyorder.ui.ListScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eeasyorder.R;
import com.example.eeasyorder.domain.model.Restaurant;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {

    private List<Restaurant> restaurantList;
    private ItemClickListener listener;

    public RestaurantListAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView title = holder.itemView.findViewById(R.id.title);
        TextView distance = holder.itemView.findViewById(R.id.distance);
        TextView address = holder.itemView.findViewById(R.id.address);
        TextView workingTime = holder.itemView.findViewById(R.id.workingTime);
        CardView curbsideCard = holder.itemView.findViewById(R.id.curbsideCard);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onItemClick(view,position,restaurantList.get(position));
            }
        });

        if(!restaurantList.get(position).isOpen){
            title.setTextColor(holder.itemView.getResources().getColor(R.color.black222));
            distance.setTextColor(holder.itemView.getResources().getColor(R.color.black222));
        }

        if(restaurantList.get(position).address.isEmpty())
            address.setVisibility(View.GONE);

        if(restaurantList.get(position).serving_times.isEmpty())
            workingTime.setVisibility(View.GONE);

        title.setText(restaurantList.get(position).name);
        distance.setText(restaurantList.get(position).distance);
        address.setText(restaurantList.get(position).address);
        workingTime.setText(restaurantList.get(position).serving_times);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public void updateList(List<Restaurant> list) {
        this.restaurantList = list;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.listener = itemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position, Restaurant restaurant);
    }
}
