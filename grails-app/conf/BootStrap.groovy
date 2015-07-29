
class BootStrap {
    def userApiService
    def init = { servletContext ->
            userApiService.init();
    }
    def destroy = {
    }
}
