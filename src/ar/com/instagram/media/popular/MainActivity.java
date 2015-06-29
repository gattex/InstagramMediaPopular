package ar.com.instagram.media.popular;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.ListView;
import android.widget.Toast;
import ar.com.instagram.media.popular.model.FeedItem;
import ar.com.instagram.media.popular.view.adapters.FeedListAdapter;
import ar.com.instagram.media.popular.view.session.Instagram;
import ar.com.instagram.media.popular.view.session.Instagram.InstagramAuthListener;
import ar.com.instagram.media.popular.view.session.InstagramRequestPopular;
import ar.com.instagram.media.popular.view.session.InstagramSession;
import ar.com.instagram.media.popular.view.session.InstagramUser;
import ar.com.instagram.media.popular.view.session.configurations.InstagramKey;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
 
public class MainActivity extends Activity implements InstagramAuthListener, Listener<JSONObject>, ErrorListener, OnRefreshListener {
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private SwipeRefreshLayout swipeRefresh;
    private InstagramSession mInstagramSession;
 
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        Instagram mInstagram  = new Instagram(this, InstagramKey.CLIENT_ID.val(), InstagramKey.CLIENT_SECRET.val(), InstagramKey.REDIRECT_URI.val());
		mInstagramSession	= mInstagram.getSession();
		if (mInstagramSession.isActive()) {
			this.onSuccess(mInstagramSession.getUser());
		}else{
			mInstagram.authorize(this);
		}
    }

	@Override
	public void onSuccess(InstagramUser user) {
		callRequestMediaPopular(user);
	}

	@Override
	public void onError(String error) {
		Toast.makeText(getApplicationContext(), "Error en el logueo a instagram", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onCancel() {
		Toast.makeText(getApplicationContext(), "Cancelo el logueo a instagram", Toast.LENGTH_LONG).show();
	}
	
	@SuppressLint("NewApi")
	private void initView(){
		setContentView(R.layout.activity_main);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		this.listView = (ListView)findViewById(R.id.listFeed);
		this.feedItems = new ArrayList<FeedItem>();
		this.listAdapter = new FeedListAdapter(this, feedItems);
		this.listView.setAdapter(listAdapter);
		this.swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
		this.swipeRefresh.setOnRefreshListener(this);
	}

	@Override
	public void onErrorResponse(VolleyError arg0) {
		Toast.makeText(getApplicationContext(), "Error descarga de media", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onResponse(JSONObject data) {
		try {
			JSONArray items = data.getJSONArray("data");
			this.feedItems.clear();
			for (int i = 0; i < items.length(); i++) {
				this.feedItems.add(buildFeed(items.getJSONObject(i)));
			}
			this.listAdapter.notifyDataSetChanged();
			this.swipeRefresh.setRefreshing(false);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
	private FeedItem buildFeed(JSONObject data) throws JSONException{
		FeedItem item = new  FeedItem();
		String type = data.getString("type");
		item.setType(type);
		if ("image".equalsIgnoreCase(type)){
			item.setMediaUrl(data.getJSONObject("images").getJSONObject("low_resolution").getString("url"));
		}else{
			item.setMediaUrl(data.getJSONObject("videos").getJSONObject("low_bandwidth").getString("url"));
		}
		
		item.setProfilePic(data.getJSONObject("user").getString("profile_picture"));
		item.setName(data.getJSONObject("user").getString("username"));
		item.setStatus("no status");
		item.setTimeStamp(data.getString("created_time"));
		item.setUrl(data.getString("link"));
		item.setCountLikes(data.getJSONObject("likes").getString("count"));
		
		return item;
	}

	@Override
	public void onRefresh() {
		callRequestMediaPopular(mInstagramSession.getUser());
	}
	
	private void callRequestMediaPopular(InstagramUser user){
		InstagramRequestPopular media = new InstagramRequestPopular();
		media.doMediaPopularRequest(user,this,this);
	}
}
