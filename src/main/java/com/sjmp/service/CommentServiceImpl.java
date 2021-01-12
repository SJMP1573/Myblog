package com.sjmp.service;

import com.sjmp.dao.CommentRepository;
import com.sjmp.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: sjmp1573
 * @date: 2020/10/4 15:27
 * @description:
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
//        创建一个排序的方式,根据创建时间来排序
        Sort sort = Sort.by("createTime");
//        这里会返回所有的根上的评论数据（即没有父评论的评论）
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
//        但我们只需要父节点为空的
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
//        拿到前端页面中的默认 -1 的父id
        Long parentCommentId = comment.getParentComment().getId();
//        如果是属于回复的评论,则设置当前回复的父id.
        if(parentCommentId != -1){
//            先拿到父评论对象，再设置子评论对象属性的父 ID。
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        }else{
//            如果是不是回复,而是首条评论,则设置其父 ID 为null.
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }


    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
//        将顶级评论拷贝一份放到 commentsView 中！
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments 根节点评论集合，blog 不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在 tempReplys 中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            // 每当为一个根评论设置完回复评论的二级列表 tempReplys。 所以当循环下一个根评论的时，需要清除临时存放区 tempReplys
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱。某一顶级评论下的所有回复都放到第二层集 tempReplys
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }


}
