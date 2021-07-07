package io.renren.modules.sys.controller;

import io.renren.common.exception.RRException;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.R;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

/**
 * @Auther zxl
 * @Date 2019/12/23 16:43
 * @Description
 **/
@Slf4j
@Api(value = "文件管理",tags = "文件管理")
@RestController
@RequestMapping("/sys/file")
public class SysFileController extends AbstractController {

    @Value("${files.path}")
    private String filePath;

    /**
     * 上传文件
     */
    @ApiOperation(value = "上传文件",notes = "上传文件，图片获取地址{/files/文件名}")
    @PostMapping("/upload/")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
        String fileName =  DateUtils.format(new Date(),"yyyyMMddHHmmssSSS") + file.getOriginalFilename();

        File sysFile = new File(StringUtils.removeStart(filePath,"file:")+fileName);
        file.transferTo(sysFile);
        return R.ok().put("fileName",fileName);
    }
}
