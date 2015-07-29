package com.lj.csp.enumCustom

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-3
 * Time: 上午11:25
 * To change this template use File | Settings | File Templates.
 */
public class ReCode {
//    public final static def NO_RESULT=[code:1,label:"没有记录"];
//    public final static def NO_ENOUGH_PARAMS=[code:2,label:"没有足够的参数"];
//    public final static def HAVE_ERRORS=[code:3,label:"有错误存在"];
//    public final static def OK=[code:0,label:"成功"];
//    public final static def NOT_LOGIN=[code:-1,label:"您没有登录"];
//    public final static def SAVE_FAILED=[code:-2,label:"保存数据失败"];
//    public final static def OTHER_ERROR=[code:-3,label:"未知错误"];
//    public final static def USERNAME_NOT_EXIST=[code:-4,label:"用户名不存在"];
//    public final static def PASSWORD_INCORRECT=[code:-5,label:"密码错误"];
//    public final static def DENIED_USER=[code:-6,label:"非法用户"];
//    public final static def SAVE_FILE_FAILED=[code:-7,label:"保存文件失败"];
//    public final static def SUBMIT_FORMAT_ERROR=[code:-8,label:"提交数据格式错误"];
//    public final static def NOT_A_IMAGE=[code:-9,label:"上传的不是图片"];
//    public final static def SYSTEM_ERROR=[code:-10,label:"系统错误"];
//    public final static def SYSTEM_EXCEPTION = [code:-11, label:"系统异常"];
//    public final static def PASSWORD_DIFFERENCE=[code:-12,label:"两次输入的密码不一致！"];
//    public final static def FILE_IS_TOO_BIG=[code:-13,label:"上传文件大小超过限制"];
//    public final static def FILE_EXT_NOT_ALLOW=[code:-14,label:"上传文件扩展名是不允许的扩展名"];
//    public final static def UPLOAD_FILE_FAILED=[code:-15,label:"上传文件失败"];


    //王刚添加
    public final static def NEW_SUCCESS=[success:true,msg:'新增成功']
    public final static def NEW_FAIL=[success:false,msg:'新增失败']
    public final static def UPDATE_SUCCESS=[success:true,msg:'更新成功']
    public final static def UPDATE_FAIL=[success:false,msg:'更新失败']
    public final static def DEL_SUCCESS=[success:true,msg:'删除成功']
    public final static def DEL_FAIL=[success:false,msg:'删除失败']
    public final static def NO_RECORD=[success:false,msg:'记录不存在!']
    public final static def MANY_UPDATEING=[success:false,msg:'其他人正在修改该条记录!']
    public final static def PASS_UPDATE_SUCCESS=[success:true,msg:'密码修改成功!']
    public final static def PASS_UPDATE_FAIL=[success:false,msg:'密码修改失败!']
    public final static def UPDATE_ROLE_SUCCESS=[success:true,msg:'更新角色成功!']
    public final static def UPDATE_ROLE_FAIL=[success: false,msg:'更新角色失败!']
    public final static def UPDATE_ROLE_AUTHORITY_SUCCESS=[success:true,msg:'更新角色权限成功!']
    public final static def UPDATE_ROLE_AUTHORITY_FAIL=[success: false,msg:'更新角色权限失败!']
    public final static def MEMEBER_EXSITS=[success: false,msg:'该账户已经存在!']
    public final static def PARAMETERS_ERROR=[success:false,msg:'参数错误!']
    public final static def PASSWORD_DIFF=[success: false,msg:"两次输入的密码不一致!"]
    public final static def OLD_ERROR=[success: false,msg:"旧密码不正确!"]
    public final static def UPD_PWD_SUCCESS=[success: true,msg:"更新密码成功!"]
    public final static def UPD_PWD_FAIL=[success: true,msg:"更新密码失败!"]
    public final static def NOT_ALLOW_DEL=[success:false,msg:'该账户不允许删除!']
//    public Integer code
//    public String label
//
//    ReCode=[code:Integer code,String label){
//        this.code=code
//        this.label=label
//    }

}