package com.lq.controller;

import com.lq.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: liqian
 * @Date: 2019-09-27
 * @Time: 17:16
 */

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {


    /**
     * 查询文章
     * @return
     */
    @PostMapping("/getArticle")
    public R getArticle () {
        return R.ok("very good");
    }


}
