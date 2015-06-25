package ar.com.instagram.media.popular.view.session.configurations;

public enum InstagramURL {
	AUTH_URL("https://instagram.com/oauth/authorize/?"),
	ACCESS_TOKEN_URL("https://api.instagram.com/oauth/access_token"),
	API_BASE_URL("https://api.instagram.com/v1");
	
	private String value;
	
	private InstagramURL(String v){
		this.value = v;
	}
	
	public String val() {
		return value;
	}

}
