package com.ezio.pipeline;

import com.ezio.entity.Comment;
import com.ezio.entity.Music;
import com.ezio.repository.CommentRepository;
import com.ezio.repository.MusicRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by Ezio on 2017/6/28.
 */
@Component
public class NetEaseMusicPipeline implements Pipeline {

	@Autowired
	public MusicRepository mMusicDao;

	@Autowired
	public CommentRepository mCommentDao;

	@Override
	public void process(ResultItems resultItems, Task task) {

		for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
			if (entry.getKey().equals("music")) {
				Music music = (Music) entry.getValue();
				System.out.println("mMusicDao--->null" + mMusicDao == null);
				if (mMusicDao.countBySongId(music.getSongId()) == 0) {
					mMusicDao.save(music);
				}
			} else {
				Comment comment = (Comment) entry.getValue();
				System.out.println("mCommentDao--->null" + mCommentDao == null);
				if (mCommentDao.countByCommentId(comment.getCommentId()) == 0) {
					mCommentDao.save(comment);
				}
			}

		}
	}


}
