package com.project.ngit;

import static com.project.ngit.DgitApplication.makeFolder;

public class InitCommand {
    public static void execute(String repositoryPath) {
        makeFolder(".ngit", repositoryPath);
        makeFolder(".ngit/objects", repositoryPath);
        makeFolder(".ngit/index", repositoryPath);
    }
}
