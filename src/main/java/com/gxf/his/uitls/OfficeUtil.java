package com.gxf.his.uitls;

import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.exception.WordToHtmlException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/20 08:55
 * 适用于word 2007
 */
@Slf4j
public class OfficeUtil {


    /**
     * DOCX文档解析 * * @param inputStream * 输入流 * @throws Exception * 异常
     */
    public static String docxToHtml(InputStream inputStream) throws Exception {

        OutputStreamWriter outputStreamWriter = null;

        BufferedReader bf = null;

        Long htmlId = System.currentTimeMillis();

        StringBuilder buffer = new StringBuilder();
        try {
            XWPFDocument document = new XWPFDocument(inputStream);

            XHTMLOptions options = XHTMLOptions.create();

            // 存放图片的文件夹，设置为空是因为对当前类进行了重写
            options.setExtractor(new FileImageExtractor(new File("E:\000000000")));

            // html中图片的路径，
            options.URIResolver(new BasicURIResolver("/static/html/image"));

            // 文件输出到项目文件处
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(htmlPath(htmlId)), StandardCharsets.UTF_8);

            // 获取实例
            XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();

            // 转化为xhtml
            xhtmlConverter.convert(document, outputStreamWriter, options);

            // 从项目处读取html文件
            bf = new BufferedReader(new FileReader(htmlPath(htmlId)));
            String s;
            // 使用readLine方法，一次读一行
            while ((s = bf.readLine()) != null) {
                buffer.append(s.trim());
            }
            // 删除临时文件
            deleteTemporaryHtmlFile(htmlPath(htmlId));
        } catch (IOException e) {
            log.error("解析【docx】文档为html时，出现IO异常", e);
            throw new WordToHtmlException(ServerResponseEnum.WORD_CONVERSION_IO_EXCEPTION);
        } catch (Exception e) {
            log.error("解析【docx】文档为html时，出现一般异常");
            throw new WordToHtmlException(ServerResponseEnum.WORD_CONVERSION_EXCEPTION);
        } finally {
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
            if (null != bf) {
                bf.close();
            }
            // 再次删除临时文件
            deleteTemporaryHtmlFile(htmlPath(htmlId));
        }
        return buffer.toString();
    }


    /**
     * 获取相对路径
     */
    private static String htmlPath(Long htmlId) {

        return "src/main/resources/static/html/" +
                htmlId +
                ".html";
    }

    /**
     * 删除临时文件（相对路径下面）
     */
    private static void deleteTemporaryHtmlFile(String oppositeFilePath) {

        File file = new File(oppositeFilePath);

        if (file.isFile() && file.exists()) {
            if (file.delete()) {
                log.info("缓存的html文件删除成功");
            } else {
                log.info("缓存的html文件删除失败");
            }
        }
    }
}
