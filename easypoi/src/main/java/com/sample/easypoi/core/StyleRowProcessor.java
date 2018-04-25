package com.sample.easypoi.core;

import net.sf.jxls.processor.RowProcessor;
import net.sf.jxls.transformer.Row;
import net.sf.jxls.transformer.RowCollection;

import java.util.List;
import java.util.Map;

public class StyleRowProcessor implements RowProcessor {

    String collectionName;
    String styleCellLabel = "customRow";

    public StyleRowProcessor(String collectionName) {
        this.collectionName = collectionName;
    }

    public void processRow(Row row, Map namedCells) {
        // check if processed row has a parent row
        int maxColNum = row.getSheet().getMaxColNum();
        List cells = row.getCells();
        System.out.println("JSON.toJSONString(cells) = " +cells);
        if (row.getParentRow() != null) {
            // Processed row has parent row. It means we are processing some collection item
            RowCollection rowCollection = row.getParentRow().getRowCollectionByCollectionName(collectionName);
            Object iterateObject = rowCollection.getIterateObject();
            System.out.println("iterateObject = " + iterateObject);
        }
    }
}
            