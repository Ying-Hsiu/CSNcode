import java.io.File;

public class listFile {
	
	String[] listFile(String dirPath){
		//如果文件不以分隔符結尾，自動加入分隔符
		if(!dirPath.endsWith(File.separator)){
			dirPath = dirPath+File.separator;
		}
		File dirFile = new File(dirPath);
		if((!dirFile.exists())||(!dirFile.isDirectory())){
			return null;
		}
		String[] files = dirFile.list();
		return files;
	}
}
