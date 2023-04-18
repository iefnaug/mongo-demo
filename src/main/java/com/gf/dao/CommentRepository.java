package com.gf.dao;

import com.gf.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author GF
 * @since 2023/4/14
 */
public interface CommentRepository extends MongoRepository<Comment, String> {


    Page<Comment> findByParentid(String parentid, Pageable pageable);

}
