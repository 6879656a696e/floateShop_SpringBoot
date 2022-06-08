package com.woori.demo.controller;

import com.woori.demo.domain.Product;
import com.woori.demo.dto.FileDto;
import com.woori.demo.dto.ProductDto;
import com.woori.demo.service.FileService;
import com.woori.demo.service.ProductService;
import com.woori.demo.util.MD5Generator;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@CrossOrigin(origins="*")
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;
    private final FileService fileService;

    @PostMapping("/admin/addProduct")
    public void addProduct( @RequestPart("file") MultipartFile files, ProductDto dto) {
        try{
            String savePath=new File("").getAbsolutePath() + "_upload"+File.separator;
            String contentType = files.getContentType();
            String originalFileExtension=contentType.split("/")[1];
            String originFilename=files.getOriginalFilename();
            String filename=new MD5Generator(originFilename).toString()+"."+originalFileExtension;

            if(!new File(savePath).exists()){
                try{
                    new File(savePath).mkdir();
                } catch(Exception e){
                    e.getStackTrace();
                }
            }

            String filePath=savePath+filename;
            files.transferTo(new File(filePath));

            FileDto fileDto = FileDto.builder()
                    .filename(filename)
                    .filepath(filePath)
                    .build();


            Long fileId=fileService.saveFile(fileDto);
            dto.setFileId(fileId);
            dto.setProductPic(filename);
            productService.addProduct(dto);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("/productList")
    @ResponseBody
    public List<Product> getList() {
        return productService.getProductList();
    }

    @GetMapping("/admin/deleteProduct")
    @ResponseBody
    public String productDelete(@RequestParam Long id){
        productService.deleteProduct(id);
        return "삭제 완료";
    }

    @GetMapping("/productDetail")
    @ResponseBody
    public List<Product> productDetail(@RequestParam Long id){
        return productService.getProductDetail(id);
    }

    @PostMapping("/admin/modiProduct")
    @ResponseBody
    public void modifyProduct(@RequestPart("file") MultipartFile files, ProductDto dto){
        try{
            String savePath=new File("").getAbsolutePath() + "_upload"+File.separator;
            String contentType = files.getContentType();
            String originalFileExtension=contentType.split("/")[1];
            String originFilename=files.getOriginalFilename();
            String filename=new MD5Generator(originFilename).toString()+"."+originalFileExtension;

            if(!new File(savePath).exists()){
                try{
                    new File(savePath).mkdir();
                } catch(Exception e){
                    e.getStackTrace();
                }
            }

            String filePath=savePath+filename;
            files.transferTo(new File(filePath));

            FileDto fileDto = FileDto.builder()
                    .filename(filename)
                    .filepath(filePath)
                    .build();


            Long fileId=fileService.saveFile(fileDto);
            dto.setFileId(fileId);
            dto.setProductPic(filename);
            productService.modiProduct(dto);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}