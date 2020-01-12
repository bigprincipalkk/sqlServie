package com.youngc.pipeline.controller.system;

import com.youngc.pipeline.bean.param.DictionaryQueryBean;
import com.youngc.pipeline.bean.param.DictionaryValueBean;
import com.youngc.pipeline.model.DictionaryQueryModel;
import com.youngc.pipeline.model.DictionaryValueModel;
import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import com.youngc.pipeline.service.system.DictionaryQueryService;
import com.youngc.pipeline.utils.RequestContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("/dict")
public class DictionaryQueryController {

    @Autowired
    private DictionaryQueryService dictionaryQueryService;

    /**
     * 通过字典名称获取数据字典的数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/get")
    public Result getDictionaryList(@RequestParam String keyWord,@RequestParam int pageNum, @RequestParam int pageSize){
        return ResultGenerator.generate(dictionaryQueryService.getList(keyWord,pageNum,pageSize));
    }

    /**
     * 添加数据字典
     * @param dictionaryQueryBean
     * @return
     */
    @PostMapping(value = "/postDict")
    public  Result PostDict(@RequestBody DictionaryQueryBean dictionaryQueryBean){
        com.youngc.pipeline.bean.context.UserBean user
                = (com.youngc.pipeline.bean.context.UserBean) RequestContextHolderUtil.getRequest().getAttribute("user");
        DictionaryQueryModel dictionaryQueryModel = new DictionaryQueryModel();

        dictionaryQueryModel.setDictValue(dictionaryQueryBean.getDictValue());
        dictionaryQueryModel.setDictName(dictionaryQueryBean.getDictName());
        dictionaryQueryModel.setRemark(dictionaryQueryBean.getRemark());
        dictionaryQueryModel.setStatus(dictionaryQueryBean.getStatus());

        dictionaryQueryModel.setAddPerson(user.getUserId());
        dictionaryQueryModel.setLastPerson(user.getUserId());

        return ResultGenerator.generate(ResultCode.SUCCESS,
                dictionaryQueryService.addDict(dictionaryQueryModel));
    }

    /**
     * 批量删除数据字典
     * @param idList
     * @return
     */
    @DeleteMapping(value = "/deleteDict")
    public Result deleteDictList(@RequestParam("idList") String idList) {

        dictionaryQueryService.deleteDictList(idList);

        return ResultGenerator.generate(ResultCode.SUCCESS);
    }

    /**
     * 根据id查询数据字典的数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getDictInfo(@PathVariable Long id) {

        return ResultGenerator.generate(ResultCode.SUCCESS, dictionaryQueryService.getDictInfo(id));
    }

    /**
     * 修改数据字典的数据
     * @param dictionaryQueryBean
     * @return
     */
    @PutMapping(value = "/putDict")
    public Result putDictInfo(@RequestBody DictionaryQueryBean dictionaryQueryBean) {
        com.youngc.pipeline.bean.context.UserBean user
                = (com.youngc.pipeline.bean.context.UserBean) RequestContextHolderUtil.getRequest().getAttribute("user");
        DictionaryQueryModel dictionaryQueryModel = new DictionaryQueryModel();

        dictionaryQueryModel.setId(dictionaryQueryBean.getId());
        dictionaryQueryModel.setDictValue(dictionaryQueryBean.getDictValue());
        dictionaryQueryModel.setDictName(dictionaryQueryBean.getDictName());
        dictionaryQueryModel.setRemark(dictionaryQueryBean.getRemark());
        dictionaryQueryModel.setStatus(dictionaryQueryBean.getStatus());

        dictionaryQueryModel.setLastPerson(user.getUserId());

        return ResultGenerator.generate(ResultCode.SUCCESS, dictionaryQueryService.updateDictInfo(dictionaryQueryModel));
    }

    /**
     * 根据value查询数据字典中的数据是否存在
     */
    @GetMapping(value="/getByValue")
    public Result getInfoByValue(@RequestParam("value") String value) {
        return ResultGenerator.generate(ResultCode.SUCCESS, dictionaryQueryService.getDictInfoByValue(value));
    }

