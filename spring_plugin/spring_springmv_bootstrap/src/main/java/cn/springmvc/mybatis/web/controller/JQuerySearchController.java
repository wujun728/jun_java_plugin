package cn.springmvc.mybatis.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.springmvc.mybatis.web.vo.Tag;

/**
 * @author Vincent.wang
 *
 */
@Controller
@RequestMapping(value = "jq")
public class JQuerySearchController {

    private static List<Tag> getData() {
        List<Tag> data = new ArrayList<Tag>();
        data.add(new Tag(1, "ruby"));
        data.add(new Tag(2, "rails"));
        data.add(new Tag(3, "c / c++"));
        data.add(new Tag(4, ".net"));
        data.add(new Tag(5, "python"));
        data.add(new Tag(6, "java"));
        data.add(new Tag(7, "javascript"));
        data.add(new Tag(8, "jscript"));
        return data;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getPages() {
        return "jquery/search";
    }

    @RequestMapping(value = "/getTags", method = RequestMethod.GET)
    public @ResponseBody List<Tag> getTags(@RequestParam String tagName) {
        List<Tag> result = new ArrayList<Tag>();
        for (Tag tag : getData()) {
            if (tag.getTagName().contains(tagName)) {
                result.add(tag);
            }
        }
        return result;
    }

}
