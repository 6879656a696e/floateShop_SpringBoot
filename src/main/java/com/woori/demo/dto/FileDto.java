package com.woori.demo.dto;

import com.woori.demo.domain.File;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDto {
    private Long id;
    private String filename;
    private String filepath;

    public File toEntity(){
        return File.builder()
                .id(id)
                .filename(filename)
                .filepath(filepath)
                .build();
    }

    @Builder
    public FileDto(Long id, String filename, String filepath){
        this.id=id;
        this.filename=filename;
        this.filepath=filepath;
    }
}
