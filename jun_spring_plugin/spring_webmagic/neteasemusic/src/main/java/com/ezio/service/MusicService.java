package com.ezio.service;

import com.ezio.entity.Comment;
import com.ezio.entity.Music;
import com.ezio.repository.CommentRepository;
import com.ezio.repository.MusicRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ezio on 2017/6/28.
 */
@Service
public class MusicService {
	@Autowired
	private MusicRepository mMusicRepository;
	@Autowired
	private CommentRepository mCommentRepository;

	public void addMusic(Music music) {
		//判断数据是否存在
		if (mMusicRepository.countBySongId(music.getSongId()) == 0) {
			mMusicRepository.save(music);
		}

	}

	public void addComment(Comment comment) {
		//判断数据是否存在
		if (mCommentRepository.countByCommentId(comment.getCommentId()) == 0) {
			mCommentRepository.save(comment);
		}
	}



	public void addComments(List<Comment> comments) {
		mCommentRepository.save(comments);
	}
}
