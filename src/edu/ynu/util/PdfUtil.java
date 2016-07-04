package edu.ynu.util;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PdfUtil {

    public String dest;
    private PdfFont font;

    public PdfUtil() throws IOException {
        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream("/project.properties");
        prop.load(in);
        dest = prop.getProperty("generatePath");
        String fontPath = prop.getProperty("fontPath");
        font = PdfFontFactory.createFont(fontPath,PdfEncodings.IDENTITY_H,true);
    }

    public static void main(final String[] args) throws IOException{
        PdfUtil pdfUtil = new PdfUtil();
        File file = new File(pdfUtil.dest);
        file.getParentFile().mkdirs();
        pdfUtil.createPdf();
    }
    public void createPdf() throws IOException{

        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.setFont(font);

        Text version = new Text("实验室与设备管理处(2016)b");
        Text submitTime = new Text("登记日期");
        Text projectId = new Text("项目编号");
        Paragraph header = new Paragraph().setFontSize(9).add(version).add(submitTime).add(projectId);
        document.add(header);

        Paragraph title = new Paragraph("云南大学采购项目申报表").setFontSize(20)
                .setBold().setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        Text projectType = new Text("采购类型：G-货物(■国产 □进口)     □C-工程     □S-服务");
        Paragraph type = new Paragraph().setFontSize(10).add(projectType);
        document.add(type);

        Table table = new Table(new float[]{1,2,1,2});
        table.setWidthPercent(100)
                .setTextAlignment(TextAlignment.CENTER).setFontSize(10);
        tableAddCell(table,"项目名称");
        tableAddCell(table,"老王在干啥啊！",3);
        tableAddCell(table,"项目负责人");
        tableAddCell(table,"坂田银时");
        tableAddCell(table,"负责人电话");
        tableAddCell(table,"18487275680");
        tableAddCell(table,"项目经办人");
        tableAddCell(table,"经办人电话");
        tableAddCell(table,"经办人电话");
        tableAddCell(table,"18487275680");
        tableAddCell(table,"预算总额");
        tableAddCell(table,"10000");
        tableAddCell(table,"资金来源");
        tableAddCell(table,"国家");

        tableAddCell(table,"购置理由",100f);
        Cell reasonCell = new Cell(1,3).add(new Paragraph("就是想买，你打我啊！"));
        reasonCell.setTextAlignment(TextAlignment.LEFT);
        setDefaultFont(reasonCell);
        table.addCell(reasonCell);

        Table itemTable  = new Table(new float[]{2,2,8,2,3,3,3,4});
        itemTable.setWidthPercent(100).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
        tableAddCell(itemTable,"序号");
        tableAddCell(itemTable,"类型");
        tableAddCell(itemTable,"通用名称");
        tableAddCell(itemTable,"数量");
        tableAddCell(itemTable,"计量单位");
        tableAddCell(itemTable,"预算单价");
        tableAddCell(itemTable,"合计金额");
        tableAddCell(itemTable,"交货地点");

        document.add(table);
        document.add(new Paragraph("Item List").setFontSize(20).setTextAlignment(TextAlignment.CENTER));
        document.add(itemTable);
        document.close();
    }
    private void tableAddCell(Table table, String content){
        Cell cell = createCell(new Paragraph(content),1,1,HorizontalAlignment.CENTER,VerticalAlignment.MIDDLE,20);
        table.addCell(cell);
    }
    private void tableAddCell(Table table,String content,int colSpan){
        Cell cell = createCell(new Paragraph(content),1,colSpan,HorizontalAlignment.CENTER,VerticalAlignment.MIDDLE,20);
        table.addCell(cell);
    }
    private void tableAddCell(Table table,String content,float height){
        Cell cell = createCell(new Paragraph(content),1,1,HorizontalAlignment.CENTER,VerticalAlignment.MIDDLE,height);
        table.addCell(cell);
    }
    private Cell createCell(Paragraph paragraph,int rowSpan, int colSpan, HorizontalAlignment horizontalAlignment,
                            VerticalAlignment verticalAlignment, float height){
        Cell cell = new Cell(rowSpan,colSpan).add(paragraph);
        cell.setHeight(height);
        cell.setVerticalAlignment(verticalAlignment);
        cell.setHorizontalAlignment(horizontalAlignment);
        cell.setFont(font).setBorder(new SolidBorder(Color.BLACK,0.5f));
        return cell;
    }
    private void setDefaultFont(Cell cell){
        cell.setFont(font).setBorder(new SolidBorder(Color.BLACK,0.5f));
    }
}
