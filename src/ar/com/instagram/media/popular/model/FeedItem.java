package ar.com.instagram.media.popular.model;

public class FeedItem {
    private int id;
    private String name, status, mediaUrl, profilePic, timeStamp, url,type, countLikes;
 
    public String getType() {
		return type;
	}
    
    public void setType(String type) {
		this.type = type;
	}
    
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
    
    public String getMediaUrl() {
		return mediaUrl;
	}
    
    public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
 
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
 
    public String getProfilePic() {
        return profilePic;
    }
 
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
 
    public String getTimeStamp() {
        return timeStamp;
    }
 
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
 
    public String getUrl() {
        return url;
    }
 
    public void setUrl(String url) {
        this.url = url;
    }
    
    public void setCountLikes(String countLikes) {
		this.countLikes = countLikes;
	}
    
    public String getCountLikes() {
		return countLikes;
	}
}
