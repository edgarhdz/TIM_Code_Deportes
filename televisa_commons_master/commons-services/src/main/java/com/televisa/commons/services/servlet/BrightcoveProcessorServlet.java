
package com.televisa.commons.services.servlet;

import com.televisa.commons.services.services.BrightcoveProcessorService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Formatter;
import java.util.List;

/**
 * The default Charset (Character Encoding) in the request IO should be provided by itself.
 */
@SlingServlet(
        label = "Brightcove Processor Servlet",
        methods = { "POST", "GET" },
        extensions = { "html" },
        paths = {"/bin/brightcove/process"},
        metatype = false
)
public class BrightcoveProcessorServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(BrightcoveProcessorServlet.class);

    @Reference
    private BrightcoveProcessorService brightcoveProcessor;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        doIt(request, response);
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        doIt(request, response);
    }

    /**
     * Use the parameter contents if it exists or use the content of the POST otherwise.
     *
     * @param request the Sling HTTP Servlet Request
     * @param response the Sling HTTP Servlet Response
     * @throws java.io.IOException if an exception occurs
     */
    private void doIt(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Server Charset {}", Charset.defaultCharset());
            LOG.debug("Request Charset {}", request.getCharacterEncoding());
        }

        Reader reader;

        if (request.getAuthType() == null) {
            sendResponse(response.getWriter(), "401 Unauthorized");
            return;
        }
        request.setCharacterEncoding("utf-8");
        String videoInfoParameter = request.getParameter("video_info");
        String actionParameter = request.getParameter("action");
        if (videoInfoParameter == null || videoInfoParameter.isEmpty()) {
            try {
                String tmpdir = System.getProperty("java.io.tmpdir");

                if (ServletFileUpload.isMultipartContent(request) &&  tmpdir != null && !tmpdir.isEmpty()) {
                    FileItemFactory factory = new DiskFileItemFactory(100 * 1024, new File(tmpdir));
                    ServletFileUpload upload = new ServletFileUpload(factory);

                    Charset charset = Charset.defaultCharset();
                    try {
                        charset = Charset.forName(request.getCharacterEncoding());

                    } catch (UnsupportedCharsetException e) {
                        LOG.error(e.getMessage(), e);
                    } catch (IllegalCharsetNameException e) {
                        LOG.error(e.getMessage(), e);
                    } catch (IllegalArgumentException e) {
                        LOG.error(e.getMessage(), e);
                    }

                    boolean flag = true;
                    @SuppressWarnings("unchecked")
                    List<FileItem> files = upload.parseRequest(request);
                    for (FileItem file : files) {
                        reader = new InputStreamReader(file.getInputStream(), charset);
                        flag = flag && this.brightcoveProcessor.process(reader, actionParameter);
                    }
                    if (flag) {
                        sendResponse(response.getWriter(), "OK");
                        return;
                    }
                }
            } catch (FileUploadException e) {
                LOG.error(e.getMessage(), e);
            }
        } else {
            reader = new StringReader(videoInfoParameter);
            if (this.brightcoveProcessor.process(reader,actionParameter)) {
                sendResponse(response.getWriter(), "OK");
                return;
            }
        }
        sendResponse(response.getWriter(), "ERROR");
    }

    /**
     * A basic JSON response implementation.
     *
     * @param writer the writer to write the response to
     * @param response the response to write
     * @throws java.io.IOException if an exception occurs
     */
    private void sendResponse(Writer writer, String response) throws IOException {
        Formatter formatter = new Formatter();
        try {
            writer.write(formatter.format("{ \"response\" : \"%s\" }\n", response).toString());
            writer.flush();
            writer.close();
        } finally {
            formatter.close();
            writer.close();
        }
    }

}
