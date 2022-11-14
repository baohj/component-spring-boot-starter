package com.cosmo.component.function;

import com.cosmo.component.model.ImportExcelVO;
import com.cosmo.component.util.ExcelUtils;

import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ImportExcelFunction<T> {

    private Class<T> clz;

    public ImportExcelFunction(){
        //this指BaseDaoImpl的实例
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
        //返回表示此类型实际类型参数的 Type 对象的数组(),赋值给this.classt
        this.clz = (Class)type.getActualTypeArguments()[0];//<T>
    }

    /**
     * 第一步：判断导入名称在groupNameList中是否存在，如果存在则报异常;如果不重复，将导入的公司名称添加到groupNameList
     */
    public ImportExcelVO importing(InputStream in){
        //设置上下文
        Map<String,Object> context = new HashMap<String, Object>();
        open(context);

        List<ImportExcelVO.ErrorMsg> errorList = new ArrayList<ImportExcelVO.ErrorMsg>();
        List<T> list = ExcelUtils.importing(in,clz);

        //excel为空判断
        if(list == null || list.size() == 0){
            ImportExcelVO.ErrorMsg error = new ImportExcelVO.ErrorMsg();
            error.setNum(1).setErrorMsg("excel不能为空");
            errorList.add(error);
            ImportExcelVO importExcelVO = new ImportExcelVO().setErrorMsgList(errorList)
                    .setErrorCount(0).setCorrectCount(0);
            return importExcelVO;
        }

        for(int i=0;i<list.size();i++){
            ImportExcelVO.ErrorMsg error = new ImportExcelVO.ErrorMsg().setNum(i+1);
            T t = list.get(i);
            //收集错误信息
            List<String> msgList= new ArrayList<String>();
            check(context,t,msgList);
            if(msgList.size() > 0){
                String msgStr = msgList.stream().collect(Collectors.joining(","));
                error.setErrorMsg(msgStr);
                errorList.add(error);
            }
        }
        if(errorList.size() ==0){
            process(context,list);
            ImportExcelVO importExcelVO = new ImportExcelVO()
                    .setErrorCount(0).setCorrectCount(list.size());
            return importExcelVO;
        }

        //返回异常信息
        ImportExcelVO importExcelVO = new ImportExcelVO().setErrorMsgList(errorList)
                .setErrorCount(errorList.size()).setCorrectCount(list.size() - errorList.size());
        return importExcelVO;
    }


    /**
     * 设置上下文
     * @param context
     */
    protected void open(Map<String,Object> context){

    };

    /**
     * 校验错误信息
     * @param in
     * @return
     */
    protected abstract void check(Map<String,Object> context,T in,List<String> out);


    /**
     * 获取结果数据，可以存入数据库
     * @param in
     */
    protected abstract void process(Map<String,Object> context,List<T> in);
}