    /**
     * 查询数据字典内容表中的数据
     * @param dictValue
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/getDictValueList")
    public Result getDictValueList(@RequestParam("dictValue") String dictValue,@RequestParam int pageNum, @RequestParam int pageSize){
        return ResultGenerator.generate(dictionaryQueryService.getDictValueList(dictValue,pageNum,pageSize));
    }

    /**
     * 添加数据字典内容
     * @param dictionaryValueBean
     * @return
     */
    @PostMapping(value = "/postDictValue")
    public  Result PostDictValue(@RequestBody DictionaryValueBean dictionaryValueBean){
        com.youngc.pipeline.bean.context.UserBean user
                = (com.youngc.pipeline.bean.context.UserBean) RequestContextHolderUtil.getRequest().getAttribute("user");
        DictionaryValueModel dictionaryValueModel = new DictionaryValueModel();

        dictionaryValueModel.setDictValue(dictionaryValueBean.getDictValue());
        dictionaryValueModel.setDataName(dictionaryValueBean.getDataName());
        dictionaryValueModel.setRemark(dictionaryValueBean.getRemark());
        dictionaryValueModel.setDataValue(dictionaryValueBean.getDataValue());

        dictionaryValueModel.setAddPerson(user.getUserId());
        dictionaryValueModel.setLastPerson(user.getUserId());

        return ResultGenerator.generate(ResultCode.SUCCESS,
                dictionaryQueryService.addDictValue(dictionaryValueModel));
    }

    /**
     * 批量删除数据字典内容
     * @param idList
     * @return
     */
    @DeleteMapping(value = "/deleteDictValue")
    public Result deleteDictValueList(@RequestParam("idList") String idList) {

        dictionaryQueryService.deleteDictValueList(idList);

        return ResultGenerator.generate(ResultCode.SUCCESS);
    }

    /**
     * 根据id查询数据字典内容表的数据
     * @param id
     * @return
     */
    @GetMapping("/getValue/{id}")
    public Result getDictValueInfo(@PathVariable Long id) {

        return ResultGenerator.generate(ResultCode.SUCCESS, dictionaryQueryService.getDictValue(id));
    }

    /**
     * 修改数据字典内容表中的数据
     * @param dictionaryValueBean
     * @return
     */
    @PutMapping(value = "/putDictValue")
    public Result putDictValueInfo(@RequestBody DictionaryValueBean dictionaryValueBean) {
        com.youngc.pipeline.bean.context.UserBean user
                = (com.youngc.pipeline.bean.context.UserBean) RequestContextHolderUtil.getRequest().getAttribute("user");
        DictionaryValueModel dictionaryValueModel = new DictionaryValueModel();

        dictionaryValueModel.setId(dictionaryValueBean.getId());
        dictionaryValueModel.setDataName(dictionaryValueBean.getDataName());
        dictionaryValueModel.setRemark(dictionaryValueBean.getRemark());
        dictionaryValueModel.setDataValue(dictionaryValueBean.getDataValue());

        dictionaryValueModel.setLastPerson(user.getUserId());

        return ResultGenerator.generate(ResultCode.SUCCESS, dictionaryQueryService.updateDictValueInfo(dictionaryValueModel));
    }

    /**
     * 根据datavalue查询表中dictvalue字典是否有数据
     * @param dictValue
     * @param dataValue
     * @return
     */
    @GetMapping(value="/getDictValueByValue")
    public Result getDictValueInfoByValue(@RequestParam("dictValue") String dictValue,@RequestParam("dataValue") int  dataValue) {
        return ResultGenerator.generate(ResultCode.SUCCESS, dictionaryQueryService.getDictValueByValue(dictValue,dataValue));
    }

//    /**
//     * 获取数据字典内容
//     * @return
//     */
//    @GetMapping
//    public Result getDict(@RequestParam("key") String key){
//
//        return ResultGenerator.generate(ResultCode.SUCCESS,dictionaryQueryService.getDict(key));
//    }
    /**
     * 获取数据字典内容
     * @return
     */
    @GetMapping
    public Result getDict(){

        return ResultGenerator.generate(ResultCode.SUCCESS,dictionaryQueryService.getDict());
    }
}
