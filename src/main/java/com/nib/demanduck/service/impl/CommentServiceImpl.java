package com.nib.demanduck.service.impl;

import com.nib.demanduck.entity.Comment;
import com.nib.demanduck.mapper.CommentMapper;
import com.nib.demanduck.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论回复表 服务实现类
 * </p>
 *
 * @author linxiaobin
 * @since 2023-08-28 05:30:36
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
