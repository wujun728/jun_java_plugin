package com.ezio.repository;

import com.ezio.entity.Music;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Ezio on 2017/6/28.
 */
public interface MusicRepository extends JpaRepository<Music, Integer> {
	int countBySongId(String songId);

}
