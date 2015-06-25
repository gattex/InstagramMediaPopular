package ar.com.instagram.media.popular.view.session;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import ar.com.instagram.media.popular.network.AppController;
import ar.com.instagram.media.popular.view.session.configurations.InstagramURL;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

public class InstagramRequestPopular{
	
	
	public void doMediaPopularRequest(InstagramSession session, Listener<JSONObject> success, ErrorListener error){
		doMediaPopularRequest(session.getAccessToken(),success,error);
	}
	
	public void doMediaPopularRequest(InstagramUser user, Listener<JSONObject> success, ErrorListener error){
		doMediaPopularRequest(user.accessToken,success,error);
	}
	
	public void doMediaPopularRequest(String token , Listener<JSONObject> success, ErrorListener error){
		Map<String,String> params = new HashMap<String,String>();
		params.put("access_token", token);
		InstagramRequest request = new InstagramRequest(Method.GET,InstagramURL.MEDIA_POPULAR.val(), params, success, error);
		AppController.getInstance().addToRequestQueue(request);
	}
}
