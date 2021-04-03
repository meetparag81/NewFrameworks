package exceloperations.bordering;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

public class Errormessagedriver_delete {
	
	 

	private static XSSFWorkbook wb;
	private static int writetoexcelcounter;
	private static int sheetcounter=0;
	static Logger logger = Logger.getLogger(Errormessagedriver_delete.class.getName());

	public static void main(String[] args) throws IOException, InvalidFormatException {
		int countA = 0;
		 
		String filename;
		String  PermissionHeader="";
		String errormessage = "";
		String excelsheetname ="";
		String outputTimeStamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String csvfilepath="C:\\WorkingfolderPB\\Error Summary POC";
		//createFolder();
		String excelfilepath =  createFolder();
		String outputfilepath="C:\\Users\\parag.borawake\\RESOURCES\\FMS";
		//create excel fromcsv
		executeScript(csvfilepath, excelfilepath);
		
		 File[] Files = new File(excelfilepath).listFiles();
		int size = Files.length;
		int duplicatenumber=0;
		int deletecounter=0;
		
		ArrayList ErrorList = new ArrayList<>();
		ArrayList DuplicateErrorList = new ArrayList<>();
		//Iterate over each file
		for(int Filecounter=0;Filecounter<size;Filecounter++){
			writetoexcelcounter=0;
			  PermissionHeader="";
			 errormessage = "";
			 excelsheetname ="";
			 ErrorList = new ArrayList<>();
			 DuplicateErrorList = new ArrayList<>();
			
			
			 File excelfile = Files[Filecounter];			 
			 String excelfilename = excelfile.getName();			 
			 String namefile = excelfilename.replaceFirst("[.][^.]+$", "");
			 if(namefile.contains("_")) {
			   excelsheetname = namefile.split("_")[1];
			 }
			 else {
				  excelsheetname = "sheetnew"+"_"+Filecounter;
			}
			   		  
			  //To add constant header in excel
			  Writetoexcel(outputfilepath + "/" + "ErrorSummary"  + "_" + outputTimeStamp + ".xlsx",PermissionHeader, errormessage,countA,excelsheetname );
			  PermissionHeader="headertext";
			  sheetcounter=0;
			  //To add the sheetname and value under Employee Objectsname column			  
			  Writetoexcel(outputfilepath + "/" + "ErrorSummary"  + "_" + outputTimeStamp + ".xlsx",PermissionHeader,errormessage,countA,excelsheetname );
			  PermissionHeader="";
			  
			 
			  
			 System.out.println(namefile);
			FileInputStream fis = new FileInputStream(excelfile);
			wb = new XSSFWorkbook(fis);
			XSSFSheet inputsheet = wb.getSheet(excelsheetname);
			int rowsize = inputsheet.getPhysicalNumberOfRows();
			int cellno=inputsheet.getRow(0).getPhysicalNumberOfCells();
			for(int columncounter=0;columncounter<cellno;columncounter++){
				String errorcellname = inputsheet.getRow(0).getCell(columncounter).getStringCellValue();
				if(errorcellname.equalsIgnoreCase("Errors")){
					for(int rowcounter=1;rowcounter<rowsize;rowcounter++){
						String cellnamme = inputsheet.getRow(rowcounter).getCell(columncounter).getStringCellValue();				
							ErrorList.add(cellnamme);
						
					}
					for(int uniqueerrorcnt=1;uniqueerrorcnt<ErrorList.size();uniqueerrorcnt++){
						String errorcellvalue="";
						try {
							errorcellvalue= inputsheet.getRow(uniqueerrorcnt).getCell(columncounter).getStringCellValue();
						}
						catch (Exception e) {
							// TODO: handle exception
						}
						if(!DuplicateErrorList.contains(errorcellvalue)&&!errorcellvalue.equals("Errors")){
							DuplicateErrorList.add(errorcellvalue);
						}
					}
				}
					
			}
			for(int counter=0;counter<DuplicateErrorList.size();counter++){
				 PermissionHeader="";
				  PermissionHeader="errortext";				 
				 errormessage = DuplicateErrorList.get(counter).toString();
				 countA=Collections.frequency(ErrorList, errormessage);
				 System.out.println("Errormessage is"+errormessage+"count of errormessage"+errormessage+"is"+countA );
				Writetoexcel(outputfilepath + "/" + "ErrorSummary"  + "_" + outputTimeStamp + ".xlsx", PermissionHeader,errormessage,countA,excelsheetname );
				writetoexcelcounter++;
				
				
				
			}
			sheetcounter++;
			deletecounter++;
			
		}
		
		PermissionHeader="";
		sheetcounter=0;
		PermissionHeader="ConsolidatedSummary";
		Writetoexcel(outputfilepath + "/" + "ErrorSummary"  + "_" + outputTimeStamp + ".xlsx", PermissionHeader,errormessage,countA,excelsheetname );
		
		
		
		PermissionHeader="";
		PermissionHeader="Summarytext";
		 Writetoexcel(outputfilepath + "/" + "ErrorSummary"  + "_" + outputTimeStamp + ".xlsx", PermissionHeader,errormessage,countA,excelsheetname );
		 logger.info("Summary details are added for "+ size+" sheets");
		sheetcounter++;
		if(deletecounter>=size) {
			File[] Filesdelete = new File(excelfilepath).listFiles();
			int sizedelete = Files.length;
			for(int Filecounter=0;Filecounter<size;Filecounter++){
				 File excelfile = Files[Filecounter];
				 excelfile.delete();
				 if(Filecounter==size-1) {
					 File filedelete= new File(excelfilepath);
					 filedelete.delete();
					 logger.info("excelfolderdeleted");
				 }
				 
				
			}
			
			
		}
			
		
		
		
	}
	
	

