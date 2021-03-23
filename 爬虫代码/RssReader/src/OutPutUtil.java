import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * 出力処理クラス
 * 
 */
public class OutPutUtil {

	
	public  static void  outPutFile(List<ReadBean> readBeanList,String fileName) throws Exception{
	
		File outFile = new File(fileName);
		outFile.createNewFile();
		BufferedWriter out = new BufferedWriter(new FileWriter(outFile));
		
		for(int i=0;i<readBeanList.size();i++){
			ReadBean readBean = readBeanList.get(i);
			out.write("title:" + readBean.getTitle() + "\n");
			out.write("body:" + readBean.getBody() + "\n");
			out.write( "\n");
		}
		
		 out.flush(); 

         out.close(); 
	}
	
	
	public  static void  outPutConsole(List<ReadBean> readBeanList){
		
		for(int i=0;i<readBeanList.size();i++){
			
			ReadBean readBean = readBeanList.get(i);
			
			System.out.println("title:" + readBean.getTitle());
			
			System.out.println("body:" + readBean.getBody());
			
			System.out.print("\n");
		}
	}
	
	
}
