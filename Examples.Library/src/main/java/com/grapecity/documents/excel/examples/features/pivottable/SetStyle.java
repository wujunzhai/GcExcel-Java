package com.grapecity.documents.excel.examples.features.pivottable;

import com.grapecity.documents.excel.*;
import com.grapecity.documents.excel.examples.ExampleBase;

import java.util.GregorianCalendar;

public class SetStyle extends ExampleBase {

    @Override
    public void execute(Workbook workbook) {

        Object sourceData = new Object[][]{
                {"Order ID", "Product", "Category", "Amount", "Date", "Country"},
                {1, "Bose 785593-0050", "Consumer Electronics", 4270, new GregorianCalendar(2018, 0, 6), "United States"},
                {2, "Canon EOS 1500D", "Consumer Electronics", 8239, new GregorianCalendar(2018, 0, 7), "United Kingdom"},
                {3, "Haier 394L 4Star", "Consumer Electronics", 617, new GregorianCalendar(2018, 0, 8), "United States"},
                {4, "IFB 6.5 Kg FullyAuto", "Consumer Electronics", 8384, new GregorianCalendar(2018, 0, 10), "Canada"},
                {5, "Mi LED 40inch", "Consumer Electronics", 2626, new GregorianCalendar(2018, 0, 10), "Germany"},
                {6, "Sennheiser HD 4.40-BT", "Consumer Electronics", 3610, new GregorianCalendar(2018, 0, 11), "United States"},
                {7, "Iphone XR", "Mobile", 9062, new GregorianCalendar(2018, 0, 11), "Australia"},
                {8, "OnePlus 7Pro", "Mobile", 6906, new GregorianCalendar(2018, 0, 16), "New Zealand"},
                {9, "Redmi 7", "Mobile", 2417, new GregorianCalendar(2018, 0, 16), "France"},
                {10, "Samsung S9", "Mobile", 7431, new GregorianCalendar(2018, 0, 16), "Canada"},
                {11, "OnePlus 7Pro", "Mobile", 8250, new GregorianCalendar(2018, 0, 16), "Germany"},
                {12, "Redmi 7", "Mobile", 7012, new GregorianCalendar(2018, 0, 18), "United States"},
                {13, "Bose 785593-0050", "Consumer Electronics", 1903, new GregorianCalendar(2018, 0, 20), "Germany"},
                {14, "Canon EOS 1500D", "Consumer Electronics", 2824, new GregorianCalendar(2018, 0, 22), "Canada"},
                {15, "Haier 394L 4Star", "Consumer Electronics", 6946, new GregorianCalendar(2018, 0, 24), "France"},
        };

        IWorksheet worksheet = workbook.getWorksheets().get(0);
        worksheet.getRange("A20:F35").setValue(sourceData);
        IPivotCache pivotcache = workbook.getPivotCaches().create(worksheet.getRange("A20:F35"));
        IPivotTable pivottable = worksheet.getPivotTables().add(pivotcache, worksheet.getRange("A1"), "pivottable1");
        worksheet.getRange("D21:D35").setNumberFormat("$#,##0.00");

        //set pivot table report layout as Tabular
        pivottable.setRowAxisLayout(LayoutRowType.TabularRow);

        //config pivot table's fields
        IPivotField field_Category = pivottable.getPivotFields().get("Category");
        field_Category.setOrientation(PivotFieldOrientation.RowField);

        IPivotField field_Product = pivottable.getPivotFields().get("Product");
        field_Product.setOrientation(PivotFieldOrientation.RowField);

        IPivotField field_Amount = pivottable.getPivotFields().get("Amount");
        field_Amount.setOrientation(PivotFieldOrientation.DataField);

        IPivotField field_Country = pivottable.getPivotFields().get("Country");
        field_Country.setOrientation(PivotFieldOrientation.ColumnField);

        // find rows which contains "Mobile"
        int rowStart = Integer.MAX_VALUE;
        int rowCount = 0;
        for (IPivotLine row : pivottable.getPivotRowAxis().getPivotLines()) {
            if (row.getPivotLineCells().get(0).getPivotItem() != null && row.getPivotLineCells().get(0).getPivotItem().getName().equals("Mobile")) {
                if (rowStart > row.getPosition()) {
                    rowStart = row.getPosition();
                }
                rowCount++;
            }
        }

        // iterate all pivot lines in PivotColumnAxis
        for (IPivotLine item : pivottable.getPivotColumnAxis().getPivotLines()) {
            // find lines which pivot item is "Germany"
            if (item.getLineType() == PivotLineType.Regular && item.getPivotLineCells().get(0).getPivotItem().getName().equals("Germany")) {
                int row = pivottable.getDataBodyRange().getRow() + rowStart;
                int column = pivottable.getDataBodyRange().getColumn() + item.getPosition();
                // Set style for range of German mobile.
                worksheet.getRange(row, column, rowCount, 1).getInterior().setColor(Color.GetTeal());
                worksheet.getRange(row, column, rowCount, 1).getFont().setColor(Color.GetWhite());
            }
        }

        worksheet.getRange("A:J").getEntireColumn().autoFit();
    }

    @Override
    public boolean getShowViewer() {

        return false;
    }

    @Override
    public boolean getShowScreenshot() {

        return true;
    }
}
