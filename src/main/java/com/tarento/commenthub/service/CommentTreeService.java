package com.tarento.commenthub.service;

import com.tarento.commenthub.dto.ApiResponse;
import com.tarento.commenthub.dto.CommentTreeIdentifierDTO;
import com.tarento.commenthub.entity.CommentTree;

public interface CommentTreeService {


  CommentTree getCommentTreeById(String commentTreeId);

  ApiResponse getCommentTree(CommentTreeIdentifierDTO commentTreeIdentifierDTO);


}