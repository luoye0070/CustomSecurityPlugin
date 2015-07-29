package com.lj.csp.data

/**
 * 数据库存储请求
 */
class Requestmap {

    String url
    String configAttribute

    static mapping = {
        cache true
    }

    static constraints = {
        url blank: false
        configAttribute blank: false
    }

    def getData(){
        return [id:this.id,url:this.url,configAttribute:this.configAttribute]
    }
}
