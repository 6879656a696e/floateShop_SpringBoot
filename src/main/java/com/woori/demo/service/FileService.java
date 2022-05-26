package com.woori.demo.service;

import com.woori.demo.domain.File;
import com.woori.demo.dto.FileDto;
import com.woori.demo.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class FileService {
    private FileRepository fileRepository;

    @Transactional
    public Long saveFile(FileDto fileDto) {
        return fileRepository.save(fileDto.toEntity()).getId();
    }

    @Transactional
    public FileDto getFile(Long id){
        File file=fileRepository.findById(id).get();

        FileDto fileDto=FileDto.builder()
                .id(id)
                .filename(file.getFilename())
                .filepath(file.getFilepath())
                .build();
        return fileDto;
    }
}
