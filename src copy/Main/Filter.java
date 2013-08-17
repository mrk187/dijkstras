package Main;
import java.io.File;
import javax.swing.filechooser.FileFilter;


public class Filter extends FileFilter{
final static String jpg = "jpg";
final static String jpeg = "jpeg";
final static String gif = "gif";
int dotIndex;
		
		public boolean accept(File f){
				if(f.isDirectory()){
					return true;
				}
				String s = f.getName();
				dotIndex = s.lastIndexOf('.');
				
				if(dotIndex>0 && dotIndex<s.length()-1){
					String extension = s.substring(dotIndex+1).toLowerCase();
					if(jpg.equals(extension) ||jpeg.equals(extension) || gif.equals(extension)){
						return true;
					}else{
						return false;
					}
				}
				return false;
		}
			
		@Override
		public String getDescription() {
			return ".GIF and .JPG only";
		}
		public String extension(File f){
			String fileName = f.getName();
			if(dotIndex > 0 && dotIndex < fileName.length()-1);
			return fileName.substring(dotIndex);
		
		}
		
	}
