import java.util.List;

/**
 * 変換処理クラス
 * 
 */
public class ConverUtil {
	
	private final static int CUT_HEAD_NUM = 10;
	
	private final static int CUT_BODY_NUM = 30;
	
	private final static String RE_CHAR = "ユーザベース";
	
	private final static String RE_DES = "UZABASE";
	
	public  static void  doCut(List<ReadBean> readBeanList){
		for(int i=0;i<readBeanList.size();i++){
			ReadBean readBean = readBeanList.get(i);
			readBean.setTitle(readBean.getTitle().substring(0,CUT_HEAD_NUM));
			readBean.setBody(readBean.getBody().substring(0,CUT_BODY_NUM));
		}
	}
	
	public  static void  doConver(List<ReadBean> readBeanList){
		for(int i=0;i<readBeanList.size();i++){
			ReadBean readBean = readBeanList.get(i);
			readBean.setTitle(readBean.getTitle().replaceAll(RE_CHAR, RE_DES));
			readBean.setBody(readBean.getBody().replaceAll(RE_CHAR, RE_DES));
		}
	}
	
}
