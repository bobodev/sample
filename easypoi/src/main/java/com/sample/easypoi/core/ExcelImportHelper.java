package com.sample.easypoi.core;

import cn.afterturn.easypoi.excel.annotation.Excel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel 导入工具类
 */
public class ExcelImportHelper {
    public static <T> List<T> transferToList(File file, Class<T> clazz, ExcelImportParam param) throws Exception {
        List<T> list = new ArrayList<>();

        Map<String, Object> replaceMap = getReplaceMap(clazz);

        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        int startRowNum = param.getStartRowNum();
        for(int i=startRowNum;i<=lastRowNum;i++){
            Row row = sheet.getRow(i);
            T o = transferRowToClass(row,clazz,replaceMap);
            list.add(o);
        }
        return list;
    }

    private static  Map<String, Object> getReplaceMap(Class<?> clazz) {
        Map<String, Object> replaceMap = new HashMap<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            if(declaredField.isAnnotationPresent(Excel.class)){
                Excel excel = declaredField.getAnnotation(Excel.class);
                String[] replace = excel.replace();
                if(replace.length>0){
                    for (String s : replace) {
                        String[] split = s.split("_");
                        replaceMap.put(split[0],split[1]);
                    }
                }
            }
        }
        return replaceMap;
    }


    private static <T> T transferRowToClass(Row row, Class<T> clazz,Map<String, Object> replaceMap) throws Exception {
        T o = clazz.newInstance();
        Field[] declaredFields = clazz.getDeclaredFields();
        int j=0;
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            if(declaredField.isAnnotationPresent(Excel.class)){
                declaredField.setAccessible(true);
                Object value = getVal(row.getCell(j));
                Excel excel = declaredField.getAnnotation(Excel.class);
                String[] replace = excel.replace();
                if(replace.length>0){
                   value = replaceMap.get(value.toString());
                }
                String format = excel.format();
                if(!StringUtils.isEmpty(format)){
                    value = new SimpleDateFormat(format).parse(value.toString());
                }
                declaredField.set(o,getValByFieldType(value,declaredField.getType()));
                j++;
            }
        }
        return o;
    }

    private static Object getValByFieldType(Object o, Class<?> type) throws Exception{
        if(o==null){
            return null;
        }
        if(type.getName().equals(Integer.class.getTypeName())){
            return Integer.valueOf(o.toString());
        }else if(type.getName().equals(Long.class.getTypeName())){
            return Long.valueOf(o.toString());
        }else if(type.getName().equals(Long.class.getTypeName())){
            return Long.valueOf(o.toString());
        }else if(type.getName().equals(Float.class.getTypeName())){
            return Float.valueOf(o.toString());
        }else if(type.getName().equals(Double.class.getTypeName())){
            return Double.valueOf(o.toString());
        }else if(type.getName().equals(Date.class.getTypeName())){
            //日期类型已经在外面处理,如果没有处理，采用默认处理方式
            return o;
//            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(o.toString());
        }
        return o.toString();
    }

    private static Object getVal(Cell cell) {
        if(cell.getCellType() == CellType.STRING.getCode()){
            return cell.getStringCellValue();
        }else if(cell.getCellType() == CellType.NUMERIC.getCode()){
            return cell.getNumericCellValue();
        }else if(cell.getCellType() == CellType.BOOLEAN.getCode()){
            return cell.getBooleanCellValue();
        }
        return null;
    }
}
