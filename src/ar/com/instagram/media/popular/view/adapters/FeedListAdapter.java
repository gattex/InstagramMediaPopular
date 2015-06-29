package ar.com.instagram.media.popular.view.adapters;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import ar.com.instagram.media.popular.R;
import ar.com.instagram.media.popular.model.FeedItem;
import ar.com.instagram.media.popular.network.AppController;
import ar.com.instagram.media.popular.view.widgets.FeedImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class FeedListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<FeedItem> feedItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public FeedListAdapter(Activity activity, List<FeedItem> feedItems) {
		this.activity = activity;
		this.feedItems = feedItems;
	}

	@Override
	public int getCount() {
		return feedItems.size();
	}

	@Override
	public Object getItem(int location) {
		return feedItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.feed_item, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();

		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView timestamp = (TextView) convertView
				.findViewById(R.id.timestamp);
		TextView likes = (TextView) convertView
				.findViewById(R.id.likesCount);
		NetworkImageView profilePic = (NetworkImageView) convertView
				.findViewById(R.id.profilePic);
		FeedImageView feedImageView = (FeedImageView) convertView
				.findViewById(R.id.feedImage1);
		final VideoView feedVideoView = (VideoView) convertView
				.findViewById(R.id.feedVideo1);

		FeedItem item = feedItems.get(position);

		name.setText(item.getName());

		Calendar timeZone = Calendar.getInstance();
		timeZone.setTimeInMillis(Long.valueOf(item.getTimeStamp()));
		SimpleDateFormat format = new SimpleDateFormat("EEEE MMM dd HH:mm");
		timestamp.setText(format.format(timeZone.getTime()));
		
		
		DecimalFormat formatter = new DecimalFormat("#,###,###");
		likes.setText(formatter.format(Long.valueOf(item.getCountLikes())) + " likes");
		likes.setVisibility(View.VISIBLE);
		
		
		// user profile pic
		profilePic.setImageUrl(item.getProfilePic(), imageLoader);

		// Feed image
		if ("image".equalsIgnoreCase(item.getType())) {
			feedImageView.setImageUrl(item.getMediaUrl(), imageLoader);
			feedImageView.setVisibility(View.VISIBLE);
			feedVideoView.setVisibility(View.GONE);
		} else { // Video		
			final MediaController mediacontroller = new MediaController(inflater.getContext());
			mediacontroller.setAnchorView(feedVideoView);
			Uri video = Uri.parse(item.getMediaUrl());
			feedVideoView.setMediaController(null);
			feedVideoView.setVisibility(View.VISIBLE);
			feedVideoView.setVideoURI(video);
			feedImageView.setVisibility(View.GONE);
			feedVideoView.setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					feedVideoView.seekTo(10000);
				}
			});
			feedVideoView.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (feedVideoView.isPlaying()){
						feedVideoView.pause();
					}else{
						feedVideoView.start();
					}
					return false;
				}
			});

		}

		return convertView;
	}
}
