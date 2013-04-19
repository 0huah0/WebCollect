package webCollect;

import java.sql.SQLException;

public class Jdbc4Text extends BaseJdbc {

	protected static TextContextPOJO saveTextContext(TextContextPOJO text) {
//		text.setContext("asd");
//		text.setFromUrl("asda");
//		text.setTitleUrl("323");
		String insertsql = "insert into TextContext (" +
				"fromUrl,title,titleUrl,context,imgLocUrl,imgUrl,status,type,gettedDt) values('"
				+text.getFromUrl()+"','"+text.getTitle()+"','"+text.getTitleUrl()+"','"+text.getContext()
				+"','"+text.getImgLocUrl()+"','"+text.getImgUrl()+"',1,1,"+text.getGettedDt()+");";
		System.out.println(insertsql);
//		try {
//			getConnectionByJDBC().createStatement().execute(insertsql, new String[]{});
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		return text;

	}
}
