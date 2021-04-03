package exceloperations.bordering;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class bordering2 {
	static Logger logger = Logger.getLogger(bordering2.class.getName());

	public static void main(String[] args) throws IOException {
		 String Filepath="C:\\Users\\parag.borawake\\Border\\bordering.xlsx";
		
		addstyle(Filepath);
		
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
		font.setColor(IndexedColors.BLACK.getIndex());
		XSSFColor red=new XSSFColor(new java.awt.Color(255,0,0));
		style.setFont(font);


		Row row1=sheet.createRow(0);
		int rowsize = sheet.getPhysicalNumberOfRows();
		for(int rowcounter=0;rowcounter<10;rowcounter++){
			XSSFRow row = sheet.createRow(rowcounter);
		for(int i=0;i<header_constants.length;i++) {
			// row = sheet.createRow(i);
			Cell cell=row.createCell(i);
			cell.setCellValue(header_constants[i]);
			//cell.setCellStyle(style);
			//cell.setCellStyle((CellStyle) fontStyle);

	}
		
		
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
	public static  void addstyle(String filepath) throws IOException{
		boolean result=false;
		File file = new File(filepath);
		XSSFWorkbook wb = null;
		XSSFSheet sheet;
		Row headerrow = null;
		int RowCount = 0;
		Cell headercell;
		String rowstatus;
		String status = "";
		FileOutputStream fos;
		Cell cell = null;

		FileInputStream fis = new FileInputStream(file);
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet("Sheet1");
		CellStyle style=wb.createCellStyle();
		//Font font=wb.createFont();

		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);

		int rowsize=sheet.getPhysicalNumberOfRows();
		int coloumnsize=sheet.getRow(0).getPhysicalNumberOfCells();
		for(int rowcounter=0;rowcounter<rowsize;rowcounter++){
			XSSFRow row = sheet.getRow(rowcounter);
			for(int coloumncounter=0;coloumncounter<coloumnsize;coloumncounter++) {
				// row = sheet.createRow(i);
				try{
				 cell=row.getCell(coloumncounter);
				}
				catch (Exception e) {
				
				}
				if(cell==null){
					cell= row.createCell(coloumncounter);
				}
				cell.setCellStyle(style);
				//cell.setCellStyle((CellStyle) fontStyle);

			}
			
			
	
		}
		 fos = new FileOutputStream(filepath);		
			wb.write(fos);
			fos.flush();
			fos.close();
		
			
		
	}
}
		
