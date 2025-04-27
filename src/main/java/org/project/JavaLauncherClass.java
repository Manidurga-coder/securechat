package org.project;

// (match your groupId)

import org.apache.catalina.startup.Tomcat;

public class JavaLauncherClass {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        String webappDirLocation = "src/main/webapp/";

        tomcat.setPort(Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"))); // Dynamic PORT
        tomcat.getHost().setAppBase(".");
        tomcat.addWebapp("", new java.io.File(webappDirLocation).getAbsolutePath());

        System.out.println("Configuring app with basedir: " + new java.io.File("./" + webappDirLocation).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }
}
