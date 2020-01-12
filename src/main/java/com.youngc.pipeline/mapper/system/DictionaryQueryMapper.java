package com.youngc.pipeline.mapper.system;

import com.youngc.pipeline.model.DictModel;
import com.youngc.pipeline.model.DictionaryQueryModel;
import com.youngc.pipeline.model.DictionaryValueModel;
import com.youngc.pipeline.model.UserManagerModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DictionaryQueryMapper {

    /**
     * 数据字典操作sys_dictionary
     * @param keyword
     * @return
     */
    @Select(" SELECT id,dict_value,dict_name,status,remark FROM sys_dictionary" +
            " WHERE ((dict_name LIKE CONCAT('%', #{keyword}, '%')) OR (dict_value LIKE CONCAT('%', #{keyword}, '%')))")
    List<DictionaryQueryModel> getList(String keyword);

    @Insert(" INSERT INTO sys_dictionary (dict_value, dict_name, status,remark,add_person, add_time, last_person, last_time)" +
            " VALUES(#{dictValue}, #{dictName}, #{status},#{remark}, #{addPerson}, now(), #{lastPerson}, now())")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    int insertNewDict(DictionaryQueryModel dictionaryQueryModel);

    /**
     * 删除字典信息
     * @param idList
     * @return
     */
    @Delete(" DELETE FROM sys_dictionary WHERE id IN (${idList});")
    int deleteDictList(@Param("idList") String  idList);

    /**
     * 删除字典信息
     * @param idList
     * @return
     */
    @Delete(" DELETE FROM sys_dict_data  WHERE " +
            " INSTR(CONCAT(',',(SELECT GROUP_CONCAT(dict_value separator ',') FROM sys_dictionary WHERE id IN (${idList})),',')," +
            " CONCAT(',',dict_value,','));")
    int deleteDictDataList(@Param("idList") String  idList);

    /**
     * 查询字典信息
     * @param id
     * @return
     */
    @Select(" SELECT id,dict_value,dict_name,status,remark " +
            " FROM sys_dictionary WHERE id = #{id}")
    DictionaryQueryModel getDictInfo(@Param("id") Long id);

    /**
     * 更新字典信息
     * @param dictionaryQueryModel
     * @return
     */
    @Update(" UPDATE sys_dictionary SET dict_value = #{dictValue}, dict_name = #{dictName},status=#{status},remark=#{remark}," +
            " last_person = #{lastPerson}, last_time = now() WHERE id = #{id}")
    int updateDictInfo(DictionaryQueryModel dictionaryQueryModel);

    /**
     * 查询字典信息
     * @param value
     * @return
     */
    @Select(" SELECT id,dict_value " +
            " FROM sys_dictionary WHERE dict_value = #{value}")
    List<DictionaryQueryModel> getDictInfoByValue(@Param("value") String value);

    /**
     * 数据字典内容sys_dict_data操作
     */
    @Select(" SELECT id,data_name,data_value,dict_value,remark FROM sys_dict_data where dict_value=#{dictValue}" )
    List<DictionaryValueModel> getDictValueList(@Param("dictValue") String dictValue);

    /**
     * 查询字典数据
     * @param id
     * @return
     */
    @Select(" SELECT id,data_name,data_value,dict_value,remark " +
            " FROM sys_dict_data WHERE id = #{id}")
    DictionaryValueModel getDictValueInfo(@Param("id") Long id);

    /**
     * 更新字典数据
     * @param dictionaryValueModel
     * @return
     */
    @Update(" UPDATE sys_dict_data SET data_name = #{dataName}, data_value = #{dataValue},remark=#{remark}," +
            " last_person = #{lastPerson}, last_time = now() WHERE id = #{id}")
    int updateDictValueInfo(DictionaryValueModel dictionaryValueModel);

    /**
     * 添加字典数据
     * @param dictionaryValueModel
     * @return
     */
    @Insert(" INSERT INTO sys_dict_data (data_name, data_value,dict_value,remark,add_person, add_time, last_person, last_time)" +
            " VALUES(#{dataName}, #{dataValue}, #{dictValue},#{remark}, #{addPerson}, now(), #{lastPerson}, now())")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    int insertDictValue(DictionaryValueModel dictionaryValueModel);

    /**
     * 删除字典数据
     * @param idList
     * @return
     */
    @Delete(" DELETE FROM sys_dict_data WHERE id IN (${idList})")
    int deleteDictValueList(@Param("idList") String  idList);

    /**
     * 查询
     * @param dictValue
     * @param dataValue
     * @return
     */
    @Select(" SELECT id,data_value,dict_value" +
            " FROM sys_dict_data WHERE  dict_value = #{dictValue} and data_value=#{dataValue}")
    List<DictionaryValueModel> getDictValueByValue(@Param("dictValue") String dictValue,@Param("dataValue") int dataValue);

//    @Select(" SELECT sdd.data_name name,sdd.data_value value FROM sys_dict_data sdd" +
//            " INNER JOIN sys_dictionary sd ON sd.dict_value = sdd.dict_value" +
//            " WHERE sd.status = 1 AND sdd.dict_value = #{dictValue};")
//    List<DictModel> getDictData(@Param("dictValue") String key);

    /**
     * 查询所有启用组状态的字典数据
     * @return
     */
    @Select(" SELECT sdd.dict_value dictValue,sdd.data_name name,sdd.data_value value FROM sys_dict_data sdd" +
            " INNER JOIN sys_dictionary sd ON sd.dict_value = sdd.dict_value" +
            " WHERE sd.status = 1;")
    List<DictModel> getDictData();

    /**
     * 查询所有启用组状态的字典数据
     * @return
     */
    @Select(" SELECT dict_value FROM sys_dictionary WHERE status=1;")
    List<DictModel> getDict();
}
