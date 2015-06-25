package ar.com.instagram.media.popular.view.session;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import ar.com.instagram.media.popular.network.AppController;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class Instagram {
	private Context mContext;

	private InstagramDialog mDialog;
	private InstagramAuthListener mListener;
	private InstagramSession mSession;

	private String mClientId;
	private String mClientSecret;
	private String mRedirectUri;

	public Instagram(Context context, String clientId, String clientSecret,
			String redirectUri) {
		mContext = context;

		mClientId = clientId;
		mClientSecret = clientSecret;
		mRedirectUri = redirectUri;

		String authUrl = Cons.AUTH_URL + "client_id=" + mClientId + "&redirect_uri=" + mRedirectUri + "&response_type=code";

		mSession = new InstagramSession(context);

		mDialog = new InstagramDialog(context, authUrl, redirectUri,
				new InstagramDialog.InstagramDialogListener() {

					@Override
					public void onSuccess(String code) {
						retreiveAccessToken(code);
					}

					@Override
					public void onError(String error) {
						mListener.onError(error);
					}

					@Override
					public void onCancel() {
						mListener.onCancel();

					}
				});
	}

	public void authorize(InstagramAuthListener listener) {
		mListener = listener;

		mDialog.show();
	}

	public void resetSession() {
		mSession.reset();

		mDialog.clearCache();
	}

	public InstagramSession getSession() {
		return mSession;
	}

	private void retreiveAccessToken(final String code) {
		final ProgressDialog progressDlg = new ProgressDialog(mContext);
		progressDlg.setMessage("Getting access token...");
		final InstagramUser user = new InstagramUser();
		JsonObjectRequest jsonReq = new JsonObjectRequest(
				Method.GET, Cons.ACCESS_TOKEN_URL,(String) null, 
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						if (response != null) {
							try{
								JSONObject jsonUser = response.getJSONObject("user");
								user.accessToken = response.getString("access_token");
								user.id = jsonUser.getString("id");
								user.username = jsonUser.getString("username");
								user.fullName = jsonUser.getString("full_name");
								user.profilPicture = jsonUser.getString("profile_picture");
								mSession.store(user);
								mListener.onSuccess(user);
							}catch(Exception e){
								mListener.onError("Failed to get access token");
							}finally{
								progressDlg.dismiss();
							}
						}
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						mListener.onError("Failed to get access token");
					}
				}
		){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String,String> params = new HashMap<String, String>();
				params.put("client_id", mClientId);
				params.put("client_secret",	mClientSecret);
				params.put("grant_type","authorization_code");
				params.put("redirect_uri", mRedirectUri);
				params.put("code", code);
				return params;
			}
			
		};
		AppController.getInstance().addToRequestQueue(jsonReq);
	}
	
	public interface InstagramAuthListener {
		public abstract void onSuccess(InstagramUser user);

		public abstract void onError(String error);

		public abstract void onCancel();
	}
}