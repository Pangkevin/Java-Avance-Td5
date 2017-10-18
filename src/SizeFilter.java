import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class SizeFilter implements DirectoryStream.Filter<Path> {

	private long sizeFiltre;

	public SizeFilter(int sizeFiltre) {
		this.sizeFiltre = sizeFiltre;
	}

	@Override
	/**
	 * La fonction Accept permet de filtrer les fichiers qui ont plus de N
	 * octets. Les N octets sont spécifiés lors de la construction de la classe
	 * à travers la varibale "sizeFiltre"
	 */
	public boolean accept(Path path) throws IOException {

		Files files = null;
		long nOctetsFile = files.size(path);

		// Si le nombre d'octets est plus grand ou égal qu'un certain N octets
		// alors on return true sinon false
		if (nOctetsFile >= this.sizeFiltre && !(Files.isDirectory(path))) {
			return true;
		}

		return false;

	}

}
