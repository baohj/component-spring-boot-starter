package io.github.baohj.component.model;

import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;

@Data
@Accessors(chain = true)
public class ImportExcelVO {

    private int correctCount;

    private int errorCount;

    private List<ErrorMsg> errorMsgList;

    @Data
    @Accessors(chain = true)
    public static class ErrorMsg{

        private int num;

        private String errorMsg;

    }
}
