package cn.zyk.pluton.portal.controller;

import cn.zyk.pluton.portal.mapper.UserMapper;
import cn.zyk.pluton.portal.model.Comment;
import cn.zyk.pluton.portal.model.User;
import cn.zyk.pluton.portal.service.ICommentService;
import cn.zyk.pluton.portal.service.ServiceException;
import cn.zyk.pluton.portal.vo.CommentVo;
import cn.zyk.pluton.portal.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/{id}")
    public R<List<Comment>> getComments(@PathVariable Integer id){
        log.debug("评论:{}",id);
        List<Comment> commentBySpId = commentService.findCommentBySpId(id);
        return R.ok(commentBySpId);
    }

    @PostMapping("/add")
    public R add(@Validated CommentVo commentVo, @AuthenticationPrincipal UserDetails user, BindingResult result){
        log.debug("commentVo:{}",commentVo);
        if (result.hasErrors()){
            String msg= result.getFieldError().getDefaultMessage();
            R.unauthorized(msg);
        }
        Comment comment=commentService.insertComment(commentVo, user.getUsername());
        log.debug("comment:{}",comment);
        return R.created(comment);
    }

    @GetMapping("/delete/{id}")
    public R delete(@PathVariable Integer id){
        if (id==null){
            return R.notFound("找不到要删除的评论");
        }
        commentService.deleteCommentById(id);
        return R.gone("评论已删除");
    }

}
