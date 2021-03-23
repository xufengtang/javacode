
/**
 * パラメータチェッククラス
 * 
 */
public class CheckUtil {

	private final static String SUCCESS = "success";
	
	private final static String READ_MODE_RSS = "2";
	
	private final static String READ_MODE_TXT = "1";
	
	private static String readKbn = "";
	
	private static boolean cutKbn = false;
	
	private static boolean convertKbn = false;
	
	private static boolean outPutKbn = false;
	
	public  static String checkParam(String[] param){
		String result = SUCCESS;
		
		if(param.length < 4){
			result = "必須パラメータを入力してください";
			return result;
		}

		for(int i=0;i<param.length;i++){
			
			/**必須パラメータチェック**/
			// 読み込む命令チェック
			if(i==0 && !"-i".equals(param[i])){
				result = "読み込む命令が間違ったので,-iを入力してください";
				break;
			}
			// 読み込む源チェック
			if(i==1){
				
				String[] fileType = param[i].split("\\.");
				if(fileType.length >0){
					if("txt".equals(fileType[fileType.length-1])){
						readKbn = READ_MODE_TXT;
					}else{
						readKbn = READ_MODE_RSS;
					}
				}else{
					readKbn = READ_MODE_RSS;
				}

			}
			
			if(i==2 && !"-c".equals(param[i])){
				result = "変換命令が間違ったので,-Cを入力してください";
				break;
			}
			
			if(i==3){
				
				String[] commandCode = param[i].split(",");
				
				if(commandCode.length <1){
					result = "処理命令が間違ったので,cut,convertを入力してください";
				}
				
				for(int j=0;j<commandCode.length;j++){
					if("cut".equals(commandCode[j])){
						cutKbn = true;
					}else if("convert".equals(commandCode[j])){
						convertKbn = true;
					}else{
						result = "変換命令が間違ったので,cut,convertを入力してください";
						break;
					}
				}
				

			}
			
			/**出力パラメータチェック**/
			if(i==4){
				if( !"-o".equals(param[i])){
					result = "出力命令が間違ったので,-oを入力してください";
					break;
				}else{
					if(param.length < 6){
						result = "出力命令が出したので,出力先ファイル名も入力してください";
						break;
					}else{
						outPutKbn = true;
					}
				}

			}
		}
		
		return result;
	}

	public static String getReadKbn() {
		return readKbn;
	}



	public static boolean getCutKbn() {
		return cutKbn;
	}



	public static boolean getConvertKbn() {
		return convertKbn;
	}



	public static boolean getOutPutKbn() {
		return outPutKbn;
	}


	
}
