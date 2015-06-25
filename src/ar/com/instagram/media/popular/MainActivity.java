package ar.com.instagram.media.popular;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import ar.com.instagram.media.popular.model.FeedItem;
import ar.com.instagram.media.popular.view.adapters.FeedListAdapter;
import ar.com.instagram.media.popular.view.session.Instagram;
import ar.com.instagram.media.popular.view.session.Instagram.InstagramAuthListener;
import ar.com.instagram.media.popular.view.session.InstagramSession;
import ar.com.instagram.media.popular.view.session.InstagramUser;
import ar.com.instagram.media.popular.view.session.configurations.InstagramKey;
 
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private String URL_FEED = "http://api.androidhive.info/feed/feed.json";
 
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        Instagram mInstagram  		= new Instagram(this, InstagramKey.CLIENT_ID.val(), InstagramKey.CLIENT_SECRET.val(), InstagramKey.REDIRECT_URI.val());
		InstagramSession mInstagramSession	= mInstagram.getSession();
		
		if (mInstagramSession.isActive()) {
			
		}else{
			mInstagram.authorize(new InstagramAuthListener() {
				
				public void onSuccess(InstagramUser user) {
					Toast.makeText(getApplicationContext(), "se logueo bien" + user.accessToken + user.fullName, Toast.LENGTH_LONG).show();
				}
				
				public void onError(String error) {
					Toast.makeText(getApplicationContext(), "error " + error, Toast.LENGTH_LONG).show();
				}
				
				public void onCancel() {
					Toast.makeText(getApplicationContext(), "canecelo " , Toast.LENGTH_LONG).show();
					
				}
			});
		}
        
        
        
 
//        listView = (ListView) findViewById(R.id.list);
 
//        feedItems = new ArrayList<FeedItem>();
 
//        listAdapter = new FeedListAdapter(this, feedItems);
//        listView.setAdapter(listAdapter);
        
//        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                 Toast.makeText(MainActivity.this, "se refresco", Toast.LENGTH_SHORT).show();
//                 mSwipeRefreshLayout.setRefreshing(false);
//            }
//        });
         
        // These two lines not needed,
        // just to get the look of facebook (changing background color & hiding the icon)
//        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
//        getActionBar().setIcon(
//                   new ColorDrawable(getResources().getColor(android.R.color.transparent)));
 
        // We first check for cached request
//        Cache cache = AppController.getInstance().getRequestQueue().getCache();
//        Entry entry = cache.get(URL_FEED);
//        if (entry != null) {
            // fetch the data from cache
//            try {
//                String data = new String(entry.data, "UTF-8");
//                try {
//                    parseJsonFeed(new JSONObject(data));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
 
//        } else {
            // making fresh volley request and getting json
//            JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
//                    URL_FEED, (String)null, new Response.Listener<JSONObject>() {
// 
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            VolleyLog.d(TAG, "Response: " + response.toString());
//                            if (response != null) {
//                                parseJsonFeed(response);
//                            }
//                        }
//                    }, new Response.ErrorListener() {
// 
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            VolleyLog.d(TAG, "Error: " + error.getMessage());
//                        }
//                    });
 
            // Adding request to volley request queue
//            AppController.getInstance().addToRequestQueue(jsonReq);
//        }
 
    }
 
    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
//    private void parseJsonFeed(JSONObject response) {
//        try {
//            JSONArray feedArray = response.getJSONArray("feed");
// 
//            for (int i = 0; i < feedArray.length(); i++) {
//                JSONObject feedObj = (JSONObject) feedArray.get(i);
// 
//                FeedItem item = new FeedItem();
//                item.setId(feedObj.getInt("id"));
//                item.setName(feedObj.getString("name"));
// 
//                // Image might be null sometimes
//                String image = feedObj.isNull("image") ? null : feedObj
//                        .getString("image");
//                item.setImge(image);
//                item.setStatus(feedObj.getString("status"));
//                item.setProfilePic(feedObj.getString("profilePic"));
//                item.setTimeStamp(feedObj.getString("timeStamp"));
// 
//                // url might be null sometimes
//                String feedUrl = feedObj.isNull("url") ? null : feedObj
//                        .getString("url");
//                item.setUrl(feedUrl);
// 
//                feedItems.add(item);
//            }
// 
//            // notify data changes to list adapater
//            listAdapter.notifyDataSetChanged();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}
