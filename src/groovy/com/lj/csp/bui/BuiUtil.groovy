package com.lj.csp.bui

/**
 * Bui相关工具方法
 */
class BuiUtil {
    /**
     * ids字符串
     * @param v
     * @return  返回id通过,连接的字符串
     */
    static String getIds(def v){
        if(v instanceof Object[]){
            return v.join(',')
        }else{
            return v
        }
    }
    /**
     * 处理params
      * @param params
     */
    static void handleParams(def params){
        params.max = Math.min(params.limit as Integer ?: 20, 100)
        params.offset= params.start
    }

    /**
     *@param params相关参数
     *@param queryBlock取出数据的闭包逻辑
     *@param dataFormat 取出数据后具体需要组装成json的数据
     **/
    static def createForJson(params,queryBlock,dataFormat){
        def dataRows=queryBlock.call(params)

        def totalRows=dataRows.totalCount
        def rows=dataRows?.collect{
            dataFormat(it)
        }
        [rows:rows,results:totalRows]
    }
}
