package com.grapecity.documents.excel.examples.features.formatting;

import com.grapecity.documents.excel.FormatConditionType;
import com.grapecity.documents.excel.IFormatCondition;
import com.grapecity.documents.excel.IWorksheet;
import com.grapecity.documents.excel.Workbook;
import com.grapecity.documents.excel.examples.ExampleBase;
import com.grapecity.documents.excel.style.color.Color;

public class GetDisplayFormat extends ExampleBase {

    @Override
    public void execute(Workbook workbook) {

        IWorksheet worksheet = workbook.getWorksheets().get(0);

        //Range A1's displaying color is table style
        worksheet.getTables().add(worksheet.getRange("A1:E5"), true);
        Color color_A1 = worksheet.getRange("A1").getDisplayFormat().getInterior().getColor();

        //Range A1's displaying color will be cell style, yellow.
        worksheet.getRange("A1").getInterior().setColor(Color.getYellow());
        Color color_A1_1 = worksheet.getRange("A1").getDisplayFormat().getInterior().getColor();

        //Range A1's displaying color will be conditional format style, green.
        IFormatCondition condition = (IFormatCondition) worksheet.getRange("A1").getFormatConditions().add(FormatConditionType.NoBlanksCondition);
        condition.getInterior().setColor(Color.getGreen());
        Color color_A1_2 = worksheet.getRange("A1").getDisplayFormat().getInterior().getColor();

    }

}