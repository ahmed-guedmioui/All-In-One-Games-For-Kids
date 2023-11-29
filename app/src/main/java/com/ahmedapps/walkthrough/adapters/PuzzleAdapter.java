package com.ahmedapps.walkthrough.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedapps.walkthrough.R;
import com.ahmedapps.walkthrough.utils.ViewsAndDialogs;

import java.io.IOException;
import java.io.InputStream;

public class PuzzleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater inflater;
    private ItemClickListener clickListener;

    private String[] files;
    private final AssetManager am;
    private final Activity activity;

    private ViewsAndDialogs viewsAndDialogs;
    public PuzzleAdapter(Context context, Activity activity) {
        this.inflater = LayoutInflater.from(context);
        this.activity = activity;

        viewsAndDialogs = new ViewsAndDialogs(context, activity);

        am = context.getAssets();
        try {
            files = am.list("img");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_puzzle, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        if (position == files.length - 1) {

            int bottomMargin = (int) activity.getResources().getDimension(R.dimen.last_item_in_list_bottom_margin);
            int topMargin = (int) activity.getResources().getDimension(R.dimen.items_in_list_top_margin);

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemViewHolder.parent.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, topMargin, layoutParams.rightMargin, bottomMargin);
            itemViewHolder.parent.setLayoutParams(layoutParams);
        } else if (position == files.length - 2) {

            int bottomMargin = (int) activity.getResources().getDimension(R.dimen.last_item_in_list_bottom_margin);
            int topMargin = (int) activity.getResources().getDimension(R.dimen.items_in_list_top_margin);

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemViewHolder.parent.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, topMargin, layoutParams.rightMargin, bottomMargin);
            itemViewHolder.parent.setLayoutParams(layoutParams);
        } else if (position == 0) {

            int topMargin = (int) activity.getResources().getDimension(R.dimen.first_item_in_list_top_margin);

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemViewHolder.parent.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, topMargin, layoutParams.rightMargin, 0);
            itemViewHolder.parent.setLayoutParams(layoutParams);
        } else if (position == 1) {

            int topMargin = (int) activity.getResources().getDimension(R.dimen.first_item_in_list_top_margin);

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemViewHolder.parent.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, topMargin, layoutParams.rightMargin, 0);
            itemViewHolder.parent.setLayoutParams(layoutParams);
        } else {

            int topMargin = (int) activity.getResources().getDimension(R.dimen.items_in_list_top_margin);

            // Reset margins for views that are not the last item
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemViewHolder.parent.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, topMargin, layoutParams.rightMargin, 0);
            itemViewHolder.parent.setLayoutParams(layoutParams);
        }

        itemViewHolder.imageView.setImageBitmap(null);
        itemViewHolder.imageView.post(new Runnable() {
            @Override
            public void run() {
                new AsyncTask<Void, Void, Void>() {
                    private Bitmap bitmap;

                    @Override
                    protected Void doInBackground(Void... voids) {
                        bitmap = getPicFromAsset(itemViewHolder.imageView, files[position]);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        itemViewHolder.imageView.setImageBitmap(bitmap);
                    }
                }.execute();
            }
        });

    }

    @Override
    public int getItemCount() {
        return files.length;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout parent;
        final RelativeLayout toAnimate;
        final ImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            toAnimate = itemView.findViewById(R.id.toAnimate);
            imageView = itemView.findViewById(R.id.gridImageview);

            viewsAndDialogs.clickAnimationBig(toAnimate, imageView, view -> {
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


    private Bitmap getPicFromAsset(ImageView imageView, String assetName) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        if (targetW == 0 || targetH == 0) {
            // view has no dimensions set
            return null;
        }

        try {
            InputStream is = am.open("img/" + assetName);
            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, new Rect(-1, -1, -1, -1), bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

            is.reset();

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            return BitmapFactory.decodeStream(is, new Rect(-1, -1, -1, -1), bmOptions);
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

}
