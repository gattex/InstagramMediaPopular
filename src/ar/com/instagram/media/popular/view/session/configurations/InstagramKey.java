package ar.com.instagram.media.popular.view.session.configurations;

public enum InstagramKey {
	CLIENT_ID("c73bfc056a834cd4ae137802d77c0807"),
    CLIENT_SECRET("7dcded00f67c4d7b8aa7d5384359f874"),
    REDIRECT_URI("http://www.damian.gattesco.com");
	
	private String value;
	
	private InstagramKey(String v){
		this.value = v;
	}
	
	public String val() {
		return value;
	}

}
