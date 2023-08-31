package com.rookie.controller.yelp;


import cn.hutool.core.collection.CollUtil;
import com.rookie.common.constants.Constants.UploadSubDir;
import com.rookie.common.core.base.BaseController;
import com.rookie.common.core.dto.ResponseDTO;
import com.rookie.common.core.page.PageDTO;
import com.rookie.common.exception.ApiException;
import com.rookie.common.exception.error.ErrorCode;
import com.rookie.customize.service.file.dto.UploadFileDTO;
import com.rookie.domain.blog.command.AddBlogCommand;
import com.rookie.domain.blog.command.UpdateBlogCommand;
import com.rookie.domain.blog.db.IBlogService;
import com.rookie.domain.blog.dto.BlogDTO;
import com.rookie.domain.blog.query.BlogQuery;
import com.rookie.utils.FileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 博客信息表 前端控制器
 * </p>
 *
 * @author yayee
 */
@Api(value = "Blog Interfaces", tags = "Blog Interfaces")
@RestController
@RequestMapping("/biz/blog")
public class BlogController extends BaseController {

    @Resource
    private IBlogService blogService;

    @ApiOperation("Query blog List")
    @PostMapping("/list")
    public ResponseDTO<PageDTO<BlogDTO>> list(BlogQuery query) {
        PageDTO<BlogDTO> dto = blogService.getBlogList(query);
        return ResponseDTO.ok(dto);
    }

    @ApiOperation("Query blog By blogId")
    @GetMapping("/getInfo/{blogId}")
    public ResponseDTO<BlogDTO> getInfo(@PathVariable @NotNull @Positive Long blogId) {
        return ResponseDTO.ok(blogService.getBlogInfo(blogId));
    }

    @ApiOperation("Add blog")
    @PostMapping("/add")
    public ResponseDTO<Void> add(@Valid @RequestBody AddBlogCommand command) {
        blogService.addBlog(command);
        return ResponseDTO.ok();
    }

    @ApiOperation("Edit blog")
    @PostMapping("/edit")
    public ResponseDTO<Void> edit(@Valid @RequestBody UpdateBlogCommand command) {
        blogService.updateBlog(command);
        return ResponseDTO.ok();
    }

    @ApiOperation("Delete blog")
    @GetMapping("/delete/{blogId}")
    public ResponseDTO<Void> deleteById(@PathVariable @NotNull @Positive Long blogId) {
        blogService.deleteById(blogId);
        return ResponseDTO.ok();
    }

    @ApiOperation("Like blog")
    @GetMapping("/like/{blogId}")
    public ResponseDTO<Void> likeBlog(@PathVariable @NotNull @Positive Long blogId) {
        blogService.likeBlog(blogId);
        return ResponseDTO.ok();
    }

    @ApiOperation("Photo upload")
    @PostMapping("/images")
    @ApiImplicitParam(name = "imgfiles", dataTypeClass = MultipartFile.class, required = true)
    public ResponseDTO<List<UploadFileDTO>> uploadPhotos(@RequestPart("imgfiles") List<MultipartFile> files) {
        if (CollUtil.isEmpty(files)) {
            throw new ApiException(ErrorCode.Business.UPLOAD_FILE_IS_EMPTY);
        }
        List<UploadFileDTO> uploads = new ArrayList<>(16);
        for (MultipartFile file : files) {
            if (file != null) {
                String imgUrl = FileUploadUtil.upload(UploadSubDir.BLOG_IMAGE_PATH, file);
                UploadFileDTO uploadDTO = new UploadFileDTO(imgUrl);
                uploads.add(uploadDTO);
            }
        }
        return ResponseDTO.ok(uploads);
    }
}

