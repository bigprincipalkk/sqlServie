package com.youngc.pipeline.service.system.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youngc.pipeline.mapper.system.DictionaryQueryMapper;
import com.youngc.pipeline.model.DictModel;
import com.youngc.pipeline.model.DictionaryQueryModel;
import com.youngc.pipeline.model.DictionaryValueModel;
import com.youngc.pipeline.service.system.DictionaryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DictionaryQueryServiceImpl implements DictionaryQueryService {

    @Autowired
    private DictionaryQueryMapper dictionaryQueryMapper;

    /**
     * 分页获取字典信息
     * @param keyWord
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page getList(String keyWord, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return (Page)dictionaryQueryMapper.getList(keyWord);
    }

    /**
     * 添加字典信息
     * @param dictionaryQueryModel
     * @return
     */
    public DictionaryQueryModel addDict(DictionaryQueryModel dictionaryQueryModel) {
        dictionaryQueryMapper.insertNewDict(dictionaryQueryModel);
        return dictionaryQueryModel;
    }

    /**
     * 查询字典信息
     * @param id
     * @return
     */
    public DictionaryQueryModel getDictInfo(Long id) {
        return dictionaryQueryMapper.getDictInfo(id);
    }

    /**
     * 更新字典信息
     * @param dictionaryQueryModel
     * @return
     */
    public DictionaryQueryModel updateDictInfo(DictionaryQueryModel dictionaryQueryModel) {
        dictionaryQueryMapper.updateDictInfo(dictionaryQueryModel);
        return dictionaryQueryModel;
    }

    /**
     * 删除字典信息
     * @param idList
     * @return
     */
    @Transactional
    public boolean deleteDictList(String idList) {
        dictionaryQueryMapper.deleteDictDataList(idList);
        dictionaryQueryMapper.deleteDictList(idList);
        return true;
    }

    /**
     * 查询字典信息
     * @param value
     * @return
     */
    public List<DictionaryQueryModel> getDictInfoByValue(String value) {
        return dictionaryQueryMapper.getDictInfoByValue(value);
    }

    /**
     * 分页获取字典数据
     * @param keyWord
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page getDictValueList(String keyWord,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return (Page)dictionaryQueryMapper.getDictValueList(keyWord);
    }

    /**
     * 添加字典数据
     * @param dictionaryValueModel
     * @return
     */
    public DictionaryValueModel addDictValue(DictionaryValueModel dictionaryValueModel) {
        dictionaryQueryMapper.insertDictValue(dictionaryValueModel);
        return dictionaryValueModel;
    }

    /**
     * 查询字典数据
     * @param id
     * @return
     */
    public DictionaryValueModel getDictValue(Long id) {
        return dictionaryQueryMapper.getDictValueInfo(id);
    }

    /**
     * 更新字典数据
     * @param dictionaryValueModel
     * @return
     */
    public DictionaryValueModel updateDictValueInfo(DictionaryValueModel dictionaryValueModel) {
        dictionaryQueryMapper.updateDictValueInfo(dictionaryValueModel);
        return dictionaryValueModel;
    }

    /**
     * 删除字典数据
     * @param idList
     * @return
     */
    public boolean deleteDictValueList(String idList) {
        dictionaryQueryMapper.deleteDictValueList(idList);
        return true;
    }

    /**
     * 查询
     * @param dictValue
     * @param dataValue
     * @return
     */
    public List<DictionaryValueModel> getDictValueByValue(String dictValue, int dataValue) {
        return dictionaryQueryMapper.getDictValueByValue(dictValue,dataValue);
    }
//
//    /**
//     * 查询字典信息
//     */
//    public Map getDict(String  key){
//
//        List<DictModel> dictData = new ArrayList<DictModel>();
//
//        dictData = dictionaryQueryMapper.getDictData(key);
//        Map dict = new HashMap();
//        dict.put(key, dictData);
//        return dict;
//    }

    /**
     * 查询字典信息
     */
    public Map getDict() {
        List<DictModel> dData = dictionaryQueryMapper.getDict();
        List<DictModel> dictData = dictionaryQueryMapper.getDictData();

        Map dict = new HashMap();
        for (int j = 0; j < dData.size(); j++) {
            List<DictModel> resultData = new ArrayList<DictModel>();

            Iterator<DictModel> dictModel = dictData.iterator();

            while(dictModel.hasNext()){
                DictModel dictValue = dictModel.next();
                if(dictValue.getDictValue().equals(dData.get(j).getDictValue())){
                    resultData.add(dictValue);
                    dictModel.remove();
                }
            }

            dict.put(dData.get(j).getDictValue(), resultData);
        }
        return dict;
    }
}
