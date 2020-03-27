package com.gxf.his.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.generate.*;
import com.gxf.his.po.vo.DoctorVo;
import com.gxf.his.po.vo.DrugVo;
import com.gxf.his.po.vo.PrescriptionVo;
import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.service.DrugService;
import com.gxf.his.uitls.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/28 15:17
 */
@RestController
@RequestMapping("/drug")
@Slf4j
public class DrugController extends BaseController {
    @Resource
    private DrugService drugService;

    @GetMapping("/drugName")
    public <T> ServerResponseVO<T> getDrugByDrugName(@RequestParam(value = "drugName", required = false) String drugName
            , @RequestParam(value = "drugAlias") Integer drugAlias, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",
            defaultValue = "5") Integer size) {
        if (drugName == null || drugAlias == null) {
            PageHelper.startPage(page, size);
            List<DrugVo> drugVos = drugService.getAllDrug();
            PageInfo<DrugVo> pageInfo = PageInfo.of(drugVos);
            return MyUtil.cast(ServerResponseVO.success(pageInfo));
        }
        PageHelper.startPage(page, size);
        List<DrugVo> drugVos = drugService.getDrugByDrugName(drugName, drugAlias);
        PageInfo<DrugVo> pageInfo = PageInfo.of(drugVos);
        return MyUtil.cast(ServerResponseVO.success(pageInfo));
    }

    @GetMapping("/toxicologyList")
    public <T> ServerResponseVO<T> loadDrugToxicologyList() {
        List<DrugToxicology> drugToxicologyList = drugService.loadDrugToxicologyList();
        return MyUtil.cast(ServerResponseVO.success(drugToxicologyList));
    }

    @GetMapping("/attribute")
    public <T> ServerResponseVO<T> getDrugsByAttribute(@RequestParam(value = "attribute", defaultValue = "drugName", required = false) String attribute,
                                                       @RequestParam(value = "isAccurate") Boolean isAccurate,
                                                       @RequestParam(value = "value", required = false) String value,
                                                       @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                                       @RequestParam(value = "size", defaultValue = "5", required = false) Integer size) {
        //值为空则查询所有药品信息
        if (value == null || value.trim().length() == 0) {
            PageHelper.startPage(page, size);
            List<DrugVo> drugVos = drugService.getAllDrug();
            PageInfo<DrugVo> pageInfo = PageInfo.of(drugVos);
            return MyUtil.cast(ServerResponseVO.success(pageInfo));
        }
        //参数检查
        if (!searchParamCheck(attribute, false, value)) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        PageHelper.startPage(page, size);
        List<DrugVo> drugVos = drugService.selectDrugVosByAttribute(isAccurate, attribute, value);
        PageInfo<DrugVo> pageInfo = PageInfo.of(drugVos);
        return MyUtil.cast(ServerResponseVO.success(pageInfo));
    }

    @PostMapping("/drugDistribution")
    public <T> ServerResponseVO<T> drugDistribution(@RequestBody List<DrugDistribution> drugDistributions) {
        if (drugDistributions == null || drugDistributions.size() == 0) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        drugService.saveDrugDistributionsAndDecreaseStock(drugDistributions);
        return ServerResponseVO.success();
    }

    @PutMapping
    public <T> ServerResponseVO<T> drugUpdate(@RequestBody DrugVo drugVo) {
        drugService.drugUpdate(drugVo);
        return ServerResponseVO.success();
    }

    @PostMapping
    public <T> ServerResponseVO<T> drugAdd(@RequestBody DrugVo drugVo) {
        //检查是否存在相同药品
        List<DrugVo> drugs = drugService.getDrugByCode(drugVo.getCode());
        if (drugs.size() > 0) {
            return ServerResponseVO.error(ServerResponseEnum.DRUG_REPEAT_ERROR);
        }
        drugService.addDrug(drugVo);
        return ServerResponseVO.success();
    }

    @DeleteMapping
    public <T> ServerResponseVO<T> deleteDrugByDrugId(@RequestParam(name = "drugId") Long drugId) {
        try {
            drugService.deleteDrugByDrugId(drugId);
            return ServerResponseVO.success();
        } catch (Exception e) {
            return ServerResponseVO.error(ServerResponseEnum.DRUG_DELETE_FAIL);
        }
    }

    @DeleteMapping("/batch")
    public <T> ServerResponseVO<T> batchDeleteDrug(@RequestBody List<DrugVo> drugVos) {
        drugService.batchDeleteDrug(drugVos);
        return ServerResponseVO.success();
    }
}
