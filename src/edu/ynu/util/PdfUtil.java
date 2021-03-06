package edu.ynu.util;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import edu.ynu.entity.ItemEntity;
import edu.ynu.entity.ProjectEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PdfUtil {

    private String generatePath;
    private PdfFont font;

    public PdfUtil() throws IOException {
        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream("/project.properties");
        prop.load(in);
        generatePath = prop.getProperty("generatePath");
        String fontPath = prop.getProperty("fontPath");
        font = PdfFontFactory.createFont(fontPath,PdfEncodings.IDENTITY_H,true);
    }

    public void createProjectPdfFile(ProjectEntity projectEntity)throws IOException{
        String filePath = generatePath + projectEntity.getProjectId() + ".pdf";
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        createProjectPdf(projectEntity,filePath);
    }
    public void createProjectPdf(ProjectEntity projectEntity,String filePath) throws IOException{

        //准备工作
        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdf = new PdfDocument(writer);
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new MyEventHandler());//页码处理
        Document document = new Document(pdf);
        document.setFont(font);

        //表头
        Text version = new Text("实验室与设备管理处(2016)b                       ");
        Text submitTime = new Text("登记日期 " + projectEntity.getSubmitTime().substring(0,10)+ "    ");
        Text projectId = new Text("                      项目编号 " + projectEntity.getProjectId());
        Paragraph header = new Paragraph().setFontSize(9).add(version).add(submitTime).add(projectId);
        document.add(header);

        Paragraph title = new Paragraph("云南大学采购项目申报表").setFontSize(20)
                .setBold().setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        Text projectType = new Text("采购类型:" + projectEntity.getPurchaseType());
        Paragraph type = new Paragraph().setFontSize(10).add(projectType);
        document.add(type);

        //主要信息
        Table table = new Table(new float[]{1,2,1,2});
        table.setWidthPercent(100)
                .setTextAlignment(TextAlignment.CENTER).setFontSize(10);
        tableAddCell(table,"项目名称");
        tableAddCell(table,"" + projectEntity.getProjectName(),3);
        tableAddCell(table,"项目负责人");
        tableAddCell(table,"" + projectEntity.getProposerName());
        tableAddCell(table,"项目经办人");
        tableAddCell(table,"" + projectEntity.getAgentName());
        tableAddCell(table,"经办人固话");
        tableAddCell(table,"" + projectEntity.getAgentTel());
        tableAddCell(table,"经办人手机");
        tableAddCell(table,"" + projectEntity.getAgentMobile());
        tableAddCell(table,"预算总额");
        tableAddCell(table,"" + projectEntity.getSum());
        tableAddCell(table,"资金来源");
        tableAddCell(table,"" + projectEntity.getFundSource());

        final float CELL_HEIGHT = 95f;
        tableAddCell(table,"购置理由",CELL_HEIGHT);
        Cell reasonCell = new Cell(1,3).add(new Paragraph("" + projectEntity.getApplyReason()));
        reasonCell.setTextAlignment(TextAlignment.LEFT).setBorder(new SolidBorder(Color.BLACK,0.5f));
        table.addCell(reasonCell);

        Paragraph cellFooter = new Paragraph("（签字、日期及单位公章）");
        cellFooter.setFontSize(6);
        cellFooter.setTextAlignment(TextAlignment.RIGHT);

        tableAddCell(table,"申报单位审核意见",CELL_HEIGHT);
        Cell suggestion = new Cell(1,1).add(cellFooter)
                .setBorder(new SolidBorder(Color.BLACK,0.5f))
                .setVerticalAlignment(VerticalAlignment.BOTTOM);
        table.addCell(suggestion);

        tableAddCell(table,"经费主管部门审核意见",CELL_HEIGHT);
        Cell suggestion1 = new Cell(1,1).add(cellFooter)
                .setBorder(new SolidBorder(Color.BLACK,0.5f))
                .setVerticalAlignment(VerticalAlignment.BOTTOM);
        table.addCell(suggestion1);

        tableAddCell(table,"采购管理部门审核意见",CELL_HEIGHT);
        Cell suggestion2 = new Cell(1,3).add(cellFooter)
                .setBorder(new SolidBorder(Color.BLACK,0.5f))
                .setVerticalAlignment(VerticalAlignment.BOTTOM);
        table.addCell(suggestion2);

        Paragraph cellP = new Paragraph("资金性质：□预算管理   □其它专项资金")
                .setTextAlignment(TextAlignment.LEFT)
                .setHeight(CELL_HEIGHT)
                .setVerticalAlignment(VerticalAlignment.TOP);

        tableAddCell(table,"财务部门审核意见",CELL_HEIGHT);
        Cell suggestion3 = new Cell(1,3).add(cellP)
                .setBorder(new SolidBorder(Color.BLACK,0.5f))
                .add(cellFooter);
        table.addCell(suggestion3);

        tableAddCell(table,"分管校领导意见",CELL_HEIGHT);
        Cell suggestion4 = new Cell(1,3).add(cellFooter)
                .setBorder(new SolidBorder(Color.BLACK,0.5f))
                .setVerticalAlignment(VerticalAlignment.BOTTOM);
        table.addCell(suggestion4);

        tableAddCell(table,"备注",CELL_HEIGHT);
        Cell suggestion5 = new Cell(1,3).add(new Paragraph());
        suggestion5.setTextAlignment(TextAlignment.LEFT).setBorder(new SolidBorder(Color.BLACK,0.5f));
        table.addCell(suggestion5);

        document.add(table);

        //项目清单
        Paragraph itemTitle = new Paragraph("--采购项目清单--").setFontSize(20)
                .setBold().setTextAlignment(TextAlignment.CENTER);
        document.add(itemTitle);

        Table itemTable  = new Table(new float[]{1.8f,2,8,2,3,3,3,4});
        itemTable.setWidthPercent(100).setTextAlignment(TextAlignment.CENTER).setFontSize(10);
        tableAddCell(itemTable,"序号");
        tableAddCell(itemTable,"类型");
        tableAddCell(itemTable,"通用名称");
        tableAddCell(itemTable,"数量");
        tableAddCell(itemTable,"计量单位");
        tableAddCell(itemTable,"预算单价");
        tableAddCell(itemTable,"合计金额");
        tableAddCell(itemTable,"交货地点");
        int index = 1;
        for(ItemEntity item:projectEntity.getItems()){
            tableAddCell(itemTable,String.valueOf(index));
            tableAddCell(itemTable,item.getType());
            tableAddCell(itemTable,item.getItemName());
            tableAddCell(itemTable,item.getCount());
            tableAddCell(itemTable,item.getUnit());
            tableAddCell(itemTable,item.getPrice());
            tableAddCell(itemTable,item.getTotal());
            tableAddCell(itemTable,item.getDeliverySite());
            index++;
        }
        document.add(itemTable);

        //关闭文档
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
        cell.setBorder(new SolidBorder(Color.BLACK,0.5f));
        return cell;
    }
    protected class MyEventHandler implements IEventHandler {
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            int pageNumber = pdfDoc.getPageNumber(page);
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
            String footer = "第 " + pageNumber + " 页 - " + "共 " + pdfDoc.getNumberOfPages() + " 页";
            //增加页码
            pdfCanvas.beginText()
                    .setFontAndSize(font,9)
                    .moveText(pageSize.getWidth()/2-25,20)
                    .showText(footer)
                    .endText();
            pdfCanvas.release();
        }
    }
}
