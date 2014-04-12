package com.portal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.portal.model.Post;

@Repository
public class PostDaoImpl implements PostDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public Post get(long id) {
		return em.find(Post.class, id);
	}

	@Override
	@Transactional
	public void addPost(Post post) {
		em.persist(post);

	}

}
