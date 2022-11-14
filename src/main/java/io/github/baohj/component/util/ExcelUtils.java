package io.github.baohj.component.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel工具类
 *
 * @author: ZHY
 * @date: 2020-08-24 17:31
 * @version:
 **/
@Slf4j
public class ExcelUtils {

    private final static HorizontalCellStyleStrategy HORIZONTAL_CELL_STYLE_STRATEGY;

    static {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 内容字体
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 11);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HORIZONTAL_CELL_STYLE_STRATEGY = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    /**
     * 导出
     * @param data     数据
     * @param fileName 文件名
     * @param t        导出对象
     **/
    public static <T> void export(HttpServletResponse response,List<T> data, String fileName, Class<T> t) {
        try {
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            response.setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
            EasyExcel.write(response.getOutputStream(), t)
                    .registerWriteHandler(HORIZONTAL_CELL_STYLE_STRATEGY)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .sheet(fileName).doWrite(data);
        } catch (Exception e) {
            // 重置response
            response.reset();
            log.error("异常:",e);
            throw new RuntimeException("下载失败",e);
        }
    }


    /**
     * 在指定位置生成excel文件
     * @param fileName 文件路径  D:\\sdfs.xlsx
     * @param clazz    导出数据类型
     * @param list     导出数据
     * @param <T>      可以不加这个也行，但是会有警告，看起来不舒服
     */
    public static <T> void writeToExcel(String fileName, Class<T> clazz, List<T> list) {
        ExcelWriter excelWriter = EasyExcel.write(fileName).build();
        WriteSheet sheet = EasyExcel.writerSheet().head(clazz).build();
        excelWriter.write(list, sheet);
        excelWriter.finish();
    }

    /**
     * 导入excel
     * @param in
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> List<T> importing(InputStream in, Class<T> clz){
        try {
             final List<T> list = new ArrayList<T>();
            EasyExcel.read(in, clz, new ReadListener<T>() {
                @Override
                public void invoke(T t, AnalysisContext analysisContext) {
                   list.add(t);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                }
            }).sheet(0).doRead();
            return list;
        } catch (Exception e) {
                if(in != null){
                    try {
                        in.close();
                    } catch (IOException ioException) {
                        log.error("关闭输入流异常:",e);
                    }
                }
            log.error("异常:",e);
            throw new RuntimeException("上传失败",e);
        }
    }


}