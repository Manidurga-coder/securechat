package org.project;

import org.apache.catalina.startup.Tomcat;
import java.io.File;

public class SecureChatApplication {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();


        String port = System.getenv("PORT");
        if (port == null || port.isEmpty()) {
            port = "8080"; // Fallback for local testing
        }
        tomcat.setPort(Integer.parseInt(port));


        File base = new File(System.getProperty("java.io.tmpdir"));
        tomcat.setBaseDir(base.getAbsolutePath());


        tomcat.addWebapp("/", new File("src/main/webapp").getAbsolutePath());

        System.out.println("SecureChatApplication running on port: " + port);

        tomcat.start();
        tomcat.getServer().await();
    }
}
