
```
1. 概述
2. 基础示例
2.1. 导出
2.1.1. 标准模版导出[ExportTest]
2.1.2. 模版导出-自定义列[ExportTest]
2.1.3. 模版导出-大数据量[ExportTest]
2.1.4. 模版导出-多sheet[ExportTest]
2.2. 导入
2.2.1. 标准模版导入[ImportTest.java]
2.2.2. 模版导入-validation验证[ImportTest.java]
2.2.3. 模版导入-多线程处理业务验证[ImportProgressBarTest]
2.2.4. 模版导入-实时进度条[ProgressBar、ProgressBarService]
2.3. 其他
2.3.1. 获取表头
2.3.2. 自定义列导入
2.3.3. 基于jxls的自定义列导出
2.3.4. 获取最大行
2.3.5. 限制系统同时处理excel的大小[ProcessPower]
3. 参考文档
```

本文主要描述excel导入导出相关内容

项目git地址 https://github.com/gatdevelop/sample.git

## 1. 概述

* 项目主要基于easypoi对开发过程中常见的导入导出场景做了一些demo，便于项目中进行快速开发。但是在实践过程中发现easypoi很多场景支持的并不是很好，模版这块标签语言不太丰富。后面会考虑结合jxl标签进行更丰富的模版支持。
* 使用步骤

1. maven添加依赖

```
        <dependency>
            <groupId>cn.afterturn</groupId>
            <artifactId>easypoi-base</artifactId>
            <version>3.1.0</version>
        </dependency>
        <dependency>
            <groupId>cn.afterturn</groupId>
            <artifactId>easypoi-web</artifactId>
            <version>3.1.0</version>
        </dependency>
        <dependency>
            <groupId>cn.afterturn</groupId>
            <artifactId>easypoi-annotation</artifactId>
            <version>3.1.0</version>
        </dependency>
        <dependency>
            <groupId>net.sf.jxls</groupId>
            <artifactId>jxls-core</artifactId>
            <version>1.0.5</version>
        </dependency>

```

2. 拷贝 com.sample.easypoi.core下面的类到项目中
3. 参考示例进行使用

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
        String progressBarCode = "progressBar";
        progressBarService.createProgressBarByCode(progressBarCode);

        dataService.judgeFinish(progressBarCode);

        File file = new File(RESOURCE_PATH + "/import/ProgressBar.xlsx");
        //数据转化
        ExcelImportParam excelImportParam = new ExcelImportParam();
        excelImportParam.setStartRowNum(2);
        List<Student> students = ExcelImportHelper.transferToList(file, Student.class, excelImportParam);

        ExcelImportResult<Student> excelImportResult = new ExcelImportResult();
        List<Student> failList = excelImportResult.getFailList();
        //基本校验
        students.stream().forEach(o->{
            String errorMsg = PoiValidationUtil.validation(o, null);
            if(!StringUtils.isEmpty(errorMsg)){
                o.setErrorMsg(errorMsg);
                failList.add(o);
            }
        });

        if(excelImportResult.isVerifyFail()){//如果失败直接返回
            return;
        }

        progressBarService.setTotal(progressBarCode,students.size());
        List<List<Student>> sublist = ExcelCommonUtil.sublist(students, 2);
        List<Future<ExcelImportResult<Student>>> futures = new ArrayList<>();
        for (List<Student> studentList : sublist) {
            futures.add(dataService.processImport(studentList,progressBarCode));
            Thread.sleep(20);
        }

        excelImportResult = ExcelCommonUtil.dealFutureResult(futures);
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
1. ProgressBar.createProgressBarByCode创建进度条
2. ProgressBar提供的相关api，如获取总时间、获取已处理进度百分比等
3. 参考2.2.3. 程序进行代码处理

### 2.3.1. 其他-获取表头[ExcelHeaderTest]
获取表头集合

