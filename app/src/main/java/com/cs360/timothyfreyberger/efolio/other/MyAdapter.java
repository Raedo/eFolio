package com.cs360.timothyfreyberger.efolio.other;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs360.timothyfreyberger.efolio.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<CreateList> galleryList;
    private Context context;

    public MyAdapter(Context context, ArrayList<CreateList> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(galleryList.get(i).getImage_title());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageResource((galleryList.get(i).getImage_ID()));
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap image = BitmapFactory.decodeResource(context.getResources(), galleryList.get(i).getImage_ID());
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        image, "Title", null);
                Uri imageUri = Uri.parse(path);
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                context.startActivity(Intent.createChooser(share,"Select"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img;
        public ViewHolder(View view) {
            super(view);

            title = (TextView)view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }
}