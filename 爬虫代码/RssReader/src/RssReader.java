import java.util.List;

/**
 * メインクラス
 * 
 */
public class RssReader {

	// コード：チェック成功
	private final static String SUCCESS = "success";
	
	// コード：読み込みモードRSS
	private final static String READ_MODE_RSS = "2";
	
	// コード：読み込みモードTXT
	private final static String READ_MODE_TXT = "1";
	
	/**
	 * メインメソッド
	 * @String[0]:読み込む命令-i 必須
	 * @String[1]:読み込む源　必須
	 * @String[2]:変換命令-c　必須
	 * @String[3]:具体的な処理cutまたはconvert　必須
	 * @String[4]:出力命令-o　
	 * @String[5]:出力先xxx.txt
	 */
	public static void main(String[] args) {

		try{
			/**パラメータチェック**/
			String checkResult = CheckUtil.checkParam(args);
			
			if(!SUCCESS.equals(checkResult)){
				System.out.println("メッセージ:" + checkResult);
				return;
			}
			
			/**入力区分判断し、データを読み込む**/
			List<ReadBean> readBeanList = null;
		    if(READ_MODE_RSS.equals(CheckUtil.getReadKbn())){		
				 readBeanList = ReadUtil.readRss(args[1]);
			}else if (READ_MODE_TXT.equals(CheckUtil.getReadKbn())) {
				 readBeanList = ReadUtil.readTxt(args[1]);	
			}
		    
		    /**カット処理**/
		    if(CheckUtil.getCutKbn()==true){
		    	ConverUtil.doCut(readBeanList);
		    }
		    
		    /**変換処理**/
		    if(CheckUtil.getConvertKbn()==true){
		    	ConverUtil.doConver(readBeanList);
		    }
		    
		    /**出力処理**/
		    if(CheckUtil.getOutPutKbn()==true){ // 指定txtに出力する
		    	
		    	OutPutUtil.outPutFile(readBeanList,args[5]);
		    	System.out.println(args[5] + "ファイルが正常に生成しました");
		    	
		    }else{ // コンソールに出力する
		    	
		    	OutPutUtil.outPutConsole(readBeanList);
		    	
		    }
		}catch (Exception ex){
			
			System.out.println("エラーメッセージ:" + ex.getMessage());		
			
		}
		
	}
	


}
