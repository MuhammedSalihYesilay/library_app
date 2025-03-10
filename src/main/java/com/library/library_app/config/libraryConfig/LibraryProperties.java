package com.library.library_app.config.libraryConfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "library")
public class LibraryProperties {
    private String id = "1";
    private String name = "Main Library";
    private String address = "Main Street";
    private String phone = "123-456-7890";
    private String email = "library@example.com";

}

                    //@ConfigurationProperties(prefix = "library"):
        /*Bu anotasyon, application.properties dosyasındaki library. ile başlayan yapılandırma
        anahtarlarının bu sınıfa haritalanacağını belirtir.
         */