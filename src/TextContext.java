import java.util.Date;


public class TextContext {
	private long recId;
	private String fromUrl;//������ץ����
	private String title; //����
	private String titleUrl;//title�����url
	private String context; //������Ϣ���ı�
	private String imgLocUrl;	//������ص�ͼƬ��ftp�е�url
	private String imgUrl;//ͼƬ����ҳ·��
	private int status;
	private int type;
	private Date gettedDt;
	
	
	public long getRecId() {
		return recId;
	}
	public void setRecId(long recId) {
		this.recId = recId;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getFromUrl() {
		return fromUrl;
	}
	public void setFromUrl(String fromUrl) {
		this.fromUrl = fromUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitleUrl() {
		return titleUrl;
	}
	public void setTitleUrl(String titleUrl) {
		this.titleUrl = titleUrl;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getImgLocUrl() {
		return imgLocUrl;
	}
	public void setImgLocUrl(String imgLocUrl) {
		this.imgLocUrl = imgLocUrl;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Date getGettedDt() {
		return gettedDt;
	}
	public void setGettedDt(Date gettedDt) {
		this.gettedDt = gettedDt;
	}
}
