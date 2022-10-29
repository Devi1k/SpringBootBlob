package com.example.springbootblog.controller.admin;

import com.example.springbootblog.entity.BlogLink;
import com.example.springbootblog.service.LinkService;
import com.example.springbootblog.utils.PageQueryUtil;
import com.example.springbootblog.utils.Result;
import com.example.springbootblog.utils.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class LinkController {

    @Resource
    private LinkService linkService;

    @GetMapping("/links")
    public String linkPage(HttpServletRequest request) {
        request.setAttribute("path", "links");
        return "admin/link";
    }


    @PostMapping(value = "/links/save")
    @ResponseBody
    public Result save(@RequestParam("linkName") String linkName,
                       @RequestParam("linkType") Integer linkType,
                       @RequestParam("linkUrl") String linkUrl,
                       @RequestParam("linkRank") Integer linkRank,
                       @RequestParam("linkDescription") String linkDescription) {
        if (linkType == null || linkType < 0 || linkRank == null || linkRank < 0 || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkUrl) || StringUtils.isEmpty(linkDescription)) {
            return ResultGenerator.genFailResult("参数异常");
        }
        BlogLink blogLink = new BlogLink();
        blogLink.setLinkName(linkName);
        blogLink.setLinkType(linkType.byteValue());
        blogLink.setLinkUrl(linkUrl);
        blogLink.setLinkRank(linkRank);
        blogLink.setLinkDescription(linkDescription);
        return ResultGenerator.genSuccessResult(linkService.saveLink(blogLink));

    }


    @PostMapping("/links/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常");
        }
        if (linkService.deleteBatch(ids))
            return ResultGenerator.genSuccessResult();
        return ResultGenerator.genFailResult("删除失败");
    }

    @PostMapping("/links/update")
    @ResponseBody
    public Result update(@RequestParam("linkId") Integer linkId,
                         @RequestParam("linkName") String linkName,
                         @RequestParam("linkType") Integer linkType,
                         @RequestParam("linkUrl") String linkUrl,
                         @RequestParam("linkRank") Integer linkRank,
                         @RequestParam("linkDescription") String linkDescription) {
        BlogLink temp = linkService.selectById(linkId);
        if (temp == null) {
            return ResultGenerator.genFailResult("无数据");
        }
        if (linkType == null || linkType < 0 || linkRank == null || linkRank < 0 || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkUrl) || StringUtils.isEmpty(linkDescription)) {
            return ResultGenerator.genFailResult("参数异常");
        }
        temp.setLinkName(linkName);
        temp.setLinkType(linkType.byteValue());
        temp.setLinkUrl(linkUrl);
        temp.setLinkRank(linkRank);
        temp.setLinkDescription(linkDescription);
        return ResultGenerator.genSuccessResult(linkService.updateLink(temp));
    }

    @GetMapping("/links/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Integer id) {
        BlogLink link = linkService.selectById(id);
        return ResultGenerator.genSuccessResult(link);
    }

    @GetMapping("/links/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数错误");
        }
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(linkService.getBlogLinkPage(pageQueryUtil));
    }


}
