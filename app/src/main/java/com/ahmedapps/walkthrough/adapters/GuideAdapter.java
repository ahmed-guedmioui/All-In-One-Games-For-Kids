package com.ahmedapps.walkthrough.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedapps.walkthrough.R;
import com.ahmedapps.walkthrough.items.ItemInfo;
import com.ahmedapps.walkthrough.utils.Config;
import com.ahmedapps.walkthrough.utils.Shared;
import com.ahmedapps.walkthrough.utils.ViewsAndDialogs;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class GuideAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final Activity activity;
    private final LayoutInflater inflater;
    private ItemClickListener clickListener;

    private final ViewsAndDialogs viewsAndDialogs;
    public GuideAdapter(Context context, Activity activity) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.activity = activity;

        viewsAndDialogs = new ViewsAndDialogs(context, activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_info, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ItemInfo item = Config.DATA.infos.get(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        itemViewHolder.itemTitle.setText(item.getTitle());
        Glide.with(context).load(item.getImage()).thumbnail(0.25f).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                itemViewHolder.animationView.setVisibility(View.GONE);
                return false;
            }
        }).into(itemViewHolder.itemImage);

        int likes = Shared.getInt(context, item.getId() + "_likes", item.getLikes());
        itemViewHolder.likes.setText("" + likes + " " + activity.getString(R.string.likes));

        boolean isLiked = Shared.getBoolean(context, item.getId() + "_liked", false);

        if (isLiked) {
            setLikeItem(itemViewHolder);
        }

        if (position == Config.DATA.infos.size() - 1) {

            int bottomMargin = (int) activity.getResources().getDimension(R.dimen.last_item_in_list_bottom_margin);

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemViewHolder.parent.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, bottomMargin);
            itemViewHolder.parent.setLayoutParams(layoutParams);
        } else if (position == 0) {

            int topMargin = (int) activity.getResources().getDimension(R.dimen.first_item_in_list_top_margin);

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemViewHolder.parent.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, topMargin, layoutParams.rightMargin, 0);
            itemViewHolder.parent.setLayoutParams(layoutParams);
        } else {

            // Reset margins for views that are not the last item
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemViewHolder.parent.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, 0);
            itemViewHolder.parent.setLayoutParams(layoutParams);
        }

        reactionItem(itemViewHolder, item);

    }

    private void reactionItem(ItemViewHolder itemViewHolder, ItemInfo item) {

        itemViewHolder.likeButton.setOnClickListener(v -> {

            // dislike action

            item.setLikes(item.getLikes() - 1);
            itemViewHolder.likes.setText("" + item.getLikes() + " " + activity.getString(R.string.likes));
            Shared.setInt(context, item.getId() + "_likes", item.getLikes());
            Shared.setBoolean(context, item.getId() + "_liked", false);

            itemViewHolder.likeButton.setSelected(false);
            itemViewHolder.dislikeButton.setSelected(true);

            itemViewHolder.dislikeButton.setVisibility(View.VISIBLE);
            itemViewHolder.likeButton.setVisibility(View.GONE);

            Animation anim = AnimationUtils.loadAnimation(context, R.anim.like_and_dislike_anime);
            itemViewHolder.dislikeButton.startAnimation(anim);
        });

        itemViewHolder.dislikeButton.setOnClickListener(v -> {

            // like action

            item.setLikes(item.getLikes() + 1);
            itemViewHolder.likes.setText("" + item.getLikes() + " " + activity.getString(R.string.likes));
            Shared.setInt(context, item.getId() + "_likes", item.getLikes());
            Shared.setBoolean(context, item.getId() + "_liked", true);

            itemViewHolder.likeButton.setSelected(true);
            itemViewHolder.dislikeButton.setSelected(false);

            itemViewHolder.dislikeButton.setVisibility(View.GONE);
            itemViewHolder.likeButton.setVisibility(View.VISIBLE);

            Animation anim = AnimationUtils.loadAnimation(context, R.anim.like_and_dislike_anime);
            itemViewHolder.likeButton.startAnimation(anim);
        });
    }

    private void setLikeItem(ItemViewHolder itemViewHolder) {

        itemViewHolder.likeButton.setSelected(true);
        itemViewHolder.dislikeButton.setSelected(false);

        itemViewHolder.dislikeButton.setVisibility(View.GONE);
        itemViewHolder.likeButton.setVisibility(View.VISIBLE);

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.like_and_dislike_anime);
        itemViewHolder.likeButton.startAnimation(anim);
    }
    @Override
    public int getItemCount() {
        return Config.DATA.infos.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        CardView parent;
        ImageView itemImage;
        TextView itemTitle;
        LottieAnimationView animationView;
        TextView likes;
        ImageView likeButton;
        ImageView dislikeButton;

        ItemViewHolder(View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            itemImage = itemView.findViewById(R.id.mainImage);
            itemTitle = itemView.findViewById(R.id.mainText);
            animationView = itemView.findViewById(R.id.animationView);

            likes = itemView.findViewById(R.id.likes);
            likeButton = itemView.findViewById(R.id.like_button);
            dislikeButton = itemView.findViewById(R.id.dislike_button);

            viewsAndDialogs.clickAnimationBig(parent, view -> {
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
