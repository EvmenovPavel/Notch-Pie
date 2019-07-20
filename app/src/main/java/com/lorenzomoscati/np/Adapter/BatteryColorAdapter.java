package com.lorenzomoscati.np.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lorenzomoscati.np.Interface.OnTouchColorLevel;
import com.lorenzomoscati.np.Modal.ColorLevel;
import com.lorenzomoscati.np.R;

import java.util.ArrayList;
import java.util.Locale;

public class BatteryColorAdapter extends RecyclerView.Adapter<BatteryColorAdapter.ViewHolder>{

	// Required items
	// Array list of items for colors
	private final ArrayList<ColorLevel> list;

	// onClick listener for the color items/levels
	private final OnTouchColorLevel listener;

	// Parametrized constructor
	public BatteryColorAdapter(@SuppressWarnings("unused") Context context, ArrayList<ColorLevel> list, OnTouchColorLevel listener) {

		this.list = list;
		this.listener = listener;

	}

	// Called when RecyclerView in drawn in UI
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

		// Checking if listener overridden
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());

		View v = inflater.inflate(R.layout.item_color_list, parent, false);

		return new ViewHolder(v);

	}

	// Called when data is bound to the items
	// --when the list items are being loaded--
	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

		// Gets the color item that needs to be modified
		ColorLevel item = list.get(position);

		// Sets the color preview
		holder.color.setBackgroundColor(Color.parseColor(item.getColor()));
		// Sets the starting percentage
		holder.start.setText(String.format(Locale.getDefault(), "%d%%", item.getStartLevel()));
		// Sets the ending percentage
		holder.end.setText(String.format(Locale.getDefault(), "%d%%", item.getEndLevel()));


		holder.touch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				listener.onTouchItem(position);

			}

		});

	}



	// Method to know how many colors are present in the list
	@Override
	public int getItemCount() {

		return list.size();

	}



	// Constructor / defining class for the list items
	class ViewHolder extends RecyclerView.ViewHolder {

		// Defining the modifiable items of the item view
		final TextView start;
		final TextView end;
		final View color;
		final View touch;

		ViewHolder(@NonNull View itemView) {

			super(itemView);

			// Giving context
			start = itemView.findViewById(R.id.txRecyclerViewStartPercentage);
			end = itemView.findViewById(R.id.txRecyclerViewEndPercentage);
			color = itemView.findViewById(R.id.viewRecyclerViewColor);
			touch = itemView.findViewById(R.id.viewRecyclerViewTouch);

		}

	}

}
