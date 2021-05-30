package cn.zyk.pluton.portal.service;

import cn.zyk.pluton.portal.model.Comment;
import cn.zyk.pluton.portal.vo.CommentVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ICommentService extends IService<Comment> {
    List<Comment> findCommentBySpId(Integer spId);

    Comment insertComment(CommentVo commentVo,String username);

    void deleteCommentById(Integer id);
}
