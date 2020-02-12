package com.xjf.demo.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * MongoDB 文件处理
 *
 * @author xjf
 * @date 2020/2/12 12:30
 */
@RestController
public class FileController {

    /**
     * 格子文件系统操作类
     */
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 上传图片
     *
     * @param file
     * @throws IOException
     */
    @PostMapping("/upload")
    public String uploadFile(@RequestBody MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();

        // 存储文件的额外数据，比如用户 ID， 后面要查询某个用户的所有文件就可以直接查询
        DBObject metadata = new BasicDBObject("userId", "1001");

        ObjectId objectId = gridFsTemplate.store(
                inputStream, file.getName(), "image/png", metadata
        );

        System.out.println(objectId.toString());
        return "success";
    }

    /**
     * 根据文件自动生成的 ID 查询
     *
     * @return
     */
    @GetMapping("/getFile")
    public String getFile(){
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is("5e4383c0cf5b0f19f895e945")));
        System.out.println("metadata：" + gridFSFile.getMetadata());

        return gridFSFile.getFilename();
    }

    /**
     * 下载文件
     *
     * @param fileId
     * @param response
     * @throws IOException
     */
    @GetMapping("/image/{fileId}")
    public void getImage(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));

        response.setHeader("Content-Disposition", "attachment;filename=\"" + gridFSFile.getFilename() + "\"");
        GridFSBucket gridFSBucket = GridFSBuckets.create(mongoTemplate.getDb());
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource resource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        InputStream inputStream = resource.getInputStream();
        ServletOutputStream outputStream = response.getOutputStream();

        IOUtils.copy(inputStream,outputStream);
    }

    /**
     * 删除文件
     *
     * @param fileId
     * @return
     */
    @GetMapping("/delete")
    public String removeFile(String fileId){
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(fileId)));

        return "success";
    }
}
