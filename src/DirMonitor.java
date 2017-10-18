import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Iterator;


public class DirMonitor{
	private Path myPath;
	
	public DirMonitor(Path myPath) throws IOException{
		if (!(Files.isReadable(myPath)) || !(Files.isDirectory(myPath))){
			throw new IOException();
		}
		this.myPath = myPath;
	}
	
	public void displayFiles() throws IOException{
		
		Iterator<Path> it = Files.newDirectoryStream(myPath).iterator();
		
		while (it.hasNext()){
			System.out.println(it.next());
			
		}
	}
	
	public long sizeOfFiles() throws IOException{
		
		long size=0;		
		
		Iterator<Path> it = Files.newDirectoryStream(myPath).iterator();
		
		while (it.hasNext()){
			Path path =it.next();
			
			if(!(Files.isDirectory(path)))
			size += Files.size(path);
			 
		}
		
		// renvoie des octets
		return size;
		 
	}
	
	
	public Path mostRecent() throws IOException{
		FileTime mr= FileTime.fromMillis(0) ; //most recent
		Path path = null;
		 
		for ( Path allFiles : Files.newDirectoryStream(myPath)){
			if(mr.compareTo(Files.getLastModifiedTime(allFiles)) < 0){
				path = allFiles;
				mr = Files.getLastModifiedTime(allFiles);
			}
		}
		
		return path;	
	}
	
	/*
	class SizeFilter implements DirectoryStream.Filter<Path>
{
		

		@Override
		public boolean accept(Path arg0) throws IOException {
			// TODO Auto-generated method stub
			return false;
		}	
		
	}
	
	*/
	public static void main(String[] args) throws IOException {
		DirMonitor dm = new DirMonitor(Paths.get("."));
		dm.displayFiles();
		System.out.println(dm.sizeOfFiles());
		System.out.println(dm.mostRecent());
	}
	
	
	
	
}
