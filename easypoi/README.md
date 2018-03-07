---
title: 2018-3-7 excel导入导出示例分享 
grammar_cjkRuby: true
---

[toc]

本文主要描述excel导入导出相关内容

项目git地址 https://github.com/gatdevelop/sample.git

## 1. 概述
项目主要基于easypoi对开发过程中常见的导入导出场景做了一些demo，便于项目中进行快速开发。但是在实践过程中发现easypoi很多场景支持的并不是很好，模版这块标签语言不太丰富。后面会考虑结合jxl标签进行更丰富的模版支持。
## 2. 基础示例
### 2.1. 导出
* 项目代码【ExportTest.java】
* 步骤 1、准备数据 2、构造数据和模版 3、导出
### 2.1.1. 标准模版导出[ExportTest]
```
    /**
     * 模版导出
     *
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        //step1 准备数据
        List<Student> students = new DataService().loadData(100);
        List<String> headers = new ArrayList<>(Arrays.asList("学生姓名", "学生性别", "出生日期"));
        //step2 构造数据和模版
        Map<String, Object> map = new HashMap<>();
        map.put("headers", headers);
        map.put("students", students);
        map.put("title", "模版导出-学生数据");
        String templateUrl = RESOURCE_PATH + "/template/export_01.xlsx";
        //step3 导出
        TemplateExportParams params = new TemplateExportParams(
                templateUrl);
        params.setColForEach(true);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        ExcelExportHelper.exportCommon(workbook, RESOURCE_PATH + "/export/模版导出(test01_export).xlsx");
    }
```
### 2.1.2. 模版导出-自定义列[ExportTest]
```
    /**
     * 模版导出-自定义列-65535
     *
     * @throws Exception
     */
    @Test
    public void test02() throws Exception {
        //step1 准备数据
        List<Student> students = new DataService().loadData(65535);
        //构建动态列，注意false代表需要导出，true代表不需要导出
        boolean needName = false;
        boolean needSex = false;
        boolean needBirthday = false;

        //step2 构造数据和模版
        Map<String, Object> map = new HashMap<>();
        map.put("students", students);
        map.put("needName", needName);
        map.put("needSex", needSex);
        map.put("needBirthday", needBirthday);
        map.put("title", "模版导出-自定义列-学生数据");
        String templateUrl = RESOURCE_PATH + "/template/export_02.xlsx";
        //step3 导出
        TemplateExportParams params = new TemplateExportParams(
                templateUrl);
        params.setColForEach(true);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        ExcelExportHelper.exportCommon(workbook, RESOURCE_PATH + "/export/模版导出-自定义列-65535(test02_export).xlsx");
    }

```
### 2.1.3. 模版导出-大数据量[ExportTest]
大数据量导出暂时使用正常模版导出的方法，经过测试，65535条数据大约需要10s，50万条数据需要约57s,业务上一般做异步处理，可以接受。而且07版本以后的行数最大支持在100万左右。
### 2.1.4. 模版导出-多sheet[ExportTest]
```

    /**
     * 模版导出-多sheet导出
     *
     * @throws Exception
     */
    @Test
    public void test04() throws Exception {
        //step1 准备数据
        List<Student> students = new DataService().loadData(1000);
        //构建动态列
        boolean needName = false;
        boolean needSex = true;
        boolean needBirthday = false;

        //step2 构造数据和模版
        Map<Integer, Map<String, Object>> map = new HashMap<>();
        Map<String, Object> mapTemp1 = new HashMap<>();
        mapTemp1.put("students", students);
        mapTemp1.put("needName", needName);
        mapTemp1.put("needSex", needSex);
        mapTemp1.put("needBirthday", needBirthday);
        mapTemp1.put("title", "模版导出-多sheet");
        mapTemp1.put("title2", "模版导出-多sheet2");
        map.put(0, mapTemp1);
        map.put(1, mapTemp1);
        String templateUrl = RESOURCE_PATH + "/template/export_03.xlsx";
        Integer[] sheetNum = new Integer[]{0, 1};
        //step3 导出
        TemplateExportParams params = new TemplateExportParams(
                templateUrl);
        params.setSheetNum(sheetNum);
        params.setColForEach(true);
        //解决源码中bug，使用自己封装的多sheet导出
        Workbook workbook = ExcelExportHelper.exportExcel(params, map);
        ExcelExportHelper.exportCommon(workbook, RESOURCE_PATH + "/export/模版导出-多sheet(test04_export).xlsx");
    }
```
<div style="color:red">注意：easypoi提供的多sheet导出存在bug，源码中缺少对style的处理，报空指针。建议使用封装候的新的api： ExcelExportHelper.exportExcel</div>

