package walking;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import exif.exif.NewName;
import exif.exif.Rename;

public class Walk {

	public static void main(String[] args) {
		Path homeFolder = Paths.get("_/home/autor/Desktop/quai H/soletanche/DCIM");
		FileVisitor<Path> fileVisitor = new FileSizeVisitor(new Long(50));
		try {
			Files.walkFileTree(homeFolder, fileVisitor);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
  
	
	static class FileSizeVisitor implements FileVisitor<Path> {

		private Long size;

		public FileSizeVisitor(Long size) {
			this.size = size;
		}

		/**
		 * This is triggered before visiting a directory.
		 */
		@Override
		public FileVisitResult preVisitDirectory(Path path,
				BasicFileAttributes attrs) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		/**
		 * This is triggered when we visit a file.
		 */
		@Override
		public FileVisitResult visitFile(Path path, BasicFileAttributes attrs)
				throws IOException {
/*
			long fileSize = attrs.size() / 1024;
			if (fileSize >= this.size) {
				System.out.println("File bigger than " + this.size + "KB  found: "
						+ path);
			}*/
			if(path.toString().toLowerCase().endsWith(".jpg")){
			    copier(path);
			}
			

			return FileVisitResult.CONTINUE;
		}

		/**
		 * This is triggered if we cannot visit a Path We log the fact we cannot
		 * visit a specified path .
		 */
		@Override
		public FileVisitResult visitFileFailed(Path path, IOException exc)
				throws IOException {
			// We print the error
			System.err.println("ERROR: Cannot visit path: " + path);
			// We continue the folder walk
			return FileVisitResult.CONTINUE;
		}

		/**
		 * This is triggered after we finish visiting a specified folder.
		 */
		@Override
		public FileVisitResult postVisitDirectory(Path path, IOException exc)
				throws IOException {
			// We continue the folder walk
			return FileVisitResult.CONTINUE;
		}
		
		String base = "/run/media/autor/QUAI_H/SOLETANCHE/2015-02-10";
		
		void copier (Path p){
			String ps = NewName.newName(p.toString());
	    	Path vers = FileSystems.getDefault().getPath(base, ps);
	    	vers.toFile().mkdirs();
	        //overwrite existing file, if exists
	        CopyOption[] options = new CopyOption[]{
	          StandardCopyOption.REPLACE_EXISTING,
	          StandardCopyOption.COPY_ATTRIBUTES
	        }; 
	        try {
				Files.copy(p, vers, options);
			} catch (IOException e) {
				// TODO Bloc catch généré automatiquement
				e.printStackTrace();
			}
	    }

	}

}
