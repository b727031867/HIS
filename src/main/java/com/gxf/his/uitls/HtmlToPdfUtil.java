package com.gxf.his.uitls;

import com.itextpdf.text.pdf.BaseFont;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.HtmlUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/3 14:25
 */
@Slf4j
public class HtmlToPdfUtil {
    private HtmlToPdfUtil() {
    }

    private volatile static Configuration configuration;

    static {
        if (configuration == null) {
            synchronized (HtmlToPdfUtil.class) {
                if (configuration == null) {
                    configuration = new Configuration(Configuration.VERSION_2_3_29);
                }
            }
        }
    }

    /**
     * 使用 iText 生成 PDF 文档
     *
     * @param htmlTmpStr html 模板文件字符串
     */
    public static byte[] createPdf(String htmlTmpStr) {
        ByteArrayOutputStream outputStream;
        byte[] result = null;
        try {
            outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(HtmlUtils.htmlUnescape(htmlTmpStr));
            ITextFontResolver fontResolver = renderer.getFontResolver();
            // 中文字体支持
            fontResolver.addFont("font/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.layout();
            renderer.createPDF(outputStream);
            result = outputStream.toByteArray();
            outputStream.flush();
            outputStream.close();
        } catch (IOException | com.lowagie.text.DocumentException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * PDF 文件导出
     *
     * @param html 处方单的html内容
     * @return pdf文件
     */
    public static ResponseEntity export(String html) {
        byte[] pdfBytes = createPdf(html);
        HttpHeaders headers = new HttpHeaders();
        if (pdfBytes != null && pdfBytes.length > 0) {
            String fileName = UUID.randomUUID() + ".pdf";
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            ArrayList<String> objects = new ArrayList<>();
            objects.add("Content-Disposition");
            headers.setAccessControlExposeHeaders(objects);
            return new ResponseEntity<byte[]>(pdfBytes, headers, HttpStatus.OK);
        }
        log.info("pdfBytes == null !");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String>("{ \"code\" : \"404\", \"message\" : \"not found\" }",
                headers, HttpStatus.NOT_FOUND);
    }
}
