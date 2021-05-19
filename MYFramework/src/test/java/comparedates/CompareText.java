package comparedates;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CompareText {
	
	public static void main(String[] args) {
		
		String input= "COURSE 19001 (Rev 1 - 11/3/2015 08:10 AM America/New York) ";
		int endIndex = input.indexOf("(");
		 input = input.substring(0, endIndex);
		 input=input.trim();
		String filePath="C:\\WorkingfolderPB\\LMSDemo\\Template\\BulkDeleteTemplate.xlsx";
		
		String Itemidwithtext= getCellData(filePath,"LH Template", "Item ID", 1);
		Itemidwithtext=Itemidwithtext.trim();
		if(Itemidwithtext.equalsIgnoreCase(input)){
			System.out.println("pass");
		}
		
		
	}

	public static String getCellData(String filepath,String sheetName,String colName,int rowNum){
		Cell headercell = null;
		Row headerrow;

		try{
			File file= new File(filepath);
			FileInputStream fis= new FileInputStream(file);
			XSSFWorkbook Workbook = new XSSFWorkbook(fis);
			int col_Num=0;		
			XSSFSheet inputsheet = Workbook.getSheet(sheetName);
			Row inputrow = inputsheet.getRow(0);


			for(int i=0;i<inputrow.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				String inputvalue = inputrow.getCell(i).getStringCellValue().trim();
				String value = colName.trim();

				if(inputrow.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
					col_Num=i;

					break;
				}
			}
			headerrow=inputsheet.getRow(rowNum);
			try {
				headercell = headerrow.getCell(col_Num);
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			if(headercell==null) {
				return "";
			}
			/*if(col_Num==-1) {
			return "";
		}
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(rowNum-1);
		if(row==null)
			return "";*/

			//	Cell cell = inputrow.getCell(col_Num);

			/*if(cell==null) {
			return "";
		}*/
			//System.out.println(cell.getCellType());
			if(headercell.getCellType()==Cell.CELL_TYPE_STRING) {
				return headercell.getStringCellValue();
			}
			else if(headercell.getCellType()==Cell.CELL_TYPE_NUMERIC || headercell.getCellType()==Cell.CELL_TYPE_FORMULA ){

				String cellText  = String.valueOf(headercell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(headercell)) {
					// format in form of M/D/YY
					double d = headercell.getNumericCellValue();

					Calendar cal =Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText =(String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
							cal.get(Calendar.MONTH)+1 + "/" + 
							cellText;

					//System.out.println(cellText);

				}



				return cellText;
			}else if(headercell.getCellType()==Cell.CELL_TYPE_BLANK) {
				return ""; 
			}
			else {
				return String.valueOf(headercell.getBooleanCellValue());
			}

		}
		catch(Exception e){

			e.printStackTrace();
			return "row "+rowNum+" or column "+colName +" does not exist in xls";
		}
	}

}
