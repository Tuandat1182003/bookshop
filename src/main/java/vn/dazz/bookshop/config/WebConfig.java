package vn.dazz.bookshop.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static String IMAGE_PATH = "D:\\projectT3h\\BookShop\\src\\main\\resources\\images\\";

    // Handle HTTP GET requests for /resources/** by efficiently serving
    // static resources under ${webappRoot}/resources/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images-file/**")
                .addResourceLocations("file:/" + IMAGE_PATH);
    }

}