### 2.2. 导入
* 项目代码【ImportTest.java】
### 2.2.1. 标准模版导入[ImportTest.java]
```
    /**
     * 模版导入
     *
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        File file = new File(RESOURCE_PATH + "/import/模版导入(test01_import).xlsx");
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<Student> list = ExcelImportUtil.importExcel(
                file, Student.class, params);
        System.out.println(list.size());
    }
```
<div style="color:red">注意：easypoi默认提供的方式存在一些bug，当一行数据的第一列数据为空的时候就会跳过这一行，不会处理。最好使用2.2.3. 中的示例程序</div>

### 2.2.2. 模版导入-validation验证[ImportTest.java]
```
    /**
     * 模版导入-验证
     *
     * @throws Exception
     */
    @Test
    public void test02() throws Exception {
        File file = new File(RESOURCE_PATH + "/import/模版导入-验证(test02_import).xlsx");
        ImportParams params = new ImportParams();
        params.setNeedVerfiy(true);
        ExcelImportResult<ExcelVerifyEntity> excelImportResult = ExcelImportUtil.importExcelMore(file, ExcelVerifyEntity.class, params);
        if (excelImportResult.isVerfiyFail()) {
            Workbook failWorkbook = excelImportResult.getFailWorkbook();
            FileOutputStream fos = new FileOutputStream(new File(RESOURCE_PATH + "/import/模版导入-验证-失败数据(test02_import).xlsx"));
            failWorkbook.write(fos);
            fos.flush();
            fos.close();
        }
    }
```
<div style="color:red">注意：easypoi默认提供的方式存在一些bug，当一行数据的第一列数据为空的时候就会跳过这一行，不会处理。最好使用2.2.3. 中的示例程序</div>

### 2.2.3. 模版导入-多线程处理业务验证[ImportProgressBarTest]
多线程处理easypoi没有很好的支持。于是就自己实现了。
```
 @Test
    public void test01() throws Exception {
        dataService.judgeFinish();

        long start=System.currentTimeMillis();

        File file = new File(RESOURCE_PATH + "/import/ProgressBar.xlsx");
        //数据转化
        ExcelImportParam excelImportParam = new ExcelImportParam();
        excelImportParam.setStartRowNum(2);
        List<Student> students = ExcelImportHelper.transferToList(file, Student.class, excelImportParam);
        ProgressBar.setTotal(students.size());
        List<List<Student>> sublist = ExcelCommonUtil.sublist(students, 50);
        List<Future<ExcelImportResult<Student>>> futures = new ArrayList<>();
        for (List<Student> studentList : sublist) {
            futures.add(dataService.processImport(studentList));
            Thread.sleep(10);
        }
        ExcelImportResult excelImportResult = ExcelCommonUtil.dealFutureResult(futures);

        long end=System.currentTimeMillis();
        long l = end - start;
        System.out.println("导入数据共使用时间 " + l+" 秒");

        if (excelImportResult.isVerifyFail()) {
            this.exportErrorWorkBook(excelImportResult.getFailList());
        }
    }
```

说明：

*  dataService.judgeFinish();是用来监测进度条，可以忽略
*  ProgressBar相关的行用来处理进度条

主要使用步骤：
1. 获取要处理的文件或文件流
2. 构造ExcelImportParam，其中excelImportParam.setStartRowNum可以指定开始的行数，包含该行，从0开始计算
3. ExcelImportHelper.transferToList用来转化文件内容到List集合
4. 进行数据分割，来决定处理的线程数量
5. 根据需要处理是否需要导出失败的记录对应的excel

### 2.2.4. 模版导入-实时进度条[ProgressBarBase]
1. 建议为每个业务的进度条单独建立一个类继承ProgressBarBase
2. ProgressBarBase提供的相关api，如获取总时间、获取已处理进度百分比等
3. 参考2.2.3. 程序进行代码处理

## 3. 参考文档
[EasyPoi教程](http://easypoi.mydoc.io/)