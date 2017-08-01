package com.fueled.swipeablerecyclerview.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fueled.swipeablerecyclerview.R;
import com.fueled.swipeablerecyclerview.databinding.ItemMainBinding;

import java.util.List;

/**
 * @author chetansachdeva on 04/06/17
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.VH> {

	private List<String> list;

	public RVAdapter(List<String> list) {
		this.list = list;
	}

	@Override public VH onCreateViewHolder(ViewGroup parent, int viewType) {
		return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
	}

	@Override public void onBindViewHolder(VH holder, int position) {
		holder.binding.tv.setText(list.get(position));
	}

	@Override public int getItemCount() {
		return list.size();
	}

	public void remove(int position) {
		list.remove(position);
		notifyItemRemoved(position);
	}

	public void add(int position, String string) {
		list.add(position, string);
		notifyItemInserted(position);
	}

	static class VH extends RecyclerView.ViewHolder {
		ItemMainBinding binding;

		public VH(View itemView) {
			super(itemView);
			binding = DataBindingUtil.bind(itemView);
		}
	}
}
