package sample.util;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Excel 读取（2007+新格式）
 *
 * @author chengesheng
 * @date 2012-4-27 下午03:39:01
 * @note PoiExcel2k7Helper
 */
public class PoiExcel2k7Helper extends PoiExcelHelper {
    /**
     * 获取sheet列表
     */
    public ArrayList<String> getSheetList(String filePath) {
        ArrayList<String> sheetList = new ArrayList<String>(0);
        try {
            XSSFWorkbook wb = new XSSFWorkbook(getFileInputStream(filePath));
            Iterator<Sheet> iterator = wb.iterator();
            while (iterator.hasNext()) {
                sheetList.add(iterator.next().getSheetName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sheetList;
    }

    /**
     * 读取Excel文件内容
     */
    public ArrayList<ArrayList<String>> readExcel(String filePath, int sheetIndex, String rows, String columns) {
        ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
        try {
            XSSFWorkbook wb = new XSSFWorkbook(getFileInputStream(filePath));
            XSSFSheet sheet = wb.getSheetAt(sheetIndex);

            dataList = readExcel(sheet, rows, getColumnNumber(sheet, columns));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * 读取Excel文件内容
     */
    public ArrayList<ArrayList<String>> readExcel(String filePath, int sheetIndex, String rows, int[] cols) {
        ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
        try {
            XSSFWorkbook wb = new XSSFWorkbook(getFileInputStream(filePath));
            XSSFSheet sheet = wb.getSheetAt(sheetIndex);

            dataList = readExcel(sheet, rows, cols);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    private InputStream getFileInputStream(String filePath) {
        try {
            return new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
//        return Main.class.getResourceAsStream(filePath);
    }
}