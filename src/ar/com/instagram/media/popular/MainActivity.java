package ar.com.instagram.media.popular;

import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
 
public class MainActivity extends Activity implements InstagramAuthListener, Listener<JSONObject>, ErrorListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private String URL_FEED = "http://api.androidhive.info/feed/feed.json";
 
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        Instagram mInstagram  = new Instagram(this, InstagramKey.CLIENT_ID.val(), InstagramKey.CLIENT_SECRET.val(), InstagramKey.REDIRECT_URI.val());
		final InstagramSession mInstagramSession	= mInstagram.getSession();
		if (mInstagramSession.isActive()) {
			this.onSuccess(mInstagramSession.getUser());
		}else{
			mInstagram.authorize(this);
		}
    }

	@Override
	public void onSuccess(InstagramUser user) {
		Toast.makeText(getApplicationContext(), user.username, Toast.LENGTH_LONG).show();
		InstagramRequestPopular media = new InstagramRequestPopular();
		media.doMediaPopularRequest(user,this,this);
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
	}

	@Override
	public void onErrorResponse(VolleyError arg0) {
		Toast.makeText(getApplicationContext(), "Erorr descarga de media", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onResponse(JSONObject data) {
		System.out.println("Hola");
		
	}
}