	private static String  createFolder() {
		//logger.info("user directory is"+System.getProperty("user.dir"));
		
		String currentUsersHomeDir=System.getProperty("user.home");
		logger.info("user directory is"+System.getProperty("user.home"));
		String otherFolder = currentUsersHomeDir + File.separator + "Test";
		File f = new File(otherFolder);
		boolean folderflag = f.mkdir();		
		return otherFolder;
	      	}



	private static boolean Writetoexcel(String filepath,String PermissionHeader, String errormessage, int countA, String sheetname) throws IOException {
		boolean result = false;
		try {
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
			 sheet = workbook.createSheet(sheetname);
			 
			CellStyle xssfStyle=workbook.createCellStyle();
			Font font=workbook.createFont();
			font.setColor(IndexedColors.WHITE.getIndex());
			XSSFColor red=new XSSFColor(new java.awt.Color(255,0,0));
			xssfStyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
			xssfStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			//xssfStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			xssfStyle.setFont(font);
			


			Row row=sheet.createRow(0);
			for(int i=0;i<header_constants.length;i++) {
				Cell cell=row.createCell(i);
				cell.setCellValue(header_constants[i]);
				cell.setCellStyle(xssfStyle);
				//cell.setCellStyle((CellStyle) fontStyle);

			}

			FileOutputStream fos = new FileOutputStream(filepath);
			fos=new FileOutputStream(filepath);
			workbook.write(fos);
			fos.flush();
			fos.close();
			//workbook.close();
			result=true;
			}
			else if (sheetcounter>=1&&errormessage.isEmpty()) {				
				String[] header_constants=new String[]{"Employee Objects","Issue","Error Count"};
				FileInputStream fis = new FileInputStream(file);
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				 sheet = workbook.createSheet(sheetname);
				 
				CellStyle xssfStyle=workbook.createCellStyle();
				Font font=workbook.createFont();
				font.setColor(IndexedColors.WHITE.getIndex());
				XSSFColor red=new XSSFColor(new java.awt.Color(255,0,0));
				xssfStyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
				xssfStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				//xssfStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				xssfStyle.setFont(font);
				XSSFRow rowheader;


				Row row=sheet.createRow(0);
				for(int i=0;i<header_constants.length;i++) {
					Cell cell=row.createCell(i);
					cell.setCellValue(header_constants[i]);
					cell.setCellStyle(xssfStyle);
					//cell.setCellStyle((CellStyle) fontStyle);

				}
				

				FileOutputStream fos = new FileOutputStream(filepath);
				fos=new FileOutputStream(filepath);
				workbook.write(fos);
				fos.flush();
				fos.close();
				//workbook.close();
				result=true;
				
				
			}
			
			
			else {
				boolean flag =false;
				FileInputStream fis = new FileInputStream(filepath);
				wb=new XSSFWorkbook(fis);
				sheet=wb.getSheet(sheetname);
				//XSSFSheet summarysheet = wb.getSheet("Consolidated Summary");
				

				if( PermissionHeader.equals("headertext")) {
					XSSFRow rowheaderemployeeobj = null;
					
					int lastrowcount = sheet.getLastRowNum();
					int totalcells = sheet.getRow(0).getPhysicalNumberOfCells();
					for(int headercounter=0;headercounter<totalcells;headercounter++) {
						String EmployeeObjects=sheet.getRow(0).getCell(headercounter).getStringCellValue();
						if(EmployeeObjects.equals("Employee Objects")) {
							try {
							 rowheaderemployeeobj = sheet.getRow(lastrowcount+1);
							}
							catch (Exception e) {
								rowstatus="null";
								 
							}
							if(rowheaderemployeeobj==null) {
								rowheaderemployeeobj=sheet.createRow(lastrowcount+1);
								rowheaderemployeeobj.createCell(headercounter).setCellValue(sheetname);
								break;
							}
							else {
								rowheaderemployeeobj.createCell(headercounter).setCellValue(sheetname);
								break;
							}
						}
					}
					flag =true;	
				}
				else if (PermissionHeader.equalsIgnoreCase("errortext")) {
					int totalcells = sheet.getRow(0).getPhysicalNumberOfCells();
					for(int headercounter=0;headercounter<totalcells;headercounter++) {
						String EmployeeObjects=sheet.getRow(0).getCell(headercounter).getStringCellValue();
						if(EmployeeObjects.equals("Issue")) {
							int lastrowcount = sheet.getLastRowNum();
							if(writetoexcelcounter>0) {
								lastrowcount=lastrowcount+1;
							}
							XSSFRow rowheaderissue = null;
							try {
								rowheaderissue = sheet.getRow(lastrowcount);
							}
							catch (Exception e) {
								// TODO: handle exception
							}
							if(rowheaderissue==null) {
								rowheaderissue=sheet.createRow(lastrowcount);
								rowheaderissue.createCell(headercounter).setCellValue(errormessage);
								break;
							}
							else {
								rowheaderissue.createCell(headercounter).setCellValue(errormessage);
								break;
							}
						}
					}
					for(int headercounter=0;headercounter<totalcells;headercounter++) {
						String EmployeeObjects=sheet.getRow(0).getCell(headercounter).getStringCellValue();
						if(EmployeeObjects.equals("Error Count")) {
							int lastrowcount = sheet.getLastRowNum();
							
							XSSFRow rowheadeerrorcount = null;
							try {
							rowheadeerrorcount = sheet.getRow(lastrowcount);
							}
							catch (Exception e) {
								// TODO: handle exception
							}
							if(rowheadeerrorcount==null) {
								rowheadeerrorcount=sheet.createRow(lastrowcount);
								rowheadeerrorcount.createCell(headercounter).setCellValue(countA);
								break;
							}
							else {
								rowheadeerrorcount.createCell(headercounter).setCellValue(countA);
								break;
							}
						}
					}
					 flag = true;	
				}
				else if (PermissionHeader.equalsIgnoreCase("Summarytext")) {
					int noofsheets=wb.getNumberOfSheets();
					String rowinputvalue = "";
					String columninputvalue = "";
					ArrayList EmployeeObjects = new ArrayList<>();
					ArrayList Issue = new ArrayList<>();
					LinkedHashMap<String, String> Issueandcount= new LinkedHashMap();
					ArrayList ErrorCount= new ArrayList<>();
					
					for(int sheetcounter=0;sheetcounter<noofsheets-1;sheetcounter++) {
						
						XSSFSheet inputsheet = wb.getSheetAt(sheetcounter);
						String inputsheetname = inputsheet.getSheetName();
						int totalcells = inputsheet.getRow(0).getPhysicalNumberOfCells();
						int rows = inputsheet.getPhysicalNumberOfRows();

						for(int headercounter=0;headercounter<totalcells;headercounter++) {
							columninputvalue=sheet.getRow(0).getCell(headercounter).getStringCellValue();
							if(columninputvalue.equalsIgnoreCase("Employee Objects")) {
								for(int EmpObjcounter=1; EmpObjcounter<rows;EmpObjcounter++) {
									rowinputvalue="";
									
									try {
										rowinputvalue=inputsheet.getRow(EmpObjcounter).getCell(headercounter).getStringCellValue();
									}
									catch (Exception e) {

									}
									if(!rowinputvalue.isEmpty()) {								

										EmployeeObjects.add(rowinputvalue);	
									}

								}
							}
							else if (columninputvalue.equalsIgnoreCase("Issue")) {
								rowinputvalue="";
								String errorcount ="";
								int count = 0;

								for(int issuecounter=1; issuecounter<rows;issuecounter++) {
									try {
										rowinputvalue=inputsheet.getRow(issuecounter).getCell(headercounter).getStringCellValue();
										 errorcount = inputsheet.getRow(issuecounter).getCell(headercounter+1).toString();
										 try {
											 float floatcount = Float.parseFloat(errorcount);
										  count= (int)floatcount;
										 }
										 catch (Exception e) {
											
										}
										 logger.info("value for error is"+count );
									}
									catch (Exception e) {

									}
									if(!rowinputvalue.isEmpty()) {
										Issueandcount.put(rowinputvalue, errorcount);
										Issue.add(rowinputvalue);
									}

								}

								flag = true;	
							}
							else if (columninputvalue.equalsIgnoreCase("Error Count")) {
								rowinputvalue="";
								int count = 0;

								for(int issuecounter=1; issuecounter<rows;issuecounter++) {
									try {
										rowinputvalue=inputsheet.getRow(issuecounter).getCell(headercounter).toString();
										 try {
											 float floatcount = Float.parseFloat(rowinputvalue);
										  count= (int)floatcount;
										  rowinputvalue= String.valueOf(count);
										 }
										 catch (Exception e) {
											
										}
										 logger.info("value for error is"+rowinputvalue );
									}									
									catch (Exception e) {

									}
									if(!rowinputvalue.isEmpty()) {
										ErrorCount.add(rowinputvalue);	
									}

								}



									
							}



						}
						writetosummary(EmployeeObjects,Issue,Issueandcount,ErrorCount,filepath);
						 EmployeeObjects = new ArrayList<>();
						 Issue = new ArrayList<>();
						 Issueandcount= new LinkedHashMap();
						 ErrorCount= new ArrayList<>();
						

					}
					
					
					
											
					flag = false;		
				}
				else if (PermissionHeader.equalsIgnoreCase("ConsolidatedSummary")&&sheetcounter==0) {
					String[] header_constants=new String[]{"Employee Objects","Issue","Error Count"};
					File File = new File(filepath);
					FileInputStream fils= new FileInputStream(File);
					 XSSFSheet sheetconsolidated = wb.createSheet("Consolidated Summary");			 
						CellStyle xssfStyle1=wb.createCellStyle();
						Font fontcolour=wb.createFont();
						 
						fontcolour.setColor(IndexedColors.WHITE.getIndex());
						XSSFColor headercolour=new XSSFColor(new java.awt.Color(255,0,0));
						xssfStyle1.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
						xssfStyle1.setFillPattern(CellStyle.SOLID_FOREGROUND);
						//xssfStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
						xssfStyle1.setFont(fontcolour);
						Row rowsummary=sheetconsolidated.createRow(0);
						for(int i=0;i<header_constants.length;i++) {
							Cell cell=rowsummary.createCell(i);
							cell.setCellValue(header_constants[i]);
							cell.setCellStyle(xssfStyle1);
							//cell.setCellStyle((CellStyle) fontStyle);

						}
					flag = true;	
				}
				else {
					
				}
				if(flag == true) {
				FileOutputStream fos = new FileOutputStream(filepath);
				wb.write(fos);
				fos.flush();
				fos.close();
				//wb.close();
				result=true;	
				}


			}

			
		}
		catch (Exception e) {
			// TODO: handle exception
			result = false;
		}

