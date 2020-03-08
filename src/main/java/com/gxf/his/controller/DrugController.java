package com.gxf.his.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.vo.DoctorVo;
import com.gxf.his.po.vo.DrugVo;
import com.gxf.his.po.vo.PrescriptionVo;
import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.service.DrugService;
import com.gxf.his.uitls.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    @GetMapping("/attribute")
    public <T> ServerResponseVO<T> getDrugsByAttribute(@RequestParam(value = "attribute", defaultValue = "drugName", required = false) String attribute,
                                                       @RequestParam(value = "isAccurate") Boolean isAccurate,
                                                       @RequestParam(value = "value", required = false) String value,
                                                       @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                                       @RequestParam(value = "size", defaultValue = "5", required = false) Integer size) {
        //不精确查询才检查模糊查询参数列表
        if (!isAccurate) {
            if (!searchParamCheck(attribute, false, value)) {
                return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
            }
            PageHelper.startPage(page, size);
            List<DrugVo> drugVos = drugService.selectDrugVosByAttribute(false, attribute, value);
            PageInfo<DrugVo> pageInfo = PageInfo.of(drugVos);
            return MyUtil.cast(ServerResponseVO.success(pageInfo));
        } else {
            PageHelper.startPage(page, size);
            List<DrugVo> drugVos = drugService.getDrugVoByDrugId(Long.parseLong(value));
            PageInfo<DrugVo> pageInfo = PageInfo.of(drugVos);
            return MyUtil.cast(ServerResponseVO.success(pageInfo));
        }
    }
}
