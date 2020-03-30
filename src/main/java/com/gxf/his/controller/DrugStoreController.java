package com.gxf.his.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.vo.DrugStoreVo;
import com.gxf.his.po.vo.DrugVo;
import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.service.DrugService;
import com.gxf.his.service.DrugStoreService;
import com.gxf.his.uitls.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/26 16:58
 */
@RestController
@RequestMapping("/drugStore")
@Slf4j
public class DrugStoreController extends BaseController {
    @Resource
    private DrugStoreService drugStoreService;
    @Resource
    private DrugService drugService;

    @GetMapping("/attribute")
    public <T> ServerResponseVO<T> getDrugStoresByAttribute(@RequestParam(value = "attribute", defaultValue = "code", required = false) String attribute,
                                                            @RequestParam(value = "isAccurate") Boolean isAccurate,
                                                            @RequestParam(value = "value", required = false) String value,
                                                            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                                            @RequestParam(value = "size", defaultValue = "5", required = false) Integer size) {
        //查询所有药品信息
        if (value == null || value.trim().length() == 0) {
            PageHelper.startPage(page, size);
            List<DrugStoreVo> drugVos = drugStoreService.getAllDrugStore();
            PageInfo<DrugStoreVo> pageInfo = PageInfo.of(drugVos);
            return MyUtil.cast(ServerResponseVO.success(pageInfo));
        }
        //根据属性查询药品库存
        PageHelper.startPage(page, size);
        List<DrugStoreVo> drugVos = drugStoreService.getDrugStoreVoByAttribute(value, attribute, isAccurate);
        PageInfo<DrugStoreVo> pageInfo = PageInfo.of(drugVos);
        return MyUtil.cast(ServerResponseVO.success(pageInfo));
    }

    @PutMapping
    public <T> ServerResponseVO<T> drugStoreUpdate(@RequestBody DrugStoreVo drugStoreVo) {
        drugStoreVo.setUpdateTime(new Date());
        drugStoreService.drugStoreUpdate(drugStoreVo);
        return ServerResponseVO.success();
    }

    @PostMapping
    public <T> ServerResponseVO<T> drugAdd(@RequestBody DrugStoreVo drugStoreVo) {
        //检查是否存在相同药品的库存
        List<DrugStoreVo> drugs = drugStoreService.getDrugStoreByCode(drugStoreVo.getDrug().getCode());
        if (drugs.size() > 0) {
            return ServerResponseVO.error(ServerResponseEnum.DRUG_REPEAT_ERROR);
        } else {
            //关联药品ID
            List<DrugVo> drugVos = drugService.getDrugByCode(drugStoreVo.getDrug().getCode());
            if (drugVos.size() <= 0) {
                log.warn("找不到要关联的药品信息");
                return ServerResponseVO.error(ServerResponseEnum.DRUG_EXIST_ERROR);
            }else if(drugVos.size()>1){
                log.warn("出现多个要关联的药品信息");
                return ServerResponseVO.error(ServerResponseEnum.DRUG_REPEAT_ERROR);
            }else {
               drugStoreVo.setDrugId(drugVos.get(0).getDrugId());
               drugStoreVo.setUpdateTime(new Date());
            }
            drugStoreService.addDrugStore(drugStoreVo);
            //关联插入后的药品库存ID到药品上
            drugVos.get(0).setInventoryId(drugStoreVo.getInventoryId());
            drugService.drugUpdate(drugVos.get(0));
            return ServerResponseVO.success();
        }

    }

    @DeleteMapping
    public <T> ServerResponseVO<T> deleteDrugStoreByDrugId(@RequestParam(name = "inventoryId") Long inventoryId) {
        try {
            drugStoreService.deleteDrugStoreByInventoryId(inventoryId);
            return ServerResponseVO.success();
        } catch (Exception e) {
            return ServerResponseVO.error(ServerResponseEnum.DRUG_DELETE_FAIL);
        }
    }

    @DeleteMapping("/batch")
    public <T> ServerResponseVO<T> batchDeleteDrugStore(@RequestBody List<DrugStoreVo> drugStoreVos) {
        drugStoreService.batchDeleteDrugStore(drugStoreVos);
        return ServerResponseVO.success();
    }
}
