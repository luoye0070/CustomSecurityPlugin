// configuration for plugin testing - will not be included in the plugin zip

log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
}


// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.lj.csp.data.Member'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.lj.csp.data.MemberAuthority'
grails.plugins.springsecurity.authority.className = 'com.lj.csp.data.Authority'
grails.plugins.springsecurity.requestMap.className = 'com.lj.csp.data.Requestmap'

grails.plugins.springsecurity.apf.filterProcessesUrl = '/login_check' //登录验证的路径,前台把用户名和密码提交到这个路径 ,默认是j_spring_security_check
grails.plugins.springsecurity.apf.usernameParameter = 'username' //前台提交用户名 时用到的参数名称,可以自定义,与前台一致即可
grails.plugins.springsecurity.apf.passwordParameter = 'password' //前台提交密码 时用到的参数名称,可以自定义,与前台一致即可
grails.plugins.springsecurity.logout.filterProcessesUrl = '/logout' //退出验证的路径, 前台要与此一致

grails.plugins.springsecurity.securityConfigType = "Requestmap"