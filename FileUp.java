package sevlert;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUp extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "pload";
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;// 缓存3MB
	private static final int MAX_FLIE_SIZE = 1024 * 1024 * 40;// 文件最大40MB
	private static final int MAX_REQUST_SIZE = 1024 * 1024 * 50;// 表单数据最大50MB

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		if (!ServletFileUpload.isMultipartContent(request)) {// 判断表单中是否有enctype属性值是multipart/form-data
			PrintWriter writer = response.getWriter();
			writer.print("表单中没有enctype=multipart/form-data");
			writer.flush();
			writer.close();
		}
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();// 文件存储对象
		fileItemFactory.setSizeThreshold(MEMORY_THRESHOLD);
		fileItemFactory.setRepository(new File(System
				.getProperty("java.io.tmpdir")));// 设置临时储存目录
		ServletFileUpload upload = new ServletFileUpload(fileItemFactory);// 文件上传对象
		upload.setFileSizeMax(MAX_FLIE_SIZE); // 设置最大文件上传值
		upload.setSizeMax(MAX_REQUST_SIZE);// 设置最大请求值 (包含文件和表单数据)
		String upLoadPath = getServletContext().getRealPath("")
				+ File.separator + UPLOAD_DIRECTORY;// 获取文件路径
		File uploadPath = new File(upLoadPath);// 创建文件夹对象

		if (!uploadPath.exists()) {// 判断文件夹是否存在
			uploadPath.mkdir();// 创建文件夹
		}
		try {
			List<FileItem> fileitem = upload.parseRequest(request);// 获取表单中的组件
			if (fileitem != null && fileitem.size() > 0) {// 判断表单组件是否有
				for (FileItem item : fileitem) {
					if (!item.isFormField()) {// 判断是否是文件上传表单组件
						String filename = item.getName();// 获取文件name
						String filePath = upLoadPath + File.separator
								+ filename;
						File storeFile = new File(filePath);
						System.out.println(filePath);
						item.write(storeFile);
						request.setAttribute("message", "文件上传成功");
					}
				}
			}
		} catch (Exception e) {
			request.setAttribute("message", "错误信息: " + e.getMessage());
			System.out.println("错误信息: " + e.getMessage());
		}
		request.getRequestDispatcher("message.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
