package exceloperations.bordering.convercsvroexcel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import au.com.bytecode.opencsv.CSVReader;

class Test {
    public static void main(String[] args) throws IOException {
    	File file= new File("C:\\WorkingfolderPB\\Test\\Book1.xlsx");
    	FileInputStream fis = new FileInputStream(file);
    			Workbook wb = new XSSFWorkbook(fis);
        CreationHelper helper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("new sheet");

        CSVReader reader = new CSVReader(new FileReader("C:\\WorkingfolderPB\\Error Summary POC\\jobResponse1111122_Personal Info.csv"));
        String[] line;
        int r = 0;
        while ((line = reader.readNext()) != null) {
            Row row = sheet.createRow((short) r++);

            for (int i = 0; i < line.length; i++)
                row.createCell(i)
                   .setCellValue(helper.createRichTextString(line[i]));
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("C:\\WorkingfolderPB\\Test\\Book1.xlsx");
        wb.write(fileOut);
        fileOut.close();
        
    }
}