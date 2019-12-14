package SpringCA.Service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileSystemStorageService {

	public static final String UPLOAD_LOCATION = "img\\";
	private Path uploadLocation;


//	@PostConstruct
//	public void init() {
//		this.uploadLocation = Paths.get(UPLOAD_LOCATION + folder);
//		try {
//			Files.createDirectories(uploadLocation);
//		} catch (IOException e) {
//			throw new RuntimeException("Could not initialize storage", e);
//		}
//	}
	
	public void store(MultipartFile file, String username) throws IOException {
		this.uploadLocation = Paths.get(username);
		try {
			Files.createDirectories(uploadLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage", e);
		}
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		FileUtils.cleanDirectory(uploadLocation.toFile());
		try {
			
			// This is a security check
			if (filename.contains("..")) {
				throw new RuntimeException("Cannot store file with relative path outside current directory " + filename);
			}
			
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.uploadLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to store file " + filename, e);
		}
	}

	public Resource loadAsResource(String filename, String username) {
		try {
			Path file = Paths.get(username).resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read file: " + filename);
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Could not read file: " + filename, e);
		}
	}

	public List<Path> listSourceFiles(Path dir) throws IOException {
		List<Path> result = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{png}")) {
			for (Path entry : stream) {
				result.add(entry);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
}

