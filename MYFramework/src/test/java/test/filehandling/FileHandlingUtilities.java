package test.filehandling;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import au.com.bytecode.opencsv.CSVReader;

public class FileHandlingUtilities {
	Properties configProp = new Properties();

	static Logger logger = Logger.getLogger(FileHandlingUtilities.class.getName());

	public static String getServerRootPath() {

		logger.info("catalina.home" + System.getProperty("catalina.base"));
		return System.getProperty("catalina.base");
	}

	public List<File> filesPresentFolder(String filePath) {
		 List<File> filesInFolder = null;
		try {
			filesInFolder = Files.walk(Paths.get(filePath)).filter(Files::isRegularFile).map(Path::toFile)
					.collect(Collectors.toList());
			for (File ite : filesInFolder) {
				logger.info(ite.getAbsolutePath());
			}
		} catch (IOException e) {
			logger.error("StackTrace :", e);
		}
		return filesInFolder;
	}

	public static void recursiveDelete(File file) {
		// to end the recursive loop
		if (!file.exists())
			return;

		// if directory, go inside and call recursively
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				// call recursively
				recursiveDelete(f);
			}
		}
		// call delete to delete files and empty directory
		file.delete();
	}

	public String folderCreation() {
		String foldLoc = System.getProperty("user.home").replaceAll("\\\\", "/") + configProp.getProperty("FileLoc");

		Path path = Paths.get(foldLoc);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
				// return foldLoc;
			} catch (IOException e) {
				// fail to create directory
				logger.error("StackTrace :", e);
				return "";
			}
		} else {
			try {
				recursiveDelete(new File(foldLoc));
				Files.createDirectories(path);
			} catch (IOException e) {
				logger.error("StackTrace :", e);
			}
		}

		if (Files.exists(path)) {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String todaysDate = dateFormat.format(System.currentTimeMillis());

			String upLoc = foldLoc + "/" + todaysDate;
			path = Paths.get(upLoc);

			if (!Files.exists(path)) {
				try {
					Files.createDirectories(path);
					return upLoc;
				} catch (IOException e) {
					// fail to create directory
					logger.error("StackTrace :", e);
					return "";
				}
			}
		}
		return "";
	}

	public Properties fnReadPropertyFile() {
		InputStream input = null;
		try {
			input = this.getClass().getResourceAsStream("/com/botsftool/resources/config.properties");
			configProp.load(input);
			return configProp;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("StackTrace :", e);
				}
			}
		}
		return null;
	}

	public Properties fnReadPropertyFile(String location) {
		Properties configProp = new Properties();
		InputStream input = null;
		try {
			input = this.getClass().getResourceAsStream(location);
			configProp.load(input);
			return configProp;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("StackTrace :", e);
				}
			}
		}
		return configProp;
	}

	@SuppressWarnings("deprecation")
	public void fnConversionCSVtoXLSX(String inputFleLoc, String outputFleLoc) {
		ArrayList<ArrayList<String>> arList = null;
		ArrayList<String> al = null;
		String fName = inputFleLoc;
		String thisLine;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fName);
		} catch (FileNotFoundException e) {
			logger.error("StackTrace :", e);
		}
		DataInputStream myInput = new DataInputStream(fis);
		arList = new ArrayList<ArrayList<String>>();
		try {
			while ((thisLine = myInput.readLine()) != null) {
				al = new ArrayList<String>();
				String strar[] = thisLine.split(",");
				for (int j = 0; j < strar.length; j++) {
					al.add(strar[j]);
				}
				arList.add(al);
				System.out.println();
				
			}
		} catch (IOException e) {
			logger.error("StackTrace :", e);
		}

		try {
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("new sheet");
			for (int k = 0; k < arList.size(); k++) {
				ArrayList<?> ardata = (ArrayList<?>) arList.get(k);
				HSSFRow row = sheet.createRow((short) 0 + k);
				for (int p = 0; p < ardata.size(); p++) {
					HSSFCell cell = row.createCell((short) p);
					String data = ardata.get(p).toString();
					if (data.startsWith("=")) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						data = data.replaceAll("\"", "");
						data = data.replaceAll("=", "");
						cell.setCellValue(data);
					} else if (data.startsWith("\"")) {
						data = data.replaceAll("\"", "");
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(data);
					} else {
						data = data.replaceAll("\"", "");
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(data);
					}
				}
				System.out.println();
			}
			FileOutputStream fileOut = new FileOutputStream(outputFleLoc);
			hwb.write(fileOut);
			fileOut.close();
			hwb.close();
			logger.info("Your excel file has been generated");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void fnConversionCSVtoXLSX1(String inputFleLoc, String outputFleLoc) throws OutOfMemoryError {
		CSVReader reader = null;
		FileOutputStream fileOutputStream = null;
		XSSFWorkbook workBook = null;

		String input_file = inputFleLoc;

		// xls file name
		String xlsxFileAddress = outputFleLoc;

		try {
			// Get the CSVReader instance with specifying the delimiter to be
			// used
			String[] nextLine;
			//reader = new CSVReader(new FileReader(input_file), ',');
			
			reader  = new CSVReader(
			        new InputStreamReader(new FileInputStream(inputFleLoc), "UTF-8"), ','); 

			workBook = new XSSFWorkbook();
			XSSFSheet sheet = workBook.createSheet("sheet1");

			int RowNum = 0;
			while ((nextLine = reader.readNext()) != null) {
				XSSFRow currentRow = sheet.createRow(RowNum++);
				for (int i = 0; i < nextLine.length; i++) {
					
					currentRow.createCell(i).setCellValue(nextLine[i]);
				}
			}

			fileOutputStream = new FileOutputStream(xlsxFileAddress);
			workBook.write(fileOutputStream);
		} catch (Exception e1) {
			// logger.error("StackTrace :",e);
			logger.info("\nFile \"" + input_file + "\" does not exist!");
		} finally {
			if (reader != null) {
				try {
					workBook.close();
					fileOutputStream.close();
					reader.close();
					logger.info("Done");
				} catch (IOException e2) {
					// e2.printStackTrace();
					logger.info("\nFile \"" + input_file + "\" can not be closed correctly!");
				}
			}
		}
	}

	public void fnConvertCSVtoXlSX(String inputFleLoc, String outputFleLoc) {
		CSVReader reader = null;
		FileOutputStream fileOutputStream = null;
		try {
			// Get the CSVReader instance with specifying the delimiter to be
			// used
			String[] nextLine;
			//reader = new CSVReader(new FileReader(new File(inputFleLoc)), ',');
			
			
			reader  = new CSVReader(new InputStreamReader(new FileInputStream(inputFleLoc), "ISO-8859-1"), ','); 
			XSSFWorkbook workBook = new XSSFWorkbook();
			XSSFSheet sheet = workBook.createSheet("DataValidation");
			int RowNum = 0;
			while ((nextLine = reader.readNext()) != null) {
				XSSFRow currentRow = sheet.createRow(RowNum++);
				for (int i = 0; i < nextLine.length; i++) {
					currentRow.createCell(i).setCellValue(nextLine[i]);
				}
			}
			fileOutputStream = new FileOutputStream(outputFleLoc);
			workBook.write(fileOutputStream);
			workBook.close();
		} catch (Exception e1) {
			// logger.error("StackTrace :",e);
			logger.info("\nFile \"" + inputFleLoc + "\" does not exist!");
		} finally {
			if (reader != null) {
				try {
					reader.close();
					fileOutputStream.close();
					logger.info("Done");
				} catch (IOException e2) {
					// e2.printStackTrace();
					logger.info("\nFile \"" + inputFleLoc + "\" can not be closed correctly!");
				}
			}
		}
	}

	public boolean fileTransferOrAvailableInLocalDirectory(String contPath) throws IOException {
		if (System.getProperty("user.home").replaceAll("\\\\", "/") != null
				&& !System.getProperty("user.home").replaceAll("\\\\", "/").equals("")) {
			String foldLoc = System.getProperty("user.home").replaceAll("\\\\", "/") + "/RESOURCES/";
			
			
			
			Path path = Paths.get(foldLoc);
			if (!Files.exists(path)) {
				try {
					// Target Directory is created now
					Files.createDirectories(path);

					// Copy the files from source directory to target directory
					String srcDir = contPath.trim() + "\\RESOURCES";
					String destDir = foldLoc.trim();
					FileUtils.copyDirectory(new File(srcDir), new File(destDir));
					return true;
				} catch (Exception e) {
					// fail to create directory
					Files.delete(path);
					logger.error("StackTrace :", e);
					return false;
				}
			} else {
				
				FileHandlingUtilities fhUtil = new FileHandlingUtilities();
				Properties propin = fhUtil.fnReadPropertyFile();
				String foldersName = propin.getProperty("FoldersToOverwrite");
				String flushFiles = propin.getProperty("ClearFolderContents");
				String folderToCreateNew = propin.getProperty("FoldersToCreateNew");
				String foldersToMove = propin.getProperty("FoldersInClientMachineToMove");
				String strMov[] = foldersToMove.split("<>");
				
				String prevFoldLoc=System.getProperty("user.home").replaceAll("\\\\", "/") +propin.getProperty("PreviousFolderLoc");
				String newFoldLoc=System.getProperty("user.home").replaceAll("\\\\", "/")+propin.getProperty("NewFolderLoc");
				System.out.println("prevFoldLoc:"+prevFoldLoc);
				System.out.println("newFoldLoc:"+newFoldLoc);
				File file = new File(foldLoc);
				if (file.isDirectory()) {
					for (File f : file.listFiles()) {
						if (!foldersName.equals("") && foldersName.contains(f.getName())) {
							String srcPath = contPath.trim() + "\\" + file.getName() + "\\" + f.getName();
							FileUtils.copyDirectory(new File(srcPath), new File(f.getAbsolutePath()));
						} else if (!flushFiles.equals("") && f.getName().equalsIgnoreCase(flushFiles)) {
							FileUtils.cleanDirectory(new File(f.getAbsolutePath()));
						} else if (!foldersToMove.equals("") && strMov != null && strMov[0] != null
								&& strMov[0].toString().contains(f.getName().toString())) {
							
							if(strMov[0].equalsIgnoreCase("DATAVAL")){
								File newFolderCheck=new File(newFoldLoc);
								if(!newFolderCheck.exists()){
									File fileDataVal= new File(prevFoldLoc);
									if (fileDataVal.isDirectory()) {
										for (File f1: fileDataVal.listFiles()) {
											System.out.println("f1.name:"+f1.getName());
										if(f1.getName().equalsIgnoreCase("EC")){
											String sourcePath = prevFoldLoc+ "/" + f1.getName();
											String destPath=f.getAbsolutePath()+"/EC";
											FileUtils.copyDirectory(new File(sourcePath), new File(destPath));
										}
									}
								}
								}
							}
							String srcFolder[] = strMov[1].toString().split(",");
							String desFolder[] = strMov[2].toString().split(",");
							if (srcFolder != null && desFolder != null && srcFolder.length == desFolder.length) {
								for (int i = 0; i < srcFolder.length; i++) {
									if (new File(f.getAbsolutePath() + File.separator + srcFolder[i].toString()).exists()) {
										FileUtils.moveDirectory(
												new File(f.getAbsolutePath() + File.separator + srcFolder[i].toString()),
												new File(f.getAbsolutePath() + File.separator + desFolder[i].toString()));
									} else {
										break;
									}
								}
							}
							
							/*if (strMov[3] != null && !strMov[3].toString().equals("")) {
								String srcPath = contPath.trim() + "\\" + file.getName() + "\\" + f.getName() + "\\"
										+ strMov[3].toString();
								String desPath = f.getAbsolutePath() + File.separator + strMov[3].toString();
								if (!new File(desPath).exists()) {
									FileUtils.copyDirectory(new File(srcPath), new File(desPath));
								}
							}*/
							
							if (strMov[3] != null && !strMov[3].toString().equals("")) {
								String srcPath = prevFoldLoc+ "/" + strMov[3].toString();
								String desPath = f.getAbsolutePath() + File.separator + strMov[3].toString();
								if (!new File(desPath).exists()) {
									FileUtils.copyDirectory(new File(srcPath), new File(desPath));
								}
							
							}
							
							// - Ketan
							if (strMov[4] != null && !strMov[4].toString().equals("")) {
								String srcPath = prevFoldLoc+ "/" + strMov[4].toString();
								String desPath = f.getAbsolutePath() + File.separator + strMov[4].toString();
								if (!new File(desPath).exists()) {
									FileUtils.copyDirectory(new File(srcPath), new File(desPath));
								}
							}
							//LMS Harika
							if (strMov[5] != null && !strMov[5].toString().equals("")) {
								String srcPath = prevFoldLoc+ "/" +strMov[5].toString();
								String desPath = f.getAbsolutePath() + File.separator + strMov[5].toString();
								if (!new File(desPath).exists()) {
									FileUtils.copyDirectory(new File(srcPath), new File(desPath));
								}
							}
							String checkCompanyIDFolder=System.getProperty("user.home").replaceAll("\\\\", "/")+propin.getProperty("ToDeleteComanyIDFolder");
							File fil=new File(checkCompanyIDFolder);
							if (fil.isDirectory()) {
								for (File f2: fil.listFiles()) { 
									System.out.println("f1.name:"+f2.getName());
									if(f2.getName().equalsIgnoreCase("SFPART007209")){
										FileUtils.deleteDirectory(f2);
									}
								}
							}
							
							
						} else if (!folderToCreateNew.equals("") && folderToCreateNew.contains(f.getName())) {
							String srcPath = contPath.trim() + "\\" + file.getName() + "\\" + f.getName();
							FileUtils.copyDirectory(new File(srcPath), new File(f.getAbsolutePath()));
						}
					}

					if (!folderToCreateNew.equals("")) {
						StringTokenizer folderToCreate = new StringTokenizer(folderToCreateNew, ",");
						while (folderToCreate.hasMoreTokens()) {
							String folderPath = folderToCreate.nextToken().trim();
							// String srcPath = contPath.trim() + "\\RESOURCES"
							// + "\\" + folderToCreateNew; - Original
							String srcPath = contPath.trim() + "\\RESOURCES" + "\\" + folderPath; // -
																									// Ketan
							String desPath = foldLoc + "\\" + folderPath;
							FileUtils.copyDirectory(new File(srcPath), new File(desPath));
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	public static void copyFileUsingStream(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
		}
	}

	public static void main(String[] args) {
		FileHandlingUtilities fhUtil = new FileHandlingUtilities();
		try {
			fhUtil.fnConversionCSVtoXLSX1(
					"C:/Users/siva.k.thiyagarajan/Automation_Tool/Siva_Work/15122016/Compinfotestdata_v1.0_DONOTOPEN.csv",
					"C:/Users/siva.k.thiyagarajan/Automation_Tool/Siva_Work/15122016/Compinfotestdata_v1.0_DONOTOPEN.xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}