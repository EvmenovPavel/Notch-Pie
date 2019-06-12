package com.oddlyspaced.np.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oddlyspaced.np.Interface.NotchStyleTouchListener;
import com.oddlyspaced.np.Interface.OnTouchColorLevel;
import com.oddlyspaced.np.Modal.ColorLevel;
import com.oddlyspaced.np.Modal.NotchItem;
import com.oddlyspaced.np.R;

import java.util.ArrayList;

public class NotchLayoutAdapter extends RecyclerView.Adapter<NotchLayoutAdapter.ViewHolder>{


    // Required items
    // ui context
    private Context context;
    // array list of items for colors
    private ArrayList<NotchItem> list;
    // the onClick listener for the color items/levels
    private NotchStyleTouchListener listener;

    // parametrized constructor
    public NotchLayoutAdapter(Context context, ArrayList<NotchItem> list, NotchStyleTouchListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    // Called when RecyclerView in drawn in UI
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // checking if listener overridden
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_notch_style, parent, false);
        return new ViewHolder(v);
    }

    // Called when data is bound to the items
    // --when the list items are being loaded--
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //
        final NotchItem item = list.get(position);
        holder.notch.setImageBitmap(drawNotch(item.getHeight(), item.getWidth(), item.getSize(), item.getTopRadius(), item.getBottomRadius()));
        holder.touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTouch(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // constructor / defining class for the list items
    class ViewHolder extends RecyclerView.ViewHolder {

        // defining the modifiable items of the item view
        ImageView notch;
        View touch;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            // giving context
            notch = itemView.findViewById(R.id.imgNotch);
            touch = itemView.findViewById(R.id.touchNotchStyle);
        }
    }

    private Bitmap drawNotch(int height, int width, int size, int topRadius, int bottomRadius) {
        int w = width;
        int h = height;
        int ns = size;
        int tr = topRadius;
        int br = bottomRadius;

        float p1 = 0;
        float p2 = (float) (w * 2);
        float p3 = (float) (((w + ns) * 2) + 2);
        float p4 = p3 - (p1 + p1);

        Path path = new Path();
        path.moveTo(p3 - 1.0f, -1.0f + p1);
        float p5 = -p1;
        path.rMoveTo(p5, p5);
        float p6 = h;
        float p7 = ns;
        float p8 = ((tr / 100.0f) * p7);// top radius
        float p9 = 0.0f;
        float p10 = ((br / 100.0f) * p7);
        p4 = -((p4 - ((p7 * 2.0f) + p2)) / 2.0f);
        path.rMoveTo(p4, 0.0F);
        float p11 = -p9;
        p3 = -p7;
        path.rCubicTo(-p8, p9, p10 - p7, p11 + p6, p3, p6);
        path.rLineTo(-p2, 0.0f);
        path.rCubicTo(-p10, p11, p8 - p7, p9 - p6, p3, -p6);

        path.close();

        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        rectF.height();

        Bitmap bitmap = Bitmap.createBitmap(
                //screenWidth,
                (int) Math.abs(rectF.width()),
                (int) Math.abs(rectF.height()) + 120,// , // Height
                Bitmap.Config.ARGB_8888 // Config
        );

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawPath(path, paint);

        return bitmap;
    }

}