		return result;

		
	}
		
			
	
		
		
	

	



	private static void writetosummary(ArrayList employeeObjects, ArrayList issue, HashMap<String, String> issueandcount, ArrayList errorCount, String filepath) throws IOException {
		try {		
				String rowinputvalue = "";
				String columninputvalue = "";
				File File = new File(filepath);
				FileInputStream fils= new FileInputStream(File);
				XSSFWorkbook workbooksummary = new XSSFWorkbook(fils);
				CellStyle style = setBorderinexcel(workbooksummary);
				XSSFSheet sheet = workbooksummary.getSheet("Consolidated Summary");
				int totalcells = sheet.getRow(0).getPhysicalNumberOfCells();
				int lastrowcount = sheet.getPhysicalNumberOfRows();

				for(int headercounter=0;headercounter<totalcells;headercounter++) {
					columninputvalue=sheet.getRow(0).getCell(headercounter).getStringCellValue();
					if(columninputvalue.equalsIgnoreCase("Employee Objects")) {

						XSSFRow rowheaderemployeeobj = null;
						for(int EmpObjcounter=1; EmpObjcounter<=employeeObjects.size();EmpObjcounter++) {
							rowinputvalue="";
							try {
								rowheaderemployeeobj = sheet.getRow(lastrowcount+1);
							}
							catch (Exception e) {

							}
							if(rowheaderemployeeobj==null) {
								rowheaderemployeeobj=sheet.createRow(lastrowcount);
								rowheaderemployeeobj.createCell(headercounter).setCellValue(employeeObjects.get(0).toString());
								rowheaderemployeeobj.createCell(headercounter).setCellStyle(style);
								
							}
							else {
								rowheaderemployeeobj.createCell(headercounter).setCellValue(employeeObjects.get(0).toString());
								
							}

						}
						FileOutputStream fos = new FileOutputStream(filepath);
						workbooksummary.write(fos);
						fos.flush();
						fos.close();
						((Closeable) workbooksummary).close();

						
					}
					else if (columninputvalue.equalsIgnoreCase("Issue")) {
						/*for (Map.Entry<String, String> entry : issueandcount.entrySet()) {
							String issuetext = entry.getKey();
							String errorvalue = entry.getValue();
						FileInputStream fils1= new FileInputStream(File);
						XSSFWorkbook workbooksummary1 = new XSSFWorkbook(fils1);
						XSSFSheet summarysheet = workbooksummary1.getSheet("Consolidated Summary");
						int totalcells1 = summarysheet.getRow(0).getPhysicalNumberOfCells();
						int lastrowcount1 = summarysheet.getPhysicalNumberOfRows();
						int lastrowcountissue = summarysheet.getPhysicalNumberOfRows();*/
						for(int counter=0;counter<issue.size();counter++) {
							FileInputStream fils1= new FileInputStream(File);
							XSSFWorkbook workbooksummary1 = new XSSFWorkbook(fils1);
							//CellStyle styleissue = setBorderinexcel(workbooksummary1);
							XSSFSheet summarysheet = workbooksummary1.getSheet("Consolidated Summary");
							int totalcells1 = summarysheet.getRow(0).getPhysicalNumberOfCells();
							int lastrowcount1 = summarysheet.getPhysicalNumberOfRows();
							int lastrowcountissue = summarysheet.getPhysicalNumberOfRows();



							lastrowcountissue=lastrowcountissue;

							XSSFRow rowheaderissue = null;
							try {
								rowheaderissue = summarysheet.getRow(lastrowcountissue);
							}
							catch (Exception e) {
								// TODO: handle exception
							}
							if(rowheaderissue==null) {
								rowheaderissue=summarysheet.createRow(lastrowcount1);
								rowheaderissue.createCell(headercounter).setCellValue(issue.get(counter).toString());
								rowheaderissue.createCell(headercounter).setCellStyle(style);
								rowheaderissue.createCell(headercounter+1).setCellValue(errorCount.get(counter).toString());
								rowheaderissue.createCell(headercounter+1).setCellStyle(style);
								
							}
							else {
								rowheaderissue.getCell(headercounter).setCellValue(issue.get(counter).toString());
								rowheaderissue.getCell(headercounter).setCellValue(errorCount.get(counter).toString());

							}
							
							
							FileOutputStream fos = new FileOutputStream(filepath);	
							workbooksummary1.write(fos);
							fos.flush();
							fos.close();
							//workbooksummary1.close();
							
						}
				
						

					//}
					
					

				}
				
				
			
						
			




			
		}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
			}
	
				
				
			
			



		

		
	



	private static void executeScript(String csvfilepath,String exceloutputpath) throws IOException {
		createExcelFromcsv(csvfilepath, exceloutputpath);
		
		
		
	}
	public static void createExcelFromcsv(String csvfilepath,String exceloutputpath) throws IOException {
		String filepath=csvfilepath;
		String outputfilepath = exceloutputpath;
		File[] Files = new File(filepath).listFiles();
		int size = Files.length;
		for(int Filecounter=0;Filecounter<size;Filecounter++){
			 File file = Files[Filecounter];
			 String filename = file.getName();
			
			  String name = filename.replaceFirst("[.][^.]+$", "");
			  String sheetname = name.split("_")[1];
			  
			  String excelFilepath =outputfilepath + "/" + name +".xlsx";
			   Createexcel(outputfilepath + "/" + name + ".xlsx",sheetname);
			String csvpath = file.getAbsolutePath();
			System.out.println(csvpath);
			String Fileadresscsv = csvpath;//"C:\\WorkingfolderPB\\Error Summary POC\\jobResponse1510072_Phone Info.csv";
			String Fiileadressxlsx =excelFilepath;
			csvToXLSX(Fileadresscsv,Fiileadressxlsx,sheetname);
		}
		
	}
	
	private static void Createexcel(String Filepath, String sheetname) throws IOException {
		boolean result = false;
		File file = new File(Filepath);
		XSSFWorkbook wb = null;
		XSSFSheet sheet;
		Row headerrow = null;
		int RowCount = 0;
		Cell headercell;
		String rowstatus;
		if(!file.exists()) {
			
		String[] header_constants=new String[]{"Employee Objects","Issue","Error Count"};
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		setBorderinexcel(workbook);
		 sheet = workbook.createSheet("sheet1");
		

		FileOutputStream fos = new FileOutputStream(Filepath);			
		workbook.write(fos);
		fos.flush();
		fos.close();
		//workbook.close();
		
		}
		else {
			//file.delete();
			XSSFWorkbook workbook = new XSSFWorkbook();
			 sheet = workbook.createSheet("sheet1");
			

			FileOutputStream fos = new FileOutputStream(Filepath);			
			workbook.write(fos);
			fos.flush();
			fos.close();

		}
		

		
	}
	public static void csvToXLSX(String Fileadress,String xlsxadress, String sheetname) {
	    try {
	        String csvFileAddress = Fileadress; //csv file address
	        String xlsxFileAddress = xlsxadress; //xlsx file address
	         
			File file;
			FileInputStream  fis= new FileInputStream(file= new File(xlsxadress));
	        XSSFWorkbook workBook = new XSSFWorkbook(fis);
	        XSSFSheet sheet = workBook.createSheet(sheetname);
	        String currentLine=null;
	        int RowNum=0;
	        BufferedReader br = new BufferedReader(new FileReader(csvFileAddress));
	        while ((currentLine = br.readLine()) != null) {
	            String str[] = currentLine.split(",");
	            RowNum++;
	            XSSFRow currentRow=sheet.createRow(RowNum-1);
	            for(int i=0;i<str.length;i++){
	            	XSSFCell cell = currentRow.createCell(i);
	            	String data = str[i];
	            	if(currentRow.createCell(i).getStringCellValue().contains(",")){
	            		//data.append("\"").append(cell.getStringCellValue().replaceAll("\"", "\"\"")).append("\",");
	            	}
	                currentRow.createCell(i).setCellValue(str[i]);
	            }
	        }

	        FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress);
	        workBook.write(fileOutputStream);
	        fileOutputStream.close();
	       // workBook.close();
	        System.out.println("Done");
	    } catch (Exception ex) {
	        System.out.println(ex.getMessage()+"Exception in try");
	    }
	}
	public static CellStyle setBorderinexcel(XSSFWorkbook wb) {
		CellStyle style=wb.createCellStyle();
		style.setBorderBottom(style.BORDER_THICK);
		style.setBorderTop(style.BORDER_THICK);
		style.setBorderRight(style.BORDER_THICK);
		style.setBorderLeft(style.BORDER_THICK);
		return style;
		
	}




}
