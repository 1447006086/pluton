package cn.zyk.pluton.portal.service.impl;

import cn.zyk.pluton.portal.mapper.CommentMapper;
import cn.zyk.pluton.portal.mapper.UserMapper;
import cn.zyk.pluton.portal.model.Comment;
import cn.zyk.pluton.portal.model.User;
import cn.zyk.pluton.portal.service.ICommentService;
import cn.zyk.pluton.portal.service.ServiceException;
import cn.zyk.pluton.portal.vo.CommentVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentSericerimpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public List<Comment> findCommentBySpId(Integer spId) {
        if (spId==null){
            throw ServiceException.busy();
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("sp_id",spId);
        queryWrapper.orderByDesc("createTime");
        List<Comment>comments = commentMapper.selectList(queryWrapper);
        return comments;
    }

    @Override
    @Transactional
    public Comment insertComment(CommentVo commentVo,String username) {
        User user= userMapper.findeUserByName(username);
        Comment comment=new Comment()
                .setStep(0)
                .setUserLike(0)
                .setUserNickName(user.getUserNickname())
                .setUserId(user.getId())
                .setRete(commentVo.getRate())
                .setUrl(user.getUrl())
                .setContent(commentVo.getContent())
                .setSpId(commentVo.getSpId())
                .setDateTime(LocalDateTime.now());
        int insert = commentMapper.insert(comment);
        if (insert!=1){
            throw ServiceException.busy();
        }
        return comment;
    }

    @Override
    public void deleteCommentById(Integer id) {
        int i = commentMapper.deleteById(id);
        if (i!=1){
            throw ServiceException.busy();
        }
    }
}