```
    /**
     * 获取表头List
     *
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        File file = new File(RESOURCE_PATH + "/import/模版导入(test01_import).xlsx");
        ExcelImportParam params = new ExcelImportParam();
        params.setHeaderRowNum(1);
        List<String> headerRow = ExcelImportHelper.getHeaderRow(file, params);
        System.out.println("headerRow = " + headerRow);
    }
```
### 2.3.2. 其他-自定义列导入[ImportCustomColumnTest]

```
     /**
       * 自定义列导入，excel数据解析类型为 List<Map<String,Object>> key为header，value为对应的值
       * 
       * @throws Exception
       */
      @Test
      public void test01() throws Exception {
          File file = new File(RESOURCE_PATH + "/import/ProgressBar.xlsx");
          boolean b = ProcessPower.canProcess(file);
          if (!b) {
              throw new Exception("系统繁忙，请稍后再试");
          }
  
          try{
              String progressBarCode = "progressBar";
  
              progressBarService.createProgressBarByCode(progressBarCode);
  
              dataService.judgeFinish(progressBarCode);
  
              long start = System.currentTimeMillis();
  
              //数据转化
              ExcelImportParam excelImportParam = new ExcelImportParam();
              excelImportParam.setStartRowNum(1);
  
              //获取headerRow
              ExcelImportParam paramForHeaderRow = new ExcelImportParam();
              paramForHeaderRow.setHeaderRowNum(0);
              List<String> headerRows = ExcelImportHelper.getHeaderRow(file, paramForHeaderRow);
  
              excelImportParam.setHeaderRows(headerRows);
              List<Map> mapList = ExcelImportHelper.transferToList(file, Map.class, excelImportParam);
  
              progressBarService.setTotal(progressBarCode, mapList.size());
              List<List<Map>> sublist = ExcelCommonUtil.sublist(mapList, 20);
              List<Future<ExcelImportResult<Map>>> futures = new ArrayList<>();
              for (List<Map> tempList : sublist) {
                  futures.add(dataService.processImport2(tempList, progressBarCode));
                  Thread.sleep(20);
              }
              ExcelImportResult<Map> excelImportResult = ExcelCommonUtil.dealFutureResult(futures);
              if (excelImportResult.isVerifyFail()) {
                  //导出错误的数据，参考ImportProgressBarTest.exportErrorWorkBook
              }
          }catch (Exception e){
              e.printStackTrace();
          }finally {
              ProcessPower.reduce(new FileInputStream(file).available());
          }
      }
``
### 2.3.3 其他-基于jxls的自定义列导出[ImportCustomColumnTest]

```
        /**
         * 获取最大行
         *
         * @throws Exception
         */
        @Test
        public void test01() throws Exception {
            ....省略代码
            
            //导出自定义列示例 基于jxls
            Map<String, Object> map = new HashMap<>();
            headerRows.add("错误信息");
            map.put("headers", headerRows);
            map.put("list", mapList);
            String templateUrl = RESOURCE_PATH + "/template/export_06.xlsx";
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateUrl, map, RESOURCE_PATH + "/export/自定义列导出_ImportCustomColumnTest(test01_export).xlsx");

        }
```

### 2.3.4. 其他-获取最大行[LastRowNumTest]

```
    /**
     * 获取最大行
     *
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        File file = new File(RESOURCE_PATH + "/import/模版导入(test01_import).xlsx");
        ExcelImportParam params = new ExcelImportParam();
        int lastRowNum = ExcelImportHelper.getLastRowNum(file, params);
        System.out.println("lastRowNum = " + lastRowNum);
    }
    
```

2.3.5. 限制系统同时处理excel的大小[ProcessPower]
参考示例 2.3.2. 其他-自定义列导入

说明：

1、建议生产环境部署2G内存时，大小设置为3.5M

2、调用 ProcessPower.canProcess(file) 判断是否可以处理文件，支持参数为流、文件路径、文件

3、调用 ProcessPower.reduce(new FileInputStream(file).available()) 释放处理空间，支持参数为流、文件路径、文件

4、注意调用ProcessPower.canProcess(file)后逻辑用try...catch...finally,避免因逻辑而导致的没有及时释放内存空间


## 3. 参考文档
[EasyPoi教程](http://easypoi.mydoc.io/)