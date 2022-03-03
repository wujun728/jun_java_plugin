package org.springrain.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springrain.system.dto.FileDto;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.service.ISystemRootService;

@Service("systemRootService")
public class SystemRootServiceImpl extends BaseSpringrainServiceImpl implements
		ISystemRootService {

	@Override
	public List<FileDto> findFileDtosByPath(String path, String rootPath)
			throws Exception {
		List<FileDto> rets = new ArrayList<>();
		List<FileDto> fileDtos = new ArrayList<>();
		File file = new File(path);

		File[] fs = file.listFiles();
		
		if (fs != null) {
			for (File f : fs) {
				FileDto dto = new FileDto();
				String ap = f.getAbsolutePath();
				ap = StringUtils.replace(ap, "\\", "/");

				String pa = f.getPath();
				pa = StringUtils.replace(pa, "\\", "/");
				dto.setName(f.getName());
				dto.setAbsolutePath(ap);
				dto.setPath(pa);
				pa = dto.getPath().replaceAll(rootPath, "");
				dto.setPath(pa);
				if (f.isDirectory()) {
					dto.setType(1);
					rets.add(dto);
				} else {
					dto.setType(0);
					fileDtos.add(dto);
				}

			}
		}
		rets.addAll(fileDtos);
		return rets;
	}
}
