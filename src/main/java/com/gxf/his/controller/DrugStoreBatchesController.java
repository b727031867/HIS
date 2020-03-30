package com.gxf.his.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxf.his.enmu.ServerResponseEnum;
import com.gxf.his.po.vo.DrugStoreBatchesVo;
import com.gxf.his.po.vo.ServerResponseVO;
import com.gxf.his.service.DrugStoreBatchesService;
import com.gxf.his.uitls.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/3/28 00:21
 */
@RestController
@RequestMapping("/drugStoreBatches")
@Slf4j
public class DrugStoreBatchesController extends BaseController {
    @Resource
    private DrugStoreBatchesService drugStoreBatchesService;

    @GetMapping("/attribute")
    public <T> ServerResponseVO<T> getDrugStoreBatchesByAttribute(@RequestParam(value = "attribute", defaultValue = "supplierName", required = false) String attribute,
                                                                  @RequestParam(value = "isAccurate") Boolean isAccurate,
                                                                  @RequestParam(value = "value", required = false) String value,
                                                                  @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                                                  @RequestParam(value = "size", defaultValue = "5", required = false) Integer size) {
        //值为空则查询所有药品库存批次信息
        if (value == null || value.trim().length() == 0) {
            PageHelper.startPage(page, size);
            List<DrugStoreBatchesVo> drugStoreBathesVos = drugStoreBatchesService.getAllDrugStoreBatches();
            PageInfo<DrugStoreBatchesVo> pageInfo = PageInfo.of(drugStoreBathesVos);
            return MyUtil.cast(ServerResponseVO.success(pageInfo));
        }
        //参数检查
        if (!searchParamCheck(attribute, false, value)) {
            return ServerResponseVO.error(ServerResponseEnum.PARAMETER_ERROR);
        }
        PageHelper.startPage(page, size);
        List<DrugStoreBatchesVo> drugStoreBathesVos = drugStoreBatchesService.selectDrugStoreBatchesVosByAttribute(isAccurate, attribute, value);
        PageInfo<DrugStoreBatchesVo> pageInfo = PageInfo.of(drugStoreBathesVos);
        return MyUtil.cast(ServerResponseVO.success(pageInfo));
    }

    @GetMapping("/review")
    public <T> ServerResponseVO<T> reviewDrugStoreBatches( @RequestParam(value = "inventoryBatchesId") Long inventoryBatchesId,@RequestParam(value = "verifierId") Long verifierId){
        drugStoreBatchesService.reviewDrugStoreBatches(inventoryBatchesId,verifierId);
        return ServerResponseVO.success();
    }

    @GetMapping("/antiReview")
    public <T> ServerResponseVO<T> antiReviewDrugStoreBatches( @RequestParam(value = "inventoryBatchesId") Long inventoryBatchesId,@RequestParam(value = "verifierId") Long verifierId){
        drugStoreBatchesService.antiReviewDrugStoreBatches(inventoryBatchesId,verifierId);
        return ServerResponseVO.success();
    }

    @PostMapping("/batchReview")
    public <T> ServerResponseVO<T> reviewDrugStoreBatches(@RequestBody List<DrugStoreBatchesVo> drugStoreBathesVos){
        drugStoreBatchesService.batchReviewDrugStoreBatches(drugStoreBathesVos);
        return ServerResponseVO.success();
    }

    @PostMapping("/batchAntiReview")
    public <T> ServerResponseVO<T> antiReviewDrugStoreBatches(@RequestBody List<DrugStoreBatchesVo> drugStoreBathesVos){
        drugStoreBatchesService.batchAntiReviewDrugStoreBatches(drugStoreBathesVos);
        return ServerResponseVO.success();
    }

    @PutMapping
    public <T> ServerResponseVO<T> drugStoreBathesUpdate(@RequestBody DrugStoreBatchesVo drugStoreBathesVo) {
        drugStoreBatchesService.drugStoreBathesUpdate(drugStoreBathesVo);
        return ServerResponseVO.success();
    }

    @PostMapping
    public <T> ServerResponseVO<T> drugStoreBathesAdd(@RequestBody DrugStoreBatchesVo drugStoreBathesVo) {
        drugStoreBatchesService.addDrugStoreBatches(drugStoreBathesVo);
        return ServerResponseVO.success();
    }

    @DeleteMapping
    public <T> ServerResponseVO<T> deleteDrugStoreBatchesById(@RequestParam(name = "inventoryBatchesId") Long inventoryBatchesId) {
        try {
            drugStoreBatchesService.deleteDrugStoreBatchesById(inventoryBatchesId);
            return ServerResponseVO.success();
        } catch (Exception e) {
            return ServerResponseVO.error(ServerResponseEnum.DRUG_DELETE_FAIL);
        }
    }

    @DeleteMapping("/batch")
    public <T> ServerResponseVO<T> batchDeleteDrugStoreBatches(@RequestBody List<DrugStoreBatchesVo> drugStoreBathesVos) {
        drugStoreBatchesService.batchDeleteDrugStoreBatches(drugStoreBathesVos);
        return ServerResponseVO.success();
    }
}
