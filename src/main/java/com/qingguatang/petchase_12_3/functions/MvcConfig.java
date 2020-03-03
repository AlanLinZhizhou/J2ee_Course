package com.qingguatang.petchase_12_3.functions;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResourceInterceptor()).addPathPatterns("/**").excludePathPatterns("/templates/*",
                "/static/*");
//        super.addInterceptors(registry);
    }

    /**
     * 修改springboot中默认的静态文件路径，同时将云服务器上的图片逻辑地址进行映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//addResourceHandler请求路径
//addResourceLocations 在项目中的资源路径
//setCacheControl 设置静态资源缓存时间
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //.setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
        registry.addResourceHandler(("/templates/**")).addResourceLocations("classpath:/templates");
//        super.addResourceHandlers(registry);

        //关于云服务器的设置
        File file = new File("");
        String path = file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf(File.separator));
        //判断操作系统
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
            //项目相对路径+项目动态绝对路径
            registry.addResourceHandler("/static/photos/**").
                    addResourceLocations("file:" + path + "/home/admin/java/images/");

        } else {//linux和mac系统
            registry.addResourceHandler("/static/photos/**").
                    addResourceLocations("file:" + path + "/home/admin/java/images/");
        }

    }

}
