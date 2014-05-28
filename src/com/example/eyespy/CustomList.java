package com.example.eyespy;


import java.util.List;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomList extends ArrayAdapter<Game> {
	private final Activity context;
	private final List<Game> games;
	
	static class gameViewHolder {
		public TextView txtGameType; 
		public TextView txtHint;
		public ImageView imageView; 
	}
	
	public CustomList(Activity context, List<Game> games) {
		super(context, R.layout.list_game_single, games);
		this.context = context;
		this.games = games;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		gameViewHolder holder;
		if(view == null){
		    LayoutInflater inflater = context.getLayoutInflater();
		    view= inflater.inflate(R.layout.list_game_single, parent, false);//null, true);
		
		    holder = new gameViewHolder();
			holder.txtGameType = (TextView)view.findViewById(R.id.txt1);
			holder.txtHint = (TextView)view.findViewById(R.id.txt2);
			holder.imageView = (ImageView) view.findViewById(R.id.img1);
			view.setTag(holder);
		}
		else {
			holder = (gameViewHolder) view.getTag();
		}
		
		holder.txtGameType.setText(games.get(position).getGameType());
		holder.txtHint.setText(games.get(position).getHint());
		
		if(games.get(position).getGameType().equals("I SPY")){
	
			holder.imageView.setImageBitmap(BitmapUtility.decodeSampledBitmapFromFile(games.get(position).getPicFile(), 50, 50));
		}
		else if(games.get(position).getGameType().equals("ASS OR ELBOW")){
	
			holder.imageView.setImageBitmap(BitmapUtility.decodeSampledBitmapFromFile(games.get(position).getCropFile(), 50, 50));
		}
		return view;
	}

}