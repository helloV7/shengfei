package sample.util;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by chenweiqi on 2017/4/14.
 */
public class FileUtil {

    public static void TableViewDataToExcel(TableView tableView, File saveFile) throws NoSuchFieldException, IllegalAccessException, IOException {
        if (saveFile == null) {
            throw new RuntimeException("save File can not be null");
        }
        if (!saveFile.exists()) {
            saveFile.getParentFile().mkdirs();
        }
        ObservableList<TableColumn> columns = tableView.getColumns();
        ObservableList<Object> items = tableView.getItems();
//        Field[] fields = null;
        Workbook workbook = null;
        String[] titleNames = null;
        if (saveFile.getName().contains(".xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (saveFile.getName().contains(".xls")) {
            workbook = new HSSFWorkbook();
        }

        for (int i = 0; i < columns.size(); i++) {

        }

//        if (items!=null&&items.size()!=0){
//            fields= items.get(0).getClass().getDeclaredFields();
//            for (Field f :
//                    fields) {
//                f.setAccessible(true);
//            }
//        }

        Sheet sheet = workbook.createSheet();
        for (int i = 0, iMax = items.size() + 1; i < iMax; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0, jMax = columns.size(); j < jMax; j++) {
                Cell cell = row.createCell(j);

                if (i == 0) {
                    cell.setCellValue(columns.get(j).getText());
                    continue;
                }

                Object rowObj = items.get(i - 1);
                String columnFieldName = ((PropertyValueFactory) columns.get(j).getCellValueFactory()).getProperty();
                Field field = rowObj.getClass().getDeclaredField(columnFieldName);
                field.setAccessible(true);
                String content = (String) field.get(rowObj);
                cell.setCellValue(content);

            }
        }

        FileOutputStream outputStream = new FileOutputStream(saveFile);
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();


    }


    private Workbook getWorkBook(String path) {
        if (path.endsWith(".xlsx")) {


            return new XSSFWorkbook();
        } else if (path.endsWith(".xls")) {
            return new HSSFWorkbook();
        }
        return null;
    }


}