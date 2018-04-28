package utility;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.RowIdLifetime;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
 
import config.Constants;
import executionEngine.DriverScript;
    
public class ExcelUtils 
{
	private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static org.apache.poi.ss.usermodel.Cell Cell;
    private static XSSFRow Row;
    //private static XSSFRow Row;
 
    public static void setExcelFile(String Path) throws Exception 
    {
    	try 
                	{
                		FileInputStream ExcelFile = new FileInputStream(Path);
                		ExcelWBook = new XSSFWorkbook(ExcelFile);
                	} 
                	catch (Exception e)
                	{
                		Log.error("Class Utils | Method setExcelFile | Exception desc : "+e.getMessage());
                		DriverScript.bResult = false;
                	}
            	}
 
            public static String getCellData(int RowNum, int ColNum, String SheetName ) throws Exception
            {
                try
                {
                	ExcelWSheet = ExcelWBook.getSheet(SheetName);
                   	Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
                    String CellData = Cell.getStringCellValue();
                    return CellData;
                 }
                catch (Exception e)
                {
                     Log.error("Class Utils | Method getCellData | Exception desc : "+e.getMessage());
                     DriverScript.bResult = false;
                     return"";
                }
            }
 
        	public static int getRowCount(String SheetName)
        	{
        		int iNumber=0;
        		try 
        		{
        			ExcelWSheet = ExcelWBook.getSheet(SheetName);
        			iNumber=ExcelWSheet.getLastRowNum()+1;
        		} 
        		catch (Exception e)
        		{
        			Log.error("Class Utils | Method getRowCount | Exception desc : "+e.getMessage());
        			DriverScript.bResult = false;
        		}
        		return iNumber;
        		}
 
        	public static int getRowContains(String sTestCaseName, int colNum,String SheetName) throws Exception
        	{
        		int iRowNum=0;	
        		try 
        		{
        		    //ExcelWSheet = ExcelWBook.getSheet(SheetName);
        			int rowCount = ExcelUtils.getRowCount(SheetName);
        			for (; iRowNum<rowCount; iRowNum++)
        			{
        				if  (ExcelUtils.getCellData(iRowNum,colNum,SheetName).equalsIgnoreCase(sTestCaseName))
        				{
        					break;
        				}
        			}       			
        		} 
        		catch (Exception e)
        		{
        			Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
        			DriverScript.bResult = false;
        		}
        		return iRowNum;
        	}
 
        	public static int getTestStepsCount(String SheetName, String sTestCaseID, int iTestCaseStart) throws Exception{
        		try {
	        		for(int i=iTestCaseStart;i<=ExcelUtils.getRowCount(SheetName);i++){
	        			if(!sTestCaseID.equals(ExcelUtils.getCellData(i, Constants.Col_TestCaseID, SheetName))){
	        				int number = i;
	        				return number;      				
	        				}
	        			}
	        		ExcelWSheet = ExcelWBook.getSheet(SheetName);
	        		int number=ExcelWSheet.getLastRowNum()+1;
	        		return number;
        		} catch (Exception e){
        			Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
        			DriverScript.bResult = false;
        			return 0;
                }
        	}
 
        	@SuppressWarnings("static-access")
        	public static void setCellData(String Result,  int RowNum, int ColNum, String SheetName) throws Exception    {
                   try{
 
                	   ExcelWSheet = ExcelWBook.getSheet(SheetName);
                       Row  = ExcelWSheet.getRow(RowNum);
                       Cell = Row.getCell(ColNum);
                       if (Cell == null) {
                    	   Cell = Row.createCell(ColNum);
                    	   Cell.setCellValue(Result);
                        } else {
                            Cell.setCellValue(Result);
                        }
                         FileOutputStream fileOut = new FileOutputStream(Constants.Path_TestData);
                         ExcelWBook.write(fileOut);
                         //fileOut.flush();
                         fileOut.close();
                         ExcelWBook = new XSSFWorkbook(new FileInputStream(Constants.Path_TestData));
                     }catch(Exception e){
                    	 DriverScript.bResult = false;
 
                     }
                }
        	
        	@SuppressWarnings("deprecation")
			public static String[][] getExcelData(String excelLocation , String sheetName)
        	{
				try
				{
					String dataSets[][] = null;
					FileInputStream file = new FileInputStream(new File(excelLocation));
					XSSFWorkbook workbook = new XSSFWorkbook(file);
					XSSFSheet sheet = workbook.getSheet(sheetName);
					int totalRow = sheet.getLastRowNum()+1;
					int totalColumn = sheet.getRow(0).getLastCellNum();
					dataSets = new String[totalRow-1][totalColumn];
					
					Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = sheet.iterator();
					int i=0;
					int t=0;
					
					while(rowIterator.hasNext())
					{
						org.apache.poi.ss.usermodel.Row row = rowIterator.next();
						if(i++ !=0)
						{
							int k=t;
							t++;
							
							Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();
							int j=0;
							while(cellIterator.hasNext())
							{
								 org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
								 
								 dataSets[k][j++]= cell.getStringCellValue();
								 System.out.println(cell.getStringCellValue());
								 /*switch(cell.)
								 {
								 case Cell.CELL_TYPE_NUMERIC:
								 	dataSets[k][j++]= cell.getStringCellValue();
								 	System.out.println(cell.getNumericCellValue());
								 	break;
								 case Cell.CELL_TYPE_STRING:
									 dataSets[k][j++]= cell.getStringCellValue();
									 System.out.println(cell.getNumericCellValue());
									 break;
								 case Cell.CELL_TYPE_BOOLEAN:
									 dataSets[k][j++]= cell.getStringCellValue();
									 System.out.println(cell.getNumericCellValue());
									 break;
								 case Cell.CELL_TYPE_FORMULA:
									 dataSets[k][j++]= cell.getStringCellValue();
									 System.out.println(cell.getNumericCellValue());
									 break;
								 }*/
							}
						
						}	
						
						
					}
					System.out.println("");
					file.close();
					return dataSets;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
        		return null;
        		
        	}

        	public static void main(String args[])
        	{
        		String excelLocation = "./src/dataEngine/DataEngine.xlsx";
        		String sheetName = "Test Cases";
        		ExcelUtils excel = new ExcelUtils();
        		excel.getExcelData(excelLocation, sheetName);
        	}
}

