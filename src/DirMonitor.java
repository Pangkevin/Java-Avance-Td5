import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Iterator;

public class DirMonitor {
	private Path myPath;

	public DirMonitor(Path myPath) throws IOException {
		if (!(Files.isReadable(myPath)) || !(Files.isDirectory(myPath))) {
			throw new IOException();
		}
		this.myPath = myPath;
	}

	// displatFiles qui utilise la CLASSE SizeFilter
	/*
	 * public void displayFiles() throws IOException {
	 * 
	 * Iterator<Path> it = Files.newDirectoryStream(myPath).iterator();
	 * SizeFilter sizeFilter= new SizeFilter(70);
	 * 
	 * while (it.hasNext()){
	 * 
	 * if((sizeFilter.accept(it.next()) == true)){
	 * System.out.println(it.next()); } } }
	 */

	// displatFiles qui utilise la CLASSE INTERNE SizeFilter
	/*
	 * public void displayFiles() throws IOException {
	 * 
	 * Iterator<Path> it = Files.newDirectoryStream(myPath).iterator();
	 * SizeFilter1 sizeFilter1 = new DirMonitor.SizeFilter1();
	 * 
	 * while (it.hasNext()){
	 * 
	 * if((sizeFilter1.accept(it.next()) == true)){
	 * System.out.println(it.next()); } } }
	 */

	// displatFiles qui utilise la CLASSE INTERNE SizeFilter
	/*
	 * public void displayFiles() throws IOException {
	 * 
	 * Iterator<Path> it = Files.newDirectoryStream(myPath).iterator();
	 * SizeFilter1 sizeFilter1 = new DirMonitor.SizeFilter1();
	 * 
	 * while (it.hasNext()){
	 * 
	 * if((sizeFilter1.accept(it.next()) == true)){
	 * System.out.println(it.next()); } } }
	 */

	// displatFiles qui utilise la CLASSE ANONYME SizeFilter
	public void displayFiles() throws IOException {

		Iterator<Path> it = Files.newDirectoryStream(myPath).iterator();
		//SizeFilter1 sizeFilter1 = new DirMonitor.SizeFilter1();
		
		DirectoryStream.Filter<Path>  filer =  new DirectoryStream.Filter<Path>(){
			
			@Override	
			public boolean accept(Path path) throws IOException {

				Files files = null;
				long nOctetsFile = files.size(path);

				// Si le nombre d'octets est plus grand ou égal qu'un certain N octets
				// alors on return true sinon false
				if (nOctetsFile >=  10 && !(Files.isDirectory(path))) {
					return true;
				}

				return false;

			}
            
        };
        
		while (it.hasNext()) {

			if ((filer.accept(it.next()) == true)) {
				System.out.println(it.next());
			}
		}
	}

	public long sizeOfFiles() throws IOException {

		long size = 0;

		Iterator<Path> it = Files.newDirectoryStream(myPath).iterator();

		while (it.hasNext()) {
			Path path = it.next();

			if (!(Files.isDirectory(path)))
				size += Files.size(path);

		}

		// renvoie des octets
		return size;

	}

	public Path mostRecent() throws IOException {
		FileTime mr = FileTime.fromMillis(0); // most recent
		Path path = null;

		for (Path allFiles : Files.newDirectoryStream(myPath)) {
			if (mr.compareTo(Files.getLastModifiedTime(allFiles)) < 0) {
				path = allFiles;
				mr = Files.getLastModifiedTime(allFiles);
			}
		}

		return path;
	}

	class SizeFilter1 implements DirectoryStream.Filter<Path> {
		private long sizeFiltre;

		@Override
		public boolean accept(Path path) throws IOException {

			Files files = null;
			long nOctetsFile = files.size(path);

			// Si le nombre d'octets est plus grand ou égal qu'un certain N
			// octets
			// alors on return true sinon false
			if (nOctetsFile >= this.sizeFiltre && !(Files.isDirectory(path))) {
				return true;
			}

			return false;

		}

	}

	public static void main(String[] args) throws IOException {
		DirMonitor dm = new DirMonitor(Paths.get("."));
		System.out.println("Les dossiers:");
		dm.displayFiles();
		System.out.println("Taille en octet du ficher: " + dm.sizeOfFiles());
		System.out.println("La modification la plus récente: " + dm.mostRecent());
	}

}
