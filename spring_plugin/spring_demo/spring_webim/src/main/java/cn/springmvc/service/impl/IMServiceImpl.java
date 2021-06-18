package cn.springmvc.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.mapper.ArchivemessagesMapper;
import cn.springmvc.model.Archivemessages;
import cn.springmvc.service.IMService;

@Service
public class IMServiceImpl implements IMService {
    
    private static final Logger log = LoggerFactory.getLogger(IMServiceImpl.class);

    @Autowired
    private ArchivemessagesMapper archivemessagesMapper;

    @Override
    public List<Archivemessages> findArchivemessagesAll() {
        log.info("# findArchivemessagesAll");
        return archivemessagesMapper.findAll();
    }

}
