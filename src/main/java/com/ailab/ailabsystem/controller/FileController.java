package com.ailab.ailabsystem.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ailab.ailabsystem.common.CommonConstant;
import com.ailab.ailabsystem.enums.ResponseStatusEnum;
import com.ailab.ailabsystem.exception.CustomException;
import com.ailab.ailabsystem.model.entity.InOutRegistration;
import com.ailab.ailabsystem.model.vo.InOutRegistrationVo;
import com.ailab.ailabsystem.service.SignInService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author xiaozhi
 */
@Api(value = "文件服务接口", tags = "文件服务接口")
@RestController
@RequestMapping("/fs")
public class FileController {

    @Resource
    private SignInService signInService;

    @ApiOperation(value = "导出签到记录接口", notes = "导出签到记录接口")
    @GetMapping("/exportSignInRecode")
    public void userLogin(HttpServletResponse response){
        String filename = "SignInRecode" + CommonConstant.EXCEL_SUFFIX;
        // 文件的类型
        response.setContentType("application/vnd.ms-excel");
        // 设置浏览器的编码
        response.setCharacterEncoding("utf-8");
        // 告诉浏览器以附件的形式下载
        response.setHeader("Content-disposition","attachment;filename*=utf-8''" + filename);
        // 获取所有签到数据
        List<InOutRegistration> inOutRegistrations = signInService.list();
        List<InOutRegistrationVo> inOutRegistrationVos = BeanUtil.copyToList(inOutRegistrations, InOutRegistrationVo.class);
        // 生成Excel
        try {
            EasyExcel.write(response.getOutputStream(), InOutRegistrationVo.class)
                    .registerWriteHandler(new SimpleColumnWidthStyleStrategy(20))
                    .sheet("签到记录")
                    .doWrite(inOutRegistrationVos);
        } catch (IOException e) {
            throw new CustomException(ResponseStatusEnum.EXPORE_EXCEL_ERROR);
        }
    }

}
