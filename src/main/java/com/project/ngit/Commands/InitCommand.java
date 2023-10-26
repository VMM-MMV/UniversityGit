package com.project.ngit.Commands;

import static com.project.ngit.Commands.DgitApplication.makeFolder;

public class InitCommand {
    public static void execute(String repositoryPath) {
        makeFolder(".ngit", repositoryPath);
        makeFolder(".ngit/objects", repositoryPath);
        makeFolder(".ngit/index", repositoryPath);
    }
}
