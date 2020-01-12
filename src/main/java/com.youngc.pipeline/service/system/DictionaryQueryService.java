package com.youngc.pipeline.service.system;

import com.github.pagehelper.Page;
import com.youngc.pipeline.model.DictionaryQueryModel;
import com.youngc.pipeline.model.DictionaryValueModel;
import com.youngc.pipeline.model.UserManagerModel;

import java.util.List;
import java.util.Map;

public interface DictionaryQueryService {

    //数据字典操作sys_dictionary
    Page getList(String keyWord, int pageNum, int pageSize);

    DictionaryQueryModel addDict(DictionaryQueryModel dictionaryQueryModel);

    DictionaryQueryModel getDictInfo(Long id);

    DictionaryQueryModel updateDictInfo(DictionaryQueryModel dictionaryQueryModel);

    boolean deleteDictList(String idList);

    List<DictionaryQueryModel> getDictInfoByValue(String value);

    //数据字典内容操作sys_dict_data
    Page getDictValueList(String keyWord,int pageNum, int pageSize);

    DictionaryValueModel addDictValue(DictionaryValueModel dictionaryValueModel);

    DictionaryValueModel getDictValue(Long id);

    DictionaryValueModel updateDictValueInfo(DictionaryValueModel dictionaryValueModel);

    boolean deleteDictValueList(String idList);

    List<DictionaryValueModel> getDictValueByValue(String dictValue,int dataValue);

//    /**
//     * 查询字典信息
//     */
//    Map getDict(String  key);

    /**
     * 查询字典信息
     */
    Map getDict();


}
