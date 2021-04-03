package exceloperations.bordering;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class bordering {
	static Logger logger = Logger.getLogger(bordering.class.getName());

	public static void main(String[] args) throws IOException {
		 String outputfilepath=createFolder();
		Writetoexcel(outputfilepath + "/" + "ErrorSummary"  + "_" + 1 + ".xlsx");
		
			}

	private static String createFolder() {
		//logger.info("user directory is"+System.getProperty("user.dir"));
		
		String currentUsersHomeDir=System.getProperty("user.home");
		logger.info("user directory is"+System.getProperty("user.home"));
		String otherFolder = currentUsersHomeDir + File.separator + "Border";
		File f = new File(otherFolder);
		boolean folderflag = f.mkdir();		
		return otherFolder;
	      	}

	private static void Writetoexcel(String filepath) throws IOException {
		 boolean result=false;
		File file = new File(filepath);
		XSSFWorkbook wb = null;
		XSSFSheet sheet;
		Row headerrow = null;
		int RowCount = 0;
		Cell headercell;
		String rowstatus;
		if(!file.exists()) {	
		String[] header_constants=new String[]{"Employee Objects","Issue","Error Count"};
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		 sheet = workbook.createSheet("Test");
		 
		CellStyle style=workbook.createCellStyle();
		Font font=workbook.createFont();
		font.setColor(IndexedColors.WHITE.getIndex());
		XSSFColor red=new XSSFColor(new java.awt.Color(255,0,0));
		style.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		//xssfStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(font);
		style.setBorderBottom(XSSFCellStyle.BORDER_THICK);
		style.setBorderTop(XSSFCellStyle.BORDER_THICK);
		style.setBorderRight(XSSFCellStyle.BORDER_THICK);
		style.setBorderLeft(XSSFCellStyle.BORDER_THICK);
		


		Row row=sheet.createRow(0);
		for(int i=0;i<header_constants.length;i++) {
			Cell cell=row.createCell(i);
			cell.setCellValue(header_constants[i]);
			cell.setCellStyle(style);
			//cell.setCellStyle((CellStyle) fontStyle);

		
		
	}
		FileOutputStream fos = new FileOutputStream(filepath);
		fos=new FileOutputStream(filepath);
		workbook.write(fos);
		fos.flush();
		fos.close();
		
		 result = true;

}
		else{
			
		}
		
	}
	
}
		
