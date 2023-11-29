package com.ahmedapps.walkthrough.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ahmedapps.walkthrough.R;
import com.ahmedapps.walkthrough.items.ItemVideo;
import com.ahmedapps.walkthrough.utils.Config;
import com.ahmedapps.walkthrough.utils.ViewsAndDialogs;

public class VideosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater inflater;
    private ItemClickListener clickListener;
    private final Activity activity;
    private final Context context;
    private ViewsAndDialogs viewsAndDialogs;

    public VideosAdapter(Context context, Activity activity) {
        this.inflater = LayoutInflater.from(context);
        this.activity = activity;
        this.context = context;

        viewsAndDialogs = new ViewsAndDialogs(context, activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_video, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        ItemVideo item = Config.DATA.videos.get(position);

        if (position == Config.DATA.videos.size() - 1) {

            int bottomMargin = (int) activity.getResources().getDimension(R.dimen.last_item_in_list_bottom_margin);
//            int topMargin = 15;

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemViewHolder.parent.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, bottomMargin);
            itemViewHolder.parent.setLayoutParams(layoutParams);

        } else if (position == 0) {

            int topMargin = (int) activity.getResources().getDimension(R.dimen.first_item_in_list_top_margin);

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemViewHolder.parent.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, topMargin, layoutParams.rightMargin, 0);
            itemViewHolder.parent.setLayoutParams(layoutParams);
        }  else {

//            int topMargin = 15;

            // Reset margins for views that are not the last item
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemViewHolder.parent.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, 0);
            itemViewHolder.parent.setLayoutParams(layoutParams);
        }

        Glide.with(context).load(item.getImage_cover()).thumbnail(0.25f).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                itemViewHolder.animationView.setVisibility(View.GONE);
                return false;
            }
        }).into(itemViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return Config.DATA.videos.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        CardView parent;
        final LottieAnimationView animationView;
        final ImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            animationView = itemView.findViewById(R.id.animationView);
            imageView = itemView.findViewById(R.id.image);

            viewsAndDialogs.clickAnimationBig(parent, imageView, view -> {
                if (clickListener != null) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });

        }

    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
