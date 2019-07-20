package com.lorenzomoscati.np.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lorenzomoscati.np.Interface.NotchStyleTouchListener;
import com.lorenzomoscati.np.Modal.NotchItem;
import com.lorenzomoscati.np.R;

import java.util.ArrayList;

public class NotchLayoutAdapter extends RecyclerView.Adapter<NotchLayoutAdapter.ViewHolder>{


	// Array list of items for colors
	private final ArrayList<NotchItem> list;
	// onClick listener for the color items/levels
	private final NotchStyleTouchListener listener;

	// Parametrized constructor
	public NotchLayoutAdapter(@SuppressWarnings("unused") Context context, ArrayList<NotchItem> list, NotchStyleTouchListener listener) {

		// Required items
		// UI context
		this.list = list;
		this.listener = listener;

	}

	// Called when RecyclerView in drawn in UI
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

		// Checking if listener overridden
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());

		View v = inflater.inflate(R.layout.item_notch_style, parent, false);

		return new ViewHolder(v);

	}

	// Called when data is bound to the items
	// -- When the list items are being loaded --
	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

		// Gets the index number of the item
		final NotchItem item = list.get(position);

		// Sets the title according to the index number
		holder.title.setText(item.getTitle());

		// Sets the bitmap according to the index number
		holder.notch.setImageBitmap(drawNotch(
				item.getHeight(),
				item.getWidth(),
				item.getSize(),
				item.getTopRadius(),
				item.getBottomRadius()));

		// Sets the listener in case the item is clicked so that the configuration is loaded
		holder.touch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				listener.onTouch(item);

			}

		});

	}

	// Returns [int] the number of presets
	@Override
	public int getItemCount() {

		return list.size();

	}

	// Constructor defining class for the list items
	class ViewHolder extends RecyclerView.ViewHolder {

		// Defining the variable items of the item view
		final ImageView notch;
		final View touch;
		final TextView title;

		ViewHolder(@NonNull View itemView) {

			super(itemView);

			// Giving context
			notch = itemView.findViewById(R.id.imgNotch);
			touch = itemView.findViewById(R.id.touchNotchStyle);
			title = itemView.findViewById(R.id.txNotchStyleTitle);

		}

	}

	// Bitmap method [bitmap] to draw the notch preview in the recycler
	private Bitmap drawNotch(int height, int width, int size, int topRadius, int bottomRadius) {

		// Parameters to draw the notch
		float p1 = 0;
		float p2 = (float) (width * 2);
		float p3 = (float) (((width + size) * 2) + 2);
		float p4 = p3 - (p1 + p1);

		// Creates the notch shape
		Path path = new Path();

		// Sets the notch shape
		path.moveTo(p3 - 1.0f, -1.0f + p1);

		float p5 = -p1;
		path.rMoveTo(p5, p5);

		float p8 = ((topRadius / 100.0f) * (float) size);// top radius
		float p9 = 0.0f;
		float p10 = ((bottomRadius / 100.0f) * (float) size);

		p4 = -((p4 - (((float) size * 2.0f) + p2)) / 2.0f);

		path.rMoveTo(p4, 0.0F);

		float p11 = -p9;

		p3 = -(float) size;

		path.rCubicTo(-p8, p9, p10 - (float) size, p11 + (float) height, p3, (float) height);
		path.rLineTo(-p2, 0.0f);
		path.rCubicTo(-p10, p11, p8 - (float) size, p9 - (float) height, p3, -(float) height);

		path.close();

		RectF rectF = new RectF();

		path.computeBounds(rectF, true);

		rectF.height();

		Bitmap bitmap = Bitmap.createBitmap(

				//screenWidth
				(int) Math.abs(rectF.width()),
				// Height
				(int) Math.abs(rectF.height()) + 120,
				// Config
				Bitmap.Config.ARGB_8888
		);

		// Sets the colors and other visual settings
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// Sets the color to black
		paint.setColor(Color.BLACK);
		// Activates the antiAliasing
		paint.setAntiAlias(true);
		paint.setDither(true);
		// Sets the color to fill the shape
		paint.setStyle(Paint.Style.FILL);

		// Canvas where path and colors are combined
		Canvas canvas = new Canvas(bitmap);
		// Combines path and colors
		canvas.drawPath(path, paint);

		return bitmap;

	}

}
