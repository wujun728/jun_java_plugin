package cn.springmvc.mapper;

import java.util.List;

import cn.springmvc.model.Archivemessages;

public interface ArchivemessagesMapper extends BaseMapper<Long, Archivemessages> {

    List<Archivemessages> findAll();

}