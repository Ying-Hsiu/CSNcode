import java.io.File;

public class listFile {
	
	String[] listFile(String dirPath){
		//�p�G��󤣥H���j�ŵ����A�۰ʥ[�J���j��
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
