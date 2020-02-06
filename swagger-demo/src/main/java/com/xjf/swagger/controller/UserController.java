package com.xjf.swagger.controller;

import com.xjf.swagger.entity.AddUserParam;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

/**
 * @author xjf
 * @date 2020/2/6 15:53
 */
@Api(tags = "用户接口")
@RestController
public class UserController {

    @ApiOperation(value = "新增用户", notes = "这是新增用户方法的详细说明")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @PostMapping("/add")
    public String addUser(@ApiParam(value = "新增用户参数", required = true) @RequestBody AddUserParam param){
        System.err.println(param);

        return "新增用户成功";
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户 ID", dataType = "String", paramType = "query", required = true, defaultValue = "1")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = AddUserParam.class)
    })
    @GetMapping("/getUser")
    public AddUserParam getUser(@RequestParam("id") String id){
        return new AddUserParam(id, "盖伦", 23);
    }
}
