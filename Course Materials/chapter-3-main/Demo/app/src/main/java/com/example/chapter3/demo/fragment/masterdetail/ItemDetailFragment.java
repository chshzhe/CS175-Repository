package com.example.chapter3.demo.fragment.masterdetail;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chapter3.demo.R;

public class ItemDetailFragment extends Fragment {
	private Item item;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		item = (Item) getArguments().getSerializable("item");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
		TextView tvTitle = view.findViewById(R.id.tvTitle);
		TextView tvBody = view.findViewById(R.id.tvBody);
		tvTitle.setText(item.getTitle());
		tvBody.setText(item.getBody());
		return view;
	}
    
    public static ItemDetailFragment newInstance(Item item) {
    	ItemDetailFragment fragmentDemo = new ItemDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("item", item);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
}
