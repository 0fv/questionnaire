package space.nyuki.questionnaire.utils;

import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public class FileDownloadUtil {
	@SneakyThrows
	public static ResponseEntity<Resource> responseEntity(HttpServletRequest request, Resource resource) {
		String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity
				.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
