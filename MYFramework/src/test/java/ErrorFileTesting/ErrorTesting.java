package ErrorFileTesting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ErrorTesting {
	
	 

	private static XSSFWorkbook wb;

	public static void main(String[] args) throws IOException, InvalidFormatException {
		String filepath = "C:\\WorkingfolderPB\\Test";
		 File[] Files = new File(filepath).listFiles();
		int size = Files.length;
		int duplicatenumber=0;
		ArrayList ErrorList = new ArrayList<>();
		ArrayList DuplicateErrorList = new ArrayList<>();
		for(int Filecounter=0;Filecounter<size;Filecounter++){
			 File file = Files[Filecounter];
			 String filename = file.getName();
			 System.out.println(filename);
			FileInputStream fis = new FileInputStream(file);
			/*Workbook wb = WorkbookFactory.create(new File(filename)); 
			Sheet sheet = wb.getSheetAt(0);
			 wb= new XSSFWorkbook();*/
			wb = new XSSFWorkbook(fis);
			int index = wb.getActiveSheetIndex();
			int names=wb.getNumberOfNames();
			XSSFSheet sheet = wb.getSheetAt(index);
			int rowsize = sheet.getPhysicalNumberOfRows();
			int cellno=sheet.getRow(0).getPhysicalNumberOfCells();
			for(int columncounter=0;columncounter<cellno;columncounter++){
				String errorcellname = sheet.getRow(0).getCell(columncounter).getStringCellValue();
				if(errorcellname.equalsIgnoreCase("Errors")){
					for(int rowcounter=1;rowcounter<rowsize;rowcounter++){
						String cellnamme = sheet.getRow(rowcounter).getCell(columncounter).getStringCellValue();				
							ErrorList.add(cellnamme);
						
					}
					for(int uniqueerrorcnt=1;uniqueerrorcnt<ErrorList.size();uniqueerrorcnt++){
						String errorcellvalue = sheet.getRow(uniqueerrorcnt).getCell(columncounter).getStringCellValue();
						if(!DuplicateErrorList.contains(errorcellvalue)){
							DuplicateErrorList.add(errorcellvalue);
						}
					}
				}
					
			}
			for(int counter=1;counter<DuplicateErrorList.size();counter++){
				String errormessage = DuplicateErrorList.get(counter).toString();
				int countA=Collections.frequency(ErrorList, errormessage);
				Writetoexcel("C:\\WorkingfolderPB\\Error Summary POC" + "/" + "Erroroutputsummary"  + "_" + ".xlsx");
			}
			
			
				
			
			
			
			
			
			
		}
		/*int countA=Collections.frequency(ErrorList, "abc"); 
		int countb=Collections.frequency(ErrorList, "pqr"); 
		int countc=Collections.frequency(ErrorList, "klm"); 
		*/
				
		

		 
		
	}

	private static void Writetoexcel(String filepath) throws IOException {
		String[] header_constants=new String[]{"SL.No","Errorname","Errorcount"};
		
		 XSSFSheet sheet = wb.getSheet("Error");
		CellStyle xssfStyle=wb.createCellStyle();
		Font font=wb.createFont();
		font.setColor(IndexedColors.WHITE.getIndex());
		XSSFColor red=new XSSFColor(new java.awt.Color(255,0,0));
		xssfStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		xssfStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		//xssfStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		xssfStyle.setFont(font);
		


		int rownum = sheet.getPhysicalNumberOfRows();
		XSSFRow headerrow = null;
		XSSFRow row = sheet.getRow(rownum);	
			 headerrow = sheet.createRow(rownum);
		
		for(int i=0;i<header_constants.length;i++) {
			Cell cell=headerrow.createCell(i);
			cell.setCellValue(header_constants[i]);
			cell.setCellStyle(xssfStyle);
			//cell.setCellStyle((CellStyle) fontStyle);

		}

		FileOutputStream fos = new FileOutputStream(filepath);
		fos=new FileOutputStream(filepath);
		wb.write(fos);
		fos.flush();
		fos.close();

		
	}

	private static void executeScript(String Filepath) throws FileNotFoundException {
		
		
		
		
	}

}
