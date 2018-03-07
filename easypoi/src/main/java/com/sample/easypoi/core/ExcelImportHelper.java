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
    /**
     * excel 转化为 List 集合
     *
     * @param file excel文件
     * @param clazz 类名
     * @param param 参数
     * @param <T> 范型，用于指定类
     * @return List 集合
     * @throws Exception 异常类
     */
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

    /**
     * Row 转化为对象
     * @param row
     * @param clazz
     * @param replaceMap
     * @param <T>
     * @return
     * @throws Exception
     */
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

    /**
     * 私有方法 用来处理 注解replace
     * @param clazz
     * @return
     */
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


    /**
     * 获取指定类型的值
     *
     * @param o 原始值
     * @param type 类型
     * @return 指定类型的值
     * @throws Exception 异常信息
     */
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

    /**
     * 获取cell的值
     *
     * @param cell
     * @return
     */
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